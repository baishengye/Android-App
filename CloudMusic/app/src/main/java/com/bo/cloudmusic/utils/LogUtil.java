package com.bo.cloudmusic.utils;

import android.util.Log;

import com.bo.cloudmusic.BuildConfig;

/**
 * ⽇志⼯具类
 * 对Android⽇志API做⼀层简单的封装
 */
public class LogUtil {
    /**
     * 是否是调试状态
     */
    public static final boolean isDebug = BuildConfig.DEBUG;

    /**
     * 调试级别日志
     */
    public static void d(String tag, String value) {
        if (isDebug) {
            Log.d(tag, value);
        }
    }
}
