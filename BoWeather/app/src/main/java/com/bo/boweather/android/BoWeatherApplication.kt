package com.bo.boweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BoWeatherApplication:Application() {
    companion object{
        const val TOKEN="TQOaxyctlrtIKNvF"//唯一的彩云天气的令牌，获取数据用

        //伴生匿名单例类，也就是说一个application实例中只会有一个这个类，就让整个APP只会有一个全局Context
        @SuppressLint("StaticFieldLeak")//把context注解成静态的
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate();
        context=applicationContext
    }
}
