package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.cloudmusic.R;

/**
 * 用户详情界面
 */
public class UserDetailActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }
}