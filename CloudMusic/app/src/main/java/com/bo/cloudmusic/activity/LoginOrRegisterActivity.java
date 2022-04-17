package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.cloudmusic.R;

public class LoginOrRegisterActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button bt_login;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //显示亮色状态栏
        //显示亮⾊状态栏
        lightStatusBar();

        //登录按钮
        bt_login= findViewById(R.id.bt_login);

        //注册按钮
        bt_register= findViewById(R.id.bt_register);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.bt_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}