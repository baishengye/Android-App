package com.bo.cloudmusic.manager.impl;

import android.content.Context;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ResourceUtil;

import java.util.LinkedList;
import java.util.List;

public class ListManagerImpl implements ListManager {

    private static final String TAG = "ListManagerImpl";

    /**
     * 列表管理器实例
     */
    private static ListManagerImpl instance;

    /**
     * 音乐播放管理器实例
     */
    private final MusicPlayerManager musicPlayerManager;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 音乐列表
     */
    private List<Song> datum=new LinkedList<>();

    /**
     * 正在播放的音乐
     */
    private Song data;


    /**
     * 是否已经播放过
     */
    private boolean isPlay;

    private ListManagerImpl(Context context) {
        this.context=context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(context.getApplicationContext());
    }

    public static ListManager getInstance(Context context) {
        if(instance==null){
            synchronized (ListManagerImpl.class){
                if(instance==null){
                    instance=new ListManagerImpl(context);
                }
            }
        }
        return instance;
    }

    //对列表中的歌曲操作
    @Override
    public void setDatum(List<Song> datum) {
        LogUtil.d(TAG,"setDatum");

        //清空原来的数据
        this.datum.clear();

        //添加新的数据
        this.datum.addAll(datum);
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return null;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG,"play");

        //标记为播放了
        isPlay=true;

        //保存数据
        this.data=data;

        //播放
        musicPlayerManager.play(ResourceUtil.resourceUri(data.getUri()),data);
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");

        //暂停
        musicPlayerManager.pause();
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");

        if(isPlay){
            //原来已经播放过
            //也就说播放器已经初始化了
            musicPlayerManager.resume();
        } else {
            //到这⾥，是应⽤开启后，第⼀次点继续播放
            //⽽这时内部其实还没有准备播放，所以应该调⽤播放
            play(data);
        }
    }
    //end对列表中的歌曲操作
}
