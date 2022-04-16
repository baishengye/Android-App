package com.bo.cloudmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.bo.cloudmusic.databinding.ActivitySplashBinding;

/**
 * 启动界面
 */
public class SplashActivity extends AppCompatActivity {
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
        Log.d(TAG,"next");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());

        //设置界面全屏
        //获取屏幕view
        View decorView = getWindow().getDecorView();
        //判断版本
        if(Build.VERSION.SDK_INT>11&&Build.VERSION.SDK_INT<19){
            //11~18版本
            decorView.setSystemUiVisibility(View.GONE);//gone是不显示
        }else if(Build.VERSION.SDK_INT>=19){
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |//隐藏导航栏
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |//从状态栏下拉半透明悬浮显示一会二状态栏和导航栏
                    View.SYSTEM_UI_FLAG_FULLSCREEN;//全屏

            decorView.setSystemUiVisibility(options);
        }


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
}