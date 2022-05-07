package com.bo.cloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.Consumer;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐播放器的具体实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {

    private static final String TAG = "MusicPlayerManagerImpl";
    /**
     * 单例
     */
    private static MusicPlayerManagerImpl instance;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 当前播放的音乐对象
     */
    private Song data;

    /**
     * 播放器
     */
    private  MediaPlayer player;

    /**
     * 播放监听器的集合
     */
    List<MusicPlayerListener> listeners=new ArrayList<>();

    private MusicPlayerManagerImpl(Context context){
       /* context是活动的上下文
       *context.getApplicationContext()是整个程序的上下文
       * 如果this.context=context的话，那么单例里面就会保存活动的上下文，但活动的上下文短期内就会过期
       * 也就是说那个内存空间里面的内容无了，但是this.context还保存着那个内存地址，使jvm无法释放掉没用的内存，就会造成内存泄漏 */

        this.context=context.getApplicationContext();

        //初始化播放器
        player = new MediaPlayer();

        //设置播放器监听
        initListeners();
    }

    /**
     * 设置播放器监听
     */
    private void initListeners() {
        //设置播放器准备完毕监听器
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /**
            * 播放器准备开始播放
            *
            * 这⾥可以获取到⾳乐时⻓
            * 如果是视频还能获取到视频宽⾼等信息
            *
            */
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtil.d(TAG,"onPrepared");

                //将总进度保存到音乐对象
                data.setDuration(mp.getDuration());

                //回调监听器
                ListUtil.eachListener(listeners,listener -> listener.onPrepared(mp,data));
            }
        });
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

    @Override
    public void play(String uri, Song data) {
        try{
            //保存音乐对象
            this.data=data;

            //释放原来的播放器
            player.reset();

            //设置数据源
            player.setDataSource(uri);

            //同步准备
            //应该要使用异步
            player.prepare();

            //回调监听器
            publishPlayingStatus(data);

            //开始播放
            player.start();
        }catch ( Exception e){
            e.printStackTrace();
            //todo 处理错误
        }finally {
            //todo 释放资源
        }
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if(isPlaying()){
            player.pause();

            /*for (MusicPlayerListener listener: listeners) {
                listener.onPaused(data);
            }*/
            //使用listUtil工具类
            ListUtil.eachListener(listeners, listener -> listener.onPaused(data));
        }
    }

    @Override
    public void resume() {
        if(!player.isPlaying()){
            player.start();

            publishPlayingStatus(data);
        }
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    @Override
    public Song getData() {
        return data;
    }

    private void publishPlayingStatus(Song data) {
        /*for (MusicPlayerListener listener : listeners) {
            listener.onPlaying(data);
        }*/

        //使用listUtil工具类
        ListUtil.eachListener(listeners, new Consumer<MusicPlayerListener>() {
            @Override
            public void accept(MusicPlayerListener listener) {
               listener.onPlaying(data);
            }
        });
    }
}
