package com.bo.cloudmusic.activity;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.ResourceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicPlayerActivity extends BaseTitleActivity {


    /**
     * 列表管理器
     */
    private ListManager listManager;

    /**
     * 音乐播放管理器
     */
    private MusicPlayerManager musicPlayerManager;

    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        //状态栏透明
        lightStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //显示初始化数据
        showInitData();
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());
        //初始化播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());
    }

    /**
     * 显示初始化数据
     */
    private void showInitData(){
        //获取当前播放的音乐
        Song data = listManager.getData();

        //设置标题
        setTitle(data.getTitle());

        //设置子标题
        toolbar.setSubtitle(data.getSinger().getNickname());

        //显示背景
        //ImageUtil.show(getMainActivity(),iv_background,data.getBanner());
        //高斯模糊的背景
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).asDrawable();

        if(StringUtils.isBlank(data.getBanner())){
            //没有封面图
            //使用默认图片
            requestBuilder.load(R.drawable.default_album);
        }else{
            //使用真实图片
            requestBuilder.load(ResourceUtil.resourceUri(data.getBanner()));
        }

        //创建请求选项
        //传⼊了BlurTransformation
        //⽤来实现⾼斯模糊
        //radius:模糊半径；值越⼤越模糊
        //sampling:采样率；值越⼤越模糊
        RequestOptions options = bitmapTransform(new BlurTransformation(25, 3));

        //加载图片
        requestBuilder.apply(options)
                .into(new CustomTarget<Drawable>() {
                    /**
                     * 资源下载成功
                     * @param resource
                     * @param transition
                     */
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        //设置到背景图片上
                        iv_background.setImageDrawable(resource);

                        //创建切换动画工具类
                        //new SwitchDrawableUtil(iv_background.getDrawable(),resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 启动方法
     */
    public static void start(Activity activity){
        Intent intent=new Intent(activity,MusicPlayerActivity.class);
        activity.startActivity(intent);
    }
}