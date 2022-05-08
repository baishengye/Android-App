package com.bo.cloudmusic.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Handle工具类
 */
public class HandlerUtil {

    /*private static Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Constant.MESSAGE_PROGRESS:
                    //播放进度回调
                    break;
            }
        }
    };*/

    /**
     * 判断是否是主线程
     * @return
     */
    public static boolean isMainThread(){
        return Looper.getMainLooper()==Looper.myLooper();
    }

    /**
     * 发送消息
     * @param what
     *//*
    public static void sendMessage(int what) {
        handler.obtainMessage(what).sendToTarget();
    }

    *//**
     * 获取handle
     * @return
     *//*
    public static Handler getHandler() {
        return handler;
    }*/
}
