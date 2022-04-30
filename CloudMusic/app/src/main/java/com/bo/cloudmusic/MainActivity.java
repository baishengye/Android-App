package com.bo.cloudmusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bo.cloudmusic.Adapter.MainAdapter;
import com.bo.cloudmusic.activity.BaseCommonActivity;
import com.bo.cloudmusic.activity.BaseTitleActivity;
import com.bo.cloudmusic.activity.SettingActivity;
import com.bo.cloudmusic.activity.WebViewActivity;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.databinding.ActivityMainBinding;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseTitleActivity {

    private static final String TAG = "MainActivity";

    /*侧滑布局*/
    @BindView(R.id.dl)
    DrawerLayout dl;

    /*滚动视图*/
    @BindView(R.id.vp)
    ViewPager vp;

    /*昵称*/
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    /*描述*/
    @BindView(R.id.tv_description)
    TextView tv_description;

    /*头像*/
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

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

                //获取⽤户信息
                //当然可以在⽤户要显示侧滑的时候
                //才获取⽤户信息
                //这样可以减少请求
                fetchData();
            }
        };

        //添加侧滑监听器
        dl.addDrawerListener(actionBarDrawerToggle);

        //同步状态
        actionBarDrawerToggle.syncState();

        //创建adapter
        MainAdapter adapter = new MainAdapter(getMainActivity(), getSupportFragmentManager());
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
    }

    @Override
    protected void initDatum() {
        super.initDatum();

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