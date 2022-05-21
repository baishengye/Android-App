package com.bo.cloudmusic.manager.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseUtils;
import android.media.MediaPlayer;
import android.text.format.DateUtils;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.DataUtil;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ORMUtil;
import com.bo.cloudmusic.utils.PreferencesUtil;
import com.bo.cloudmusic.utils.ResourceUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class ListManagerImpl implements ListManager, MusicPlayerListener {

    private static final String TAG = "ListManagerImpl";

    /**
     * 列表管理器实例
     * volatile防止指令重排
     */
    private static volatile ListManagerImpl instance;

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
     * 数据库工具
     */
    private final ORMUtil orm;

    /**
     * 偏好设置工具
     */
    private final PreferencesUtil sp;

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

    /**
     * 最后依次保存的时间
     */
    private long lastTime;

    private ListManagerImpl(Context context) {
        this.context=context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(context.getApplicationContext());

        //添加音乐播放监听
        musicPlayerManager.addMusicPlayerListener(this);

        //初始化数据库工具类
        orm = ORMUtil.getInstance(this.context);

        //初始化偏好设置工具类
        sp = PreferencesUtil.getInstance(this.context);

        //初始化播放列表
        initPlayList();
    }

    /**
     * 初始化播放列表
     */
    private void initPlayList() {
        //查询播放列表
        List<Song> songs = orm.queryPlayList();
        if(!songs.isEmpty()){
            //添加到现在的播放列表
            this.datum.clear();
            this.datum.addAll(songs);

            //获取最后播放音乐的Id
            String lastPlaySongId = sp.getLastPlaySongId();
            if(StringUtils.isNotBlank(lastPlaySongId)){
                //有最后播放的音乐
                //在播放列表中播放
                for (Song song:songs) {
                    if(song.getId().equals(lastPlaySongId)){
                        //找到了
                        data=song;
                        break;
                    }
                }

                if(data==null){
                    //没找到
                    defaultPlaySong();
                }else{
                    //找到了
                    // todo 其他逻辑
                }
            }else{
                defaultPlaySong();
            }
        }
    }

    private void defaultPlaySong() {
        data=datum.get(0);
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

        //将原来数据playList标志设置位false，标识已经不在播放列表中了
        DataUtil.changePlayListFlag(this.datum,false);
        //给数据库添加新的播放列表中的歌曲
        saveAll();

        //清空原来的数据
        this.datum.clear();

        //添加新的数据
        this.datum.addAll(datum);

        //改变播放列表标识
        DataUtil.changePlayListFlag(this.datum,true);
        //给数据库添加新的播放列表中的歌曲
        saveAll();
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return datum;
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

        //保存最后播放音乐的id
        sp.setLastPlaySongId(data.getId());
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
            if(data.getProgress()>0){
                //有播放进度
                //从上一次的位置开始播放
                musicPlayerManager.seekTo((int) data.getProgress());
            }
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

    @Override
    public Song getData() {
        return data;
    }

    @Override
    public void delete(int position) {
        //获取要删除的音乐
        Song song = datum.get(position);

        //要删除的音乐正在播放
        if(song.getId().equals(data.getId())){
            //停止当前播放
            pause();

            //并且播放下一首
            Song nextSong = next();
            while(nextSong.getId().equals(song.getId())){
                if(datum.size()==1){
                    //没有音乐可以播放了
                    data=null;
                    break;
                }
                nextSong=next();
            }
            datum.remove(song);
            if(!datum.isEmpty()) play(nextSong);
        }else{
            //直接删除
            datum.remove(song);

            //从数据库中删除
            orm.deleteSong(song);
        }

    }

    /**
     * 删除列表中的所有音乐
     */
    @Override
    public void deleteAll() {
        if(musicPlayerManager.isPlaying()){
            musicPlayerManager.pause();
        }

        datum.clear();

        //从数据库中删除
        orm.deleteAll();
    }

    @Override
    public void seekTo(int progress) {
        //如果暂停了就继续播放
        if(!musicPlayerManager.isPlaying()){
            resume();
        }

        musicPlayerManager.seekTo(progress);
    }
    //end对列表中的歌曲操作

    /**
     * 保存列表中的所有音乐
     */
    public void saveAll(){
        orm.saveAll(this.datum);
    }


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

    //音乐播放管理器接口
    @Override
    public void onPaused(Song song) {

    }

    @Override
    public void onPlaying(Song song) {

    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {

    }

    @Override
    public void onProgress(Song song) {
        //保存当前⾳乐播放进度
        //因为Android应⽤不太好监听应⽤被杀掉
        //所以这⾥就在这⾥保存
        //真实项⽬肯定需要对不同版本
        //不同⼿机进⾏适配
        //⽽不是采⽤下⾯的⽅法保存
        long currentTimeMillis = System.currentTimeMillis();

        if(currentTimeMillis- lastTime >Constant.SAVE_PROGRESS_TIME){
            //保存数据
            orm.saveSong(song);

            lastTime=currentTimeMillis;
        }
    }

    /**
     * 播放完毕回调
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(Constant.MODEL_LOOP_ONE==model){
            //如果是单曲循环
            //就不会处理了
            //因为我们使⽤了MediaPlayer的循环模式
            //如果使⽤的第三⽅框架
            //如果没有循环模式
            //那就要在这⾥继续播放当前⾳乐
        }else{
            //其他模式
            //播放下⼀⾸⾳乐
            Song data = next();
            if (data != null) {
                play(data);
            }
        }
    }
    //end音乐播放管理器接口
}
