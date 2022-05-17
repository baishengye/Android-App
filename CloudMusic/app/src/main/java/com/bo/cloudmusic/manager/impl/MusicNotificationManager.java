package com.bo.cloudmusic.manager.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.NotificationUtil;
import com.bo.cloudmusic.utils.ToastUtil;

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

    /**
     * 音乐播放管理器
     */
    private final MusicPlayerManager musicPlayerManager;

    /**
     * 音乐列表管理器
     */
    private final ListManager listManager;

    /**
     * 音乐通知栏广播接收器
     */
    private BroadcastReceiver musicNotificationBroadcastReceiver;

    public MusicNotificationManager(Context context) {
        this.context =context.getApplicationContext();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(this.context);

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);

        //添加播放管理器的监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //初始化音乐通知广播接收器
        initMusicNotificationReceiver();
    }

    /**
     * 初始化音乐通知广播接收器
     */
    private void initMusicNotificationReceiver() {
        //创建一个音乐通知广播接收器
        musicNotificationBroadcastReceiver = new BroadcastReceiver() {
            /**
             * 接受到了广播
             * @param context
             * @param intent
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                //获取action
                String action = intent.getAction();

                switch (action){
                    case Constant.ACTION_PLAY:
                        if(musicPlayerManager.isPlaying()){
                            listManager.pause();
                        }else{
                            listManager.resume();
                        }
                        break;
                    case Constant.ACTION_NEXT:
                        listManager.play(listManager.next());
                        break;
                    case Constant.ACTION_PREVIOUS:
                        listManager.play(listManager.previous());
                        break;
                    case Constant.ACTION_LIKE:
                        ToastUtil.successShortToast("ACTION_LIKE");
                        break;
                    case Constant.ACTION_LYRIC:
                        ToastUtil.successShortToast("ACTION_LYRIC");
                        break;
                }
            }
        };

        //创建过滤器,过滤出想要意图的广播
        IntentFilter intentFilter = new IntentFilter();

        //添加要过滤的动作
        intentFilter.addAction(Constant.ACTION_PLAY);
        intentFilter.addAction(Constant.ACTION_LIKE);
        intentFilter.addAction(Constant.ACTION_PREVIOUS);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_LYRIC);

        //这个上下问来接收，注册广播接收器
        context.registerReceiver(musicNotificationBroadcastReceiver,intentFilter);
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
