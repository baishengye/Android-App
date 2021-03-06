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
        RealmResults<SongLocal> songLocals = queryPlayListSongLocal(realm);

        List<Song> songs = toSongs(songLocals);

        //关闭数据库
        realm.close();

        return songs;
    }

    /**
     * 查询本地播放列表音乐
     * @param realm
     * @return
     */
    private RealmResults<SongLocal> queryPlayListSongLocal(Realm realm) {
        return realm.where(SongLocal.class)
                .equalTo("playList", true)
                .findAll();
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

    /**
     * 删除
     * @param song
     */
    public void deleteSong(Song song) {
        //获取数据库对象
        Realm realm = getInstance();

        //先查询到数据在删除
        SongLocal songLocal = realm.where(SongLocal.class)
                .equalTo("playList", true)
                .and()
                .equalTo("id", song.getId())
                .findFirst();

        /*//在事务中删除(回调方式自动在事务中进行)
        realm.executeTransaction(it-> {
            assert songLocal != null;
            songLocal.deleteFromRealm();
        });*/

        //设置字段模拟删除(数据库中还有这个数据但是标识为不存在)
        realm.executeTransaction(it->{
            assert songLocal != null;
            songLocal.setPlayList(false);
        });

        //关闭数据库
        realm.close();
    }

    /**
     * 删除数据库中所有列表音乐
     */
    public void deleteAll() {
        //获取数据库实例
        Realm realm = getInstance();

        //查询所有播放列表数据
        RealmResults<SongLocal> songLocals = queryPlayListSongLocal(realm);

        //在事务中删除播放列表的音乐
        realm.executeTransaction(it->{
            for (SongLocal songLocal:songLocals) {
                //更改播放列表标志
                songLocal.setPlayList(false);
            }
        });

        //关闭数据库
        realm.close();
    }

}
