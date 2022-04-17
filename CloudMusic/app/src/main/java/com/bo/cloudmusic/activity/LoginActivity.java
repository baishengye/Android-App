package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.bo.cloudmusic.R;

import butterknife.BindView;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //初始化ToolBar
        setSupportActionBar(toolbar);
    }


}