package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.LogUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseTitleActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.bt_login)
    public void onLoginClick(Button view){
        LogUtil.d(TAG,"点击登录");

        String username = et_username.getText().toString().trim();
        //如果username字符串是空的
        if(StringUtils.isAllBlank(username)){
            LogUtil.w(TAG,"点击登录");
            Toast.makeText(getMainActivity(),R.string.enter_username,Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_password.getText().toString().trim();
        if(StringUtils.isAllBlank(password)){
            LogUtil.w(TAG,"点击登录");
            Toast.makeText(getMainActivity(),R.string.enter_password,Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        LogUtil.d(TAG,"点击登录");
    }
}