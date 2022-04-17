package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.cloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginOrRegisterActivity extends BaseCommonActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

    }

    /**
     * 点击登录按钮
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_login)
    public void onLoginClick(){
        startActivity(LoginActivity.class);
    }

    /**
     * 点击注册按钮
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        startActivity(RegisterActivity.class);
    }
}