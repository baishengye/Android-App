package com.bo.cloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.Consumer;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.HandlerUtil;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放器的具体实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager,MediaPlayer.OnCompletionListener {

    private static final String TAG = "MusicPlayerManagerImpl";

    /**
     * 单例
     */
    private static volatile MusicPlayerManagerImpl instance;

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
    private MediaPlayer player;

    /**
     * 播放监听器的集合
     */
    List<MusicPlayerListener> listeners = new ArrayList<>();

    /**
     * 定时器任务
     */
    private TimerTask timerTask;

    /**
     * 定时器
     */
    private Timer timer;

    private MusicPlayerManagerImpl(Context context) {
        /* context是活动的上下文
         *context.getApplicationContext()是整个程序的上下文
         * 如果this.context=context的话，那么单例里面就会保存活动的上下文，但活动的上下文短期内就会过期
         * 也就是说那个内存空间里面的内容无了，但是this.context还保存着那个内存地址，使jvm无法释放掉没用的内存，就会造成内存泄漏 */

        this.context = context.getApplicationContext();

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
                LogUtil.d(TAG, "onPrepared");

                //将总进度保存到音乐对象
                data.setDuration(mp.getDuration());

                //回调监听器
                ListUtil.eachListener(listeners, listener -> listener.onPrepared(mp, data));
            }
        });

        //设置播放完毕监听器
        player.setOnCompletionListener((MediaPlayer.OnCompletionListener) this);
    }

    /**
     * 双检锁获取单例
     *
     * @param context
     * @return
     */
    public static MusicPlayerManagerImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (MusicPlayerManagerImpl.class) {
                if (instance == null) {
                    instance = new MusicPlayerManagerImpl(context);
                }
            }
        }
        return instance;
    }

    //音乐播放管理器的接口方法
    @Override
    public void play(String uri, Song data) {
        try {
            //保存音乐对象
            this.data = data;

            //释放原来的播放器
            player.reset();

            //设置数据源
            player.setDataSource(uri);

            //同步准备
            //应该要使用异步
            player.prepare();

            //回调监听器
            publishPlayingStatus(data);

            //启动播放进度通知
            startPublishProgress();

            //开始播放
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
            //todo 处理错误
        } finally {
            //todo 释放资源
        }
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if (isPlaying()) {
            player.pause();

            /*for (MusicPlayerListener listener: listeners) {
                listener.onPaused(data);
            }*/
            //使用listUtil工具类
            ListUtil.eachListener(listeners, listener -> listener.onPaused(data));

            //停止播放音乐的时候停止进度通知
            stopPublishProgress();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            player.start();

            publishPlayingStatus(data);

            //开始播放的时候启动进度通知
            startPublishProgress();
        }
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }

        //启动进度通知
        startPublishProgress();
    }

    @Override
    public Song getData() {
        return data;
    }

    @Override
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    @Override
    public void setLooping(boolean looping) {
        player.setLooping(looping);
    }
    //音乐播放管理器的接口方法

    /**
     * 播放完毕了回调
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //回调监听器
        ListUtil.eachListener(listeners, listener -> listener.onCompletion(mp));
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

    /**
     * 开始分发当前音乐播放的进度
     */
    private void startPublishProgress() {
        if(isEmptyListeners()){
            //没有进度回调就不启动
            return;
        }

        if(!isPlaying()){
            //没有播放音乐就不用启动对音乐播放进度的监听
            return;
        }

        if (timerTask != null) {
            //如果已经启动了
            return;
        }

        //创建一个任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //如果没有监听器就停止定时器
                if (isEmptyListeners()) {
                    stopPublishProgress();
                    return;
                }

                LogUtil.d(TAG, "timer task start");

                //这⾥是⼦线程
                //不能直接操作UI
                //为了⽅便外部
                //在内部就切换到主线程
                //HandlerUtil.getHandler().obtainMessage(MESSAGE_PROGRESS).sendToTarget();
                handler.obtainMessage(Constant.MESSAGE_PROGRESS).sendToTarget();
            }
        };

        //创建一个定时器
        timer=new Timer();

        //启动⼀个持续的任务
        //16毫秒
        //为什么是16毫秒？
        //因为后⾯我们要实现卡拉OK歌词
        //为了画⾯的连贯性
        //应该保持1秒60帧
        //所以1/60；就是⼀帧时间
        //如果没有卡拉OK歌词
        //那么每秒钟刷新⼀次即可
        timer.schedule(timerTask, 0, Constant.DEFAULT_TIME);
    }

    /**
     * 是否没有进度监听器
     * @return
     */
    private boolean isEmptyListeners() {
        return listeners.size() == 0;
    }

    /**
     * 停止播放进度通知
     */
    private void stopPublishProgress() {
        //停止定时器任务
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }

        //停止定时器
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    /**
     * 创建一个Handler,用于将事件转换到主线程中
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Constant.MESSAGE_PROGRESS:
                    //播放请求回调

                    //将进度设置到音乐对象
                    data.setProgress(player.getCurrentPosition());

                    //回调监听
                    ListUtil.eachListener(listeners,listener -> listener.onProgress(data));
            }
        }
    };
}
