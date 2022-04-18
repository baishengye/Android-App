package com.bo.cloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.bo.cloudmusic.utils.ToastUtil;

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

        //初始化第三方toasty工具类
        Toasty.Config.getInstance().apply();

        //初始化Toast工具类
        ToastUtil.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //初始化MultiDex
        MultiDex.install(base);
    }
}
