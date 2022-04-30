package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.ToastUtil;

import butterknife.OnClick;

public class SettingActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @OnClick(R.id.bt_logout)
    public void onLogoutClick(){
        //退出
        //ToastUtil.errorShortToast("onLogoutClick");
        AppContext.getInstance().logout();
    }
}