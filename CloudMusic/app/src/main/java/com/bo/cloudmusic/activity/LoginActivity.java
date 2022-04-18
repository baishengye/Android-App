package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bo.cloudmusic.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseTitleActivity {

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
        Toast.makeText(this,"点击登录",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        Toast.makeText(this,"点击忘记密码",Toast.LENGTH_SHORT).show();
    }
}