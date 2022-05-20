package com.bo.cloudmusic.utils;

import android.content.Context;

import com.bo.cloudmusic.domain.Song;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
     * 保存音乐
     * @param song
     */
    public void saveSong(Song song){
        //开启(获得)数据库实例
        Realm realm = getInstance();

        //todo 详细操作

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
}
