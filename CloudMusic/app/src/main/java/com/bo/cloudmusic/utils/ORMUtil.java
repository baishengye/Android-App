package com.bo.cloudmusic.utils;

import android.content.Context;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.SongLocal;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * ORM数据库工具类
 * 单例实现
 */
public class ORMUtil {

    private volatile static ORMUtil instance;

    private final Context context;

    /**
     * 偏好设置
     */
    private final PreferencesUtil sp;

    public ORMUtil(Context context) {
        this.context =context.getApplicationContext();

        //初始化偏好设置
        sp = PreferencesUtil.getInstance(this.context);
    }

    /**
     * 获取数据库工具类实例
     * @param context
     * @return
     */
    public static ORMUtil getInstance(Context context) {
        if(instance==null){
            synchronized (ORMUtil.class){
                if(instance==null){
                    instance = new ORMUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 用户退出得时候要销毁数据库对象
     */
    public static void destroy(){
        instance=null;
    }

    /**
     * 保存播放列表
     */
    public void saveAll(List<Song> songs){
        SongLocal songLocal=null;

        //获取数据库实例
        Realm realm = getInstance();

        realm.beginTransaction();


        for (Song song:songs) {
            //要将Song对象转化成SongLocal
            songLocal = song.toSongLocal();

            realm.copyToRealmOrUpdate(songLocal);
        }

        realm.commitTransaction();

        realm.close();
    }

    /**
     * 保存音乐
     * @param song
     */
    public void saveSong(Song song){

        //要将Song对象转化成SongLocal
        SongLocal songLocal = song.toSongLocal();

        //开启(获得)数据库实例
        Realm realm = getInstance();

        //开启事务
        realm.beginTransaction();
        //新增或更新
        realm.copyToRealmOrUpdate(songLocal);
        //提交事务
        realm.commitTransaction();

        //关闭数据库
        realm.close();
    }

    /**
     * 获取数据库操作类实例
     * @return
     */
    private Realm getInstance(){
        //数据库配置
        RealmConfiguration build = new RealmConfiguration.Builder()
                //数据库名称
                //不同的⽤户使⽤不同的数据库⽂件
                //从⽽使⽤多⽤户
                //但让还可以在数据库中保存⽤户Id
                .name(String.format("%s.realm", sp.getUserId()))
                .build();

        //返回一个含有配置的Realm实例
        return Realm.getInstance(build);
    }

    /**
     * 从数据库中查询播放列表
     * @return
     */
    public List<Song> queryPlayList(){
        //获取数据库
        Realm realm = getInstance();

        //查询播放列表
        RealmResults<SongLocal> songLocals = realm.where(SongLocal.class)
                .equalTo("playList", true)
                .findAll();

        List<Song> songs = toSongs(songLocals);

        //关闭数据库
        realm.close();

        return songs;
    }

    /**
     * 把RealmResults<SongLocal>转化成List<Song>
     * @param songLocals
     * @return
     */
    private List<Song> toSongs(RealmResults<SongLocal> songLocals) {
        List<Song> songs=new LinkedList<>();
        for (SongLocal songLocal:songLocals) {
            songs.add(songLocal.toSong());
        }

        return songs;
    }
}
