package com.bo.cloudmusic.utils;

import android.annotation.SuppressLint;

/**
 * 时间相关工具类
 */
public class TimeUtil {
    /**
     * 将毫秒格式化成分钟:秒
     * @param data
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String formatMinuteSecond(int data) {
        if (data==0) return "00:00";

        //转成秒
        data/=1000;

        //计算分钟
        int minute=data/60;

        int second=data%60;

        return String.format("%02d:%02d",minute,second);
    }
}
