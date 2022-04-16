package com.bo.cloudmusic.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.bo.cloudmusic.databinding.ActivitySplashBinding;

/**
 * 启动界面
 */
public class SplashActivity extends BaseCommonActivity {
    /**
     * 下一步常量
     */
    private static final int MESSAGE_NEXT = 100;

    /**
     * 默认延时时间
     */
    private static final long DEFAULT_DELAY_TIME = 3000;
    private static final String TAG = "SplashActivity";
    private ActivitySplashBinding activitySplashBinding;

    /**
     * 创建handle
     * 这样创建有内存泄漏的风险
     * 需要性能优化
     */
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_NEXT:
                    next();
                    break;
            }
        }
    };

    private void next() {
        //Log.d(TAG,"next");
        startActivityAfterFinishThis(GuideActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());

        fullScreen();

        //handle经过一段时间后就发送一条消息
        //在企业中通常会有横夺逻辑处理
        //所以延时一般用maxTime-处理用时
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MESSAGE_NEXT);
            }
        },DEFAULT_DELAY_TIME);
    }

    /**
     * 启动界面
     * @param clazz
     */
    private void starActivity(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}