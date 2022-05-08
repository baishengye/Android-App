package com.bo.cloudmusic.manager.impl;

import android.content.Context;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;

import java.util.LinkedList;
import java.util.List;

public class ListManagerImpl implements ListManager {

    private static final String TAG = "ListManagerImpl";
    private static ListManagerImpl instance;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 音乐列表
     */
    private List<Song> datum=new LinkedList<>();

    private ListManagerImpl(Context context) {
        this.context=context.getApplicationContext();
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
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return null;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG,"play");
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");
    }
    //end对列表中的歌曲操作
}
