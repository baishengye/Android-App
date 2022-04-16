package com.bo.cloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}