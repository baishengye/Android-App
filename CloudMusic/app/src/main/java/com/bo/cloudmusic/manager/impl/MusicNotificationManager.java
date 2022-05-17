package com.bo.cloudmusic.manager.impl;

import android.content.Context;

/**
 * 音乐通知管理器
 */
public class MusicNotificationManager {

    /**
     * 单例
     */
    private static volatile MusicNotificationManager instance;

    /**
     * 上下文
     */
    private final Context context;

    public MusicNotificationManager(Context context) {
        this.context =context.getApplicationContext();
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
}
