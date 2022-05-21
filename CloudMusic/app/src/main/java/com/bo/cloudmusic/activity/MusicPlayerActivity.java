package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.R;

import java.net.InetSocketAddress;

public class MusicPlayerActivity extends BaseMusicPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }

    /**
     * 启动方法
     */
    public static void start(Activity activity){
        Intent intent=new Intent(activity,MusicPlayerActivity.class);
        activity.startActivity(intent);
    }
}