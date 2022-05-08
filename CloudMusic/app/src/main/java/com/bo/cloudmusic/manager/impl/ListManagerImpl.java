package com.bo.cloudmusic.manager.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ResourceUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

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
    private final List<Song> datum=new LinkedList<>();

    /**
     * 正在播放的音乐
     */
    private Song data;


    /**
     * 是否已经播放过
     */
    private boolean isPlay;

    /**
     * 循环模式,默认是列表循环
     */
    private int model= Constant.MODEL_LOOP_LIST;

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

    @Override
    public Song previous() {
        return getSong(-1);
    }

    @Override
    public Song next() {
       return getSong(1);
    }

    @Override
    public int changeLoopModel() {
        model++;
        model%=Constant.MODEL_LOOP_RANDOM+1;

        if(model==Constant.MODEL_LOOP_ONE){
            //单曲循环
            //设置到mediaPlayer
            musicPlayerManager.setLooping(true);
        }else {
            //其他模式就关闭单曲循环
            musicPlayerManager.setLooping(false);
        }

        //返回最终的循环模式
        return model;
    }

    @Override
    public int getLoopModel() {
        return model;
    }
    //end对列表中的歌曲操作


    /**
     * next()和previous()的辅助函数
     * @param x -1就是获取上一曲，1就是获取是下一曲
     * @return
     */
    private Song getSong(int x) {
        if(datum.size()==0){
            return null;
        }

        int idx=0;//音乐索引
        switch (model){
            case Constant.MODEL_LOOP_RANDOM:
                //随机循环
                idx=new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐的索引
                idx=datum.indexOf(data);

                if(idx!=-1){
                    //找到了
                    int target=(x==1?datum.size()-1:0);
                    if(idx==target){
                        //最后一首音乐
                        idx=datum.size()-target-1;
                    }else {
                        idx+=x;
                    }
                }else{
                    //如果找不到当前的音乐就要抛出一个异常，让开发者知道然后解决错误
                    throw new IllegalArgumentException("Can't found current song");
                }
                break;
        }
        return datum.get(idx);
    }

}
