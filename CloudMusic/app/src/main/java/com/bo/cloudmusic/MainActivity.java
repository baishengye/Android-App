package com.bo.cloudmusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bo.cloudmusic.Adapter.MainAdapter;
import com.bo.cloudmusic.activity.BaseMusicPlayerActivity;
import com.bo.cloudmusic.activity.BaseTitleActivity;
import com.bo.cloudmusic.activity.SettingActivity;
import com.bo.cloudmusic.activity.WebViewActivity;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMusicPlayerActivity {

    private static final String TAG = "MainActivity";

    /*侧滑布局*/
    @BindView(R.id.dl)
    DrawerLayout dl;

    /*滚动视图*/
    @BindView(R.id.vp)
    ViewPager vp;

    /*指示器*/
    @BindView(R.id.mi)
    MagicIndicator mi;

    /*昵称*/
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    /*描述*/
    @BindView(R.id.tv_description)
    TextView tv_description;

    /*头像*/
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    //适配器
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理动作
        processIntent(getIntent());
    }

    @Override
    protected void initViews() {
        super.initViews();

        //侧滑配置
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getMainActivity(),
                dl,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        //添加侧滑监听器
        dl.addDrawerListener(actionBarDrawerToggle);

        //同步状态
        actionBarDrawerToggle.syncState();

        //创建adapter
        adapter = new MainAdapter(getMainActivity(), getSupportFragmentManager());
        //把适配器设置到vp中
        vp.setAdapter(adapter);

        //缓存⻚⾯数量
        //默认是缓存⼀个
        vp.setOffscreenPageLimit(4);

        //创建占位数据
        List<Integer> datum=new ArrayList<>();
        datum.add(0);
        datum.add(1);
        datum.add(2);
        datum.add(3);
        adapter.setDatum(datum);

        //创建通用的指示器
        CommonNavigator commonNavigator = new CommonNavigator(getMainActivity());

        //设置适配器
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            /**
             * 指示器数量
             * @return
             */
            @Override
            public int getCount() {
                return datum.size();
            }

            /**
             * 返回当前位置的标题
             * @param context
             * @param index
             * @return
             */
            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                //创建简单的文本控件
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);

                //默认颜色
                titleView.setNormalColor(getResources().getColor(R.color.table_normal));

                //选中后的颜色
                titleView.setSelectedColor(getResources().getColor(R.color.white));

                //显示的文本
                titleView.setText(adapter.getPageTitle(index));

                //设置点击回调监听
                titleView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //viewpager跳转到index这个位置
                        vp.setCurrentItem(index);
                    }
                });

                return titleView;
            };

            /**
             * 返回指示器
             * 就是下面的线
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                //创建一条线
                LinePagerIndicator indicator = new LinePagerIndicator(context);

                //线的宽度和内容宽度一样
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);

                //高亮颜色
                indicator.setColors(Color.WHITE);

                return indicator;
            }
        });

        //如果位置显示不下指示器的时候
        //是否自动调整
        commonNavigator.setAdjustMode(true);

        //设置到导航器
        mi.setNavigator(commonNavigator);

        //让指示器和ViewPager配合工作哟
        ViewPagerHelper.bind(mi,vp);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //默认选中第二个界面
        vp.setCurrentItem(1);

        //获取⽤户信息
        //当然可以在⽤户要显示侧滑的时候
        //才获取⽤户信息
        //这样可以减少请求
        fetchData();

        //用Glide显示本地资源目录的图片
        /*Glide.with(this)
                .load(R.drawable.placeholder)
                .into(iv_avatar);*/
    }

    /**
     * 处理动作
     * @param intent
     */
    private void processIntent(Intent intent) {
        if(Constant.ACTION_AD.equals(intent.getAction())){
            //广告点击了

            //显示广告界面
            WebViewActivity.start(getMainActivity(),"活动标题",intent.getStringExtra(Constant.URL));
        }
    }

    /**
     * 界⾯已经显示过了(这个Activity已经在返回栈里了)
     * 不需要再次创建新界⾯的时候调⽤
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "onNewIntent");
        //处理动作
        processIntent(intent);
    }

    /**
     * 请求数据
     */
    private void fetchData() {
        Api.getInstance().userDetail(sp.getUserId())
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        next(data.getData());
                    }
                });
    }

    private void next(User data) {
        //显示头像
        ImageUtil.showAvatar(getMainActivity(),iv_avatar,data.getAvatar());

        //显示昵称
        tv_nickname.setText(data.getNickname());

        //显示描述
        tv_description.setText(data.getDescriptionFormat());
    }

    private void closeDrawer() {
        dl.closeDrawer(GravityCompat.START);//从出现的方向关闭
    }


    /**
     * 点击显示用户详情
     */
    @OnClick(R.id.ll_user)
    public void onUserClick(){
        /*ToastUtil.errorShortToast("onUserClick");*/
    }

    /**
     * 点击进入设置
     */
    @OnClick(R.id.ll_setting)
    public void onSettingClick(){

        startActivity(SettingActivity.class);

        //跳转到Setting的时候关闭抽屉
        closeDrawer();
    }


}