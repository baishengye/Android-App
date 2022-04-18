package com.bo.cloudmusic;

import android.app.Application;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends Application {
    /**
     *创建App
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化toasty工具类
        Toasty.Config.getInstance().apply();
    }
}
