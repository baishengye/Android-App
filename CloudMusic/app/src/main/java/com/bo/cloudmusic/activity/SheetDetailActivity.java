package com.bo.cloudmusic.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bo.cloudmusic.Adapter.SongAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ResourceUtil;
import com.bo.cloudmusic.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity implements View.OnClickListener {

    private static final String TAG = "SheetDetailActivity";
    /**
     * 歌单id
     */
    private String id;

    /**
     * 歌单数据
     */
    private Sheet data;

    /**
     * 歌曲适配器
     */
    private SongAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 头部容器
     */
    private LinearLayout ll_header;
    /**
     * 封面banner
     */
    private ImageView iv_banner;
    /**
     * 标题
     */
    private TextView tv_title;
    /**
     * 头像
     */
    private ImageView iv_avatar;
    /**
     *按钮容器
     */
    private LinearLayout ll_comment_container;
    /**
     * 昵称
     */
    private TextView tv_nickname;
    /**
     * 评论数
     */
    private TextView tv_comment_count;
    /**
     * 收藏按钮
     */
    private Button bt_collection;
    /**
     * 水平容器
     */
    private LinearLayout ll_play_all_container;
    /**
     * 歌曲数量
     */
    private TextView tv_count;

    /**
     * 用户容器
     */
    private LinearLayout ll_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    /**
     * 创建菜单方法
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载按钮布局
        getMenuInflater().inflate(R.menu.menu_sheet_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单点击回调
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                ToastUtil.successShortToast("搜索");
                return true;
            case R.id.action_report:
                ToastUtil.successShortToast("报告");
                return true;
            case R.id.action_sort:
                ToastUtil.successShortToast("排序");
                return true;
        }
        /*true表示该方法执行完毕后，点击事件不会再向下一个事件处理方法传递了。
        false表示执行完该方法后，点击事件继续向下传递。*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //尺寸固定
        rv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);

        /*//分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getMainActivity(), RecyclerView.VERTICAL);

        //添加到控件
        //可以添加多个
        rv.addItemDecoration(decoration);*/
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的参数
        id = extraId();

        //new 出适配器
        adapter = new SongAdapter(R.layout.item_song_detail);

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //把适配器添加到rv中
        rv.setAdapter(adapter);

        fetchData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //用户容器监听
        ll_user.setOnClickListener(this);

        //添加监听
        bt_collection.setOnClickListener(this);

        //监听评论容器
        ll_comment_container.setOnClickListener(this);

        //监听播放容器
        ll_play_all_container.setOnClickListener(this);

        //监听单曲容器
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SimplePlayerActivity.start(getMainActivity());
            }
        });
    }

    /**
     * 创建头部
     * @return
     */
    private View createHeaderView(){
        //从xml填充布局
        View view=getLayoutInflater().inflate(R.layout.header_sheet_detail, (ViewGroup) rv.getParent(),false);

        //头部容器
        ll_header = view.findViewById(R.id.ll_header);

        //歌单创建者容器
        ll_user=view.findViewById(R.id.ll_user);
        
        //封⾯图
        iv_banner = view.findViewById(R.id.iv_banner);
        
        //标题
        tv_title = view.findViewById(R.id.tv_title);

        //歌单创建者头像
        iv_avatar = view.findViewById(R.id.iv_avatar);

        //歌单创建者昵称
        tv_nickname = view.findViewById(R.id.tv_nickname);

        //评论容器
        ll_comment_container = view.findViewById(R.id.ll_comment_container);

        //评论数
        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        //收藏按钮
        bt_collection = view.findViewById(R.id.bt_collection);

        //播放全部容器
        ll_play_all_container = view.findViewById(R.id.ll_play_all_container);

        //歌曲数
        tv_count = view.findViewById(R.id.tv_count);
        

        //返回view
        return view;
    }

    /**
     * 请求歌单数据
     */
    private void fetchData(){
        Api.getInstance().sheetDetail(id)
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> data) {
                        next(data.getData());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void next(Sheet data) {
        this.data=data;
       // LogUtil.d(TAG,data.toString());

        //设置数据
        List<Song> songs= data.getSongs();
        if(songs!=null&&songs.size()>0){
            adapter.replaceData(songs);
        }

       /* //显示封面
        if(StringUtils.isBlank(data.getBanner())){
            //空的就显示默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        }else{
            //有图片
            ImageUtil.show(getMainActivity(),iv_banner,data.getBanner());
        }*/

        //原生palette
        /*//使⽤Palette获取封⾯颜⾊
        if(StringUtils.isBlank(data.getBanner())){
            //空显示默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        }else{
            //有图片
            //这是⼀个典型的构建者模式
            Glide.with(this)
                    .asBitmap()
                    .load(ResourceUtil.resourceUri(data.getBanner()))
                    .into(new CustomTarget<Bitmap>() {//将网络上的图片加载成bitmap，并且加载到自定义容器里面
                        //加载图⽚到⾃定义⽬标
                        //为什么是⾃定义⽬标
                        //是因为我们要获取Bitmap
                        //然后获取Bitmap的⼀些颜⾊
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //资源加载完完成

                            //把位图设置到iv_banner中
                            iv_banner.setImageBitmap(resource);

                            //在Material Design(MD，材料设计，是Google的⼀⻔设计语⾔)的设计中
                            //所谓的设计语⾔就是⼀些设计规范
                            //⽬前Google已经应⽤到Android，Gmail等产品
                            //推荐我们将应⽤的状态栏
                            //标题栏的颜⾊和当前⻚⾯的内容融合
                            //也就说当前⻚⾯显示⼀张红⾊的图⽚
                            //那么最好状态栏，标题栏的颜⾊也和红⾊差不多
                            //实现这种效果可以借助的Palette类。
                            //Palette:可以翻译为调⾊板
                            //功能是可以从图⽚中获取⼀些颜⾊
                            //详细的可以学习《详解Material Design，http://www.ixuea.com/courses/9》课程

                            //让背景颜色和图片颜色趋近
                            Palette.from(resource)
                                    .generate(new Palette.PaletteAsyncListener() {//在子线程中进行计算出palette

                                        *//**
                                         * 颜色计算完成了回调到主线程中
                                         * @param palette
                                         *//*
                                        @Override
                                        public void onGenerated(@Nullable Palette palette) {
                                            //获取有活力的颜色
                                            Palette.Swatch swatch = palette.getVibrantSwatch();//当图片是整体是比较暗的颜色时可能获取不到有活力的颜色

                                            if(swatch!=null){
                                                //获取颜色的rgb
                                                int rgb=swatch.getRgb();

                                                //设置标题栏的颜色
                                                toolbar.setBackgroundColor(rgb);

                                                *//*设置头部容器的颜色*//*
                                                ll_header.setBackgroundColor(rgb);

                                                //判断api版本,有些api只能在大于21的环境中使用
                                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                                                    Window window = getWindow();

                                                    //状态栏颜色
                                                    window.setStatusBarColor(rgb);

                                                    //导航栏颜色
                                                    window.setNavigationBarColor(rgb);
                                                }
                                            }
                                        }
                                    });

                        }

                        *//**
                         * 加载任务取消了
                         * 释放次元
                         * @param placeholder
                         *//*
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }*/

        //第三方框架glide+palette
        if(StringUtils.isBlank(data.getBanner())){
            //网络没请求过来图片
            //默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        }else{
            //有图片
            //获取图片路径
            String uri = ResourceUtil.resourceUri(data.getBanner());

            GlidePalette<Drawable> glidePalette = GlidePalette.with(uri)
                    //使⽤VIBRANT颜色样板(活性样板)
                    .use(BitmapPalette.Profile.VIBRANT)
                    //设置到控件背景
                    .intoBackground(toolbar, BitmapPalette.Swatch.RGB)
                    .intoBackground(ll_header, BitmapPalette.Swatch.RGB)
                    //设置回调
                    //⽤回调的⽬的是
                    //要设置状态栏和导航栏
                    .intoCallBack(new BitmapPalette.CallBack() {
                        @Override
                        public void onPaletteLoaded(@Nullable Palette palette) {
                            //获取有活力的颜色
                            Palette.Swatch swatch = palette.getVibrantSwatch();//当图片是整体是比较暗的颜色时可能获取不到有活力的颜色

                            if (swatch != null) {
                                //获取颜色的rgb
                                int rgb = swatch.getRgb();

                                //设置标题栏的颜色
                                toolbar.setBackgroundColor(rgb);

                                /*设置头部容器的颜色*/
                                ll_header.setBackgroundColor(rgb);

                                //判断api版本,有些api只能在大于21的环境中使用
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Window window = getWindow();

                                    //状态栏颜色
                                    window.setStatusBarColor(rgb);

                                    //导航栏颜色
                                    window.setNavigationBarColor(rgb);
                                }
                            }
                        }
                    })
                    //淡⼊
                    //只有第⼀次效果很明显
                    //由于这⾥是项⽬课程
                    //所以就不在深⼊查看是为什么了
                    //如果⼤家感兴趣可以深⼊查看
                    //搞懂了也可以在群⾥分享给⼤家
                    .crossfade(true);

            //使⽤Glide
            Glide.with(getMainActivity())
                    //加载图⽚
                    .load(uri)
                    //加载完成监听器
                    .listener(glidePalette)
                    //将图⽚设置到图⽚控件
                    .into(iv_banner);
        }


        //显示标题
        tv_title.setText(data.getTitle());

        //头像
        ImageUtil.showAvatar(getMainActivity(),iv_avatar,data.getUser().getAvatar());

        //昵称
        tv_nickname.setText(data.getUser().getNickname());

        //评论数
        tv_comment_count.setText(String.valueOf(data.getComments_count()));

        //音乐数
        tv_count.setText(String.valueOf(data.getSongs_count()));

        //显示收藏状态
        showCollectionStatus();
    }

    /**
     * 显示收藏状态
     */
    @SuppressLint("ResourceType")
    private void showCollectionStatus(){
        if(data.isCollection()){
            //如果已经收藏

            //把按钮文字改成取消收藏
            bt_collection.setText(getResources().getString(R.string.cancel_collection,data.getCollections_count()));

            //弱化收藏按钮
            //想要用户收藏而不是取消
            bt_collection.setBackground(null);

            //设置按钮文字颜色弱化
            bt_collection.setTextColor(getResources().getColor(R.color.light_grey));
        }else{
            //没收藏

            //按钮文字收藏
            bt_collection.setText(getResources().getString(R.string.collection,data.getCollections_count()));

            //高显收藏按钮
            bt_collection.setBackgroundResource(R.drawable.selector_color_primary);

            //文字高显白色
            bt_collection.setTextColor(getResources().getColor(R.drawable.selector_text_color_primary));
        }
    }

    /**
     * 按钮点击回调方法
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_collection:
                //收藏按钮被点击了
                processCollectionClick();
                break;
            case R.id.ll_comment_container:
                //评论容器被点击了
                CommentActivity.start(getMainActivity(),data.getId());
                break;
            case R.id.ll_user:
                startActivityExtraId(UserDetailActivity.class,data.getUser().getId());
                break;
            case R.id.ll_play_all_container:
                SimplePlayerActivity.start(getMainActivity());
                break;
        }
    }

    /**
     * 处理收藏和取消的逻辑
     */
    private void processCollectionClick() {
        //LogUtil.d(TAG,"processCollectionClick");

        if(data.isCollection()){
            //如果收藏了

            //取消收藏
            Api.getInstance().deleteCollect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> responseData) {
                            //弹出提示框
                            ToastUtil.successShortToast(R.string.cancel_success);

                            //重新加载数据，显示新的数据状态
                            //fetchData();
                            /*由于收藏数不是很紧要的数字,所以可以不用去远程请求数据(远程请求的时候还会请求其他很多数据，消耗很多资源，很没必要)*/
                            //所以可以直接本地数据加减即可
                            data.setCollection_id(null);//Collection_id是空就是没有收藏
                            data.setCollections_count(data.getCollections_count()-1);

                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        }else{
            //没有收藏
            //就收藏
            Api.getInstance().collect(id)
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> responseData) {
                            //弹出提示框
                            ToastUtil.successShortToast(R.string.collection_success);

                            //重新加载数据，显示新的数据状态
                            //fetchData();
                            /*由于收藏数不是很紧要的数字,所以可以不用去远程请求数据(远程请求的时候还会请求其他很多数据，消耗很多资源，很没必要)*/
                            //所以可以直接本地数据加减即可
                            data.setCollection_id(1);//Collection_id是空就是没有收藏
                            data.setCollections_count(data.getCollections_count()+1);

                            //刷新状态
                            showCollectionStatus();;
                        }
                    });
        }
    }

    /**
     * 获取intent中的id
     * @return
     */
    private String extraId() {
        return getIntent().getStringExtra(Constant.ID);
    }


}