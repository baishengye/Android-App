package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.R;

/**
 * 简单的播放器实现
 * 主要测试音乐播放相关逻辑
 * 因为黑胶唱片界面的逻辑比较复杂
 * 如果在和播放相关逻辑混一起，不好实现
 * 所有我们可以先使用一个简单的播放器
 * 从而把播放器相关逻辑实现完成
 * 然后在对接的黑胶唱片，就相对来说简单一点
 */
public class SimplePlayerActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    /**
     * 启动界面
     * @param activity
     */
    public static void start(Activity activity) {
        Intent intent=new Intent(activity,SimplePlayerActivity.class);

        activity.startActivity(intent);
    }
}