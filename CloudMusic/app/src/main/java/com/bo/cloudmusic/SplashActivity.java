package com.bo.cloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bo.cloudmusic.databinding.ActivityMainBinding;
import com.bo.cloudmusic.databinding.ActivitySplashBinding;

/**
 * 启动界面
 */
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding activitySplashBinding;
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


    }
}