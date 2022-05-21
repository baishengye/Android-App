package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.ImageUtil;

import butterknife.BindView;

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
        ImageUtil.show(getMainActivity(),iv_background,data.getBanner());
    }

    /**
     * 启动方法
     */
    public static void start(Activity activity){
        Intent intent=new Intent(activity,MusicPlayerActivity.class);
        activity.startActivity(intent);
    }
}