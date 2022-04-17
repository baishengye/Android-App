package com.bo.cloudmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 偏好设置工具类
 * 是否登陆了，是否显示引导页面，用户id
 */
public class PreferencesUtil {

    /**
     * 偏好设置⽂件名称
     */
    private static final String NAME = "bo_cloud_music";

    /**
     * PreferenceUtil实例
     */
    private static PreferencesUtil instance;

    /**
     * 是否显示引导界⾯key
     */
    private static final String SHOW_GUIDE = "SHOW_GUIDE";

    /**
     * 上下文
     */
    private Context context;
    private SharedPreferences preference;

    private PreferencesUtil(){}

    private PreferencesUtil(Context context) {
        //保存上下⽂
        this.context=context.getApplicationContext();
        //这样写有内存泄漏
        //因为当前⼯具类不会⻢上释放
        //如果当前⼯具类引⽤了界⾯实例
        //当界⾯关闭后
        //因为界⾯对应在这⾥还有引⽤
        //所以会导致界⾯对象不会被释放

        //获取偏好设置
        preference = this.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 线程安全获取单例
     */
    public static PreferencesUtil getInstance(Context context){
        if(instance==null){
            synchronized (PreferencesUtil.class){
                if(instance==null){
                    instance=new PreferencesUtil(context);
                }
            }
        }

        return instance;
    }

    /**
     * 是否显示引导界⾯
     */
    public boolean isShowGuide() {
        return preference.getBoolean(SHOW_GUIDE,true);
    }

    /**
     * 设置是否显示引导界⾯
     */
    public void setShowGuide(boolean value) {
        preference.edit().putBoolean(SHOW_GUIDE, value).apply();
    }
}