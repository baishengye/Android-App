package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.cloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginOrRegisterActivity extends BaseCommonActivity implements View.OnClickListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bt_login)
    Button bt_login;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bt_register)
    Button bt_register;

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

        bt_register.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.bt_login:
                startActivity(LoginActivity.class);
                break;*/
            case R.id.bt_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    /**
     * 点击登录按钮
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_login)
    public void onLoginClick(){
        startActivity(LoginActivity.class);
    }
}