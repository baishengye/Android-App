package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.R;

public class MusicPlayerActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        //状态栏透明
        lightStatusBar();
    }

    /**
     * 启动方法
     */
    public static void start(Activity activity){
        Intent intent=new Intent(activity,MusicPlayerActivity.class);
        activity.startActivity(intent);
    }
}