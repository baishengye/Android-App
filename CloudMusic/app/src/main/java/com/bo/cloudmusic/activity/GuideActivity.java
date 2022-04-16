package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.databinding.ActivityGuideBinding;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityGuideBinding activityGuideBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuideBinding=ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(activityGuideBinding.getRoot());

        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityGuideBinding.btLoginOrRegister.setOnClickListener(this);
        activityGuideBinding.btEnter.setOnClickListener(this);
    }

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