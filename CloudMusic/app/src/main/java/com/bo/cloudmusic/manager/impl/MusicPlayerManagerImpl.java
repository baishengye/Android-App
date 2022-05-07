package com.bo.cloudmusic.manager.impl;

import android.content.Context;

import com.bo.cloudmusic.manager.MusicPlayerManager;

/**
 * 音乐播放器的具体实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {

    /**
     * 单例
     */
    private static MusicPlayerManagerImpl instance;

    /**
     * 上下文
     */
    private final Context context;

    private MusicPlayerManagerImpl(Context context){
       /* context是活动的上下文
       *context.getApplicationContext()是整个程序的上下文
       * 如果this.context=context的话，那么单例里面就会保存活动的上下文，但活动的上下文短期内就会过期
       * 也就是说哪个内存空间里面的内容无了，但是this.context还保存着那个内存地址，使jvm无法释放掉没用的内存，就会造成内存泄漏 */

        this.context=context.getApplicationContext();
    }

    /**
     * 双检锁获取单例
     * @param context
     * @return
     */
    public static MusicPlayerManagerImpl getInstance(Context context) {
        if (instance==null){
            synchronized (MusicPlayerManagerImpl.class){
                if(instance==null){
                    instance=new MusicPlayerManagerImpl(context);
                }
            }
        }
        return instance;
    }
}
