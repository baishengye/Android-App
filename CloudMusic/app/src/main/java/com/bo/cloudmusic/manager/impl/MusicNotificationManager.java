package com.bo.cloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.NotificationUtil;

/**
 * 音乐通知管理器
 */
public class MusicNotificationManager implements MusicPlayerListener {

    /**
     * 单例
     */
    private static volatile MusicNotificationManager instance;

    /**
     * 上下文
     */
    private final Context context;
    private final MusicPlayerManager musicPlayerManager;

    public MusicNotificationManager(Context context) {
        this.context =context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);

        //添加播放管理器的监听器
        musicPlayerManager.addMusicPlayerListener(this);
    }

    public static MusicNotificationManager getInstance(Context context) {
        if(instance==null){
            synchronized (MusicNotificationManager.class){
                if (instance==null){
                    instance=new MusicNotificationManager(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onPaused(Song song) {
        //显示通知
        NotificationUtil.showMusicNotification(context,song,false);
    }

    @Override
    public void onPlaying(Song song) {
        //显示通知
        NotificationUtil.showMusicNotification(context,song,true);
    }

    @Override
    public void onPrepared(MediaPlayer mp, Song song) {
        //显示通知
        //NotificationUtil.showMusicNotification(context,song,true);
    }

    @Override
    public void onProgress(Song data) {

    }
}
