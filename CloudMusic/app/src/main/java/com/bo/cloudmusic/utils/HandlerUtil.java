package com.bo.cloudmusic.utils;

import android.os.Looper;

/**
 * Handle工具类
 */
public class HandlerUtil {
    /**
     * 判断是否是主线程
     * @return
     */
    public static boolean isMainThread(){
        return Looper.getMainLooper()==Looper.myLooper();
    }
}
