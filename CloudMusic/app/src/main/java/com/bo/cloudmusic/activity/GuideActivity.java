package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.databinding.ActivityGuideBinding;

public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private ActivityGuideBinding activityGuideBinding;
    private Button btLoginOrRegister;
    private Button btEnter;

    @Override
    protected void initViews() {
        super.initViews();
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btLoginOrRegister = activityGuideBinding.btLoginOrRegister;
        btEnter = activityGuideBinding.btEnter;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btLoginOrRegister.setOnClickListener(this);
        btEnter.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuideBinding=ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(activityGuideBinding.getRoot());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_or_register:
                Toast.makeText(this,"点击了登录注册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_enter:
                Toast.makeText(this,"点击了体验",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}