package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.event.LoginSuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//注册后结束运行的时候一定要注销
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //注册发布订阅框架
        //注册在哪个界面就会在哪个界面响应这个事件
        EventBus.getDefault().register(this);
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

    /**
     * 登录成功事件
     * 接受该事件的目的是关闭该界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//这个函数在哪种线程中响应(运行)
    public void onLoginSuccessEvent(LoginSuccessEvent loginSuccessEvent){
        finish();
    }
}