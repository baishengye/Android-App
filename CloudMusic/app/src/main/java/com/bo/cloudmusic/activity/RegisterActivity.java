package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.BaseModel;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.StringUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseCommonActivity {

    private static final String TAG = "RegisterActivity";
    /**
     * 昵称输入框
     */
    @BindView(R.id.et_nickname)
    EditText et_nickname;

    /**
     * 电话输入框
     */
    @BindView(R.id.et_phone)
    EditText et_phone;

    /**
     * 邮件输入框
     */
    @BindView(R.id.et_email)
    EditText et_email;

    /**
     * 密码输入框
     */
    @BindView(R.id.et_password)
    EditText et_password;

    /**
     * 确认密码输入框
     */
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * 监听按钮
     */
    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        //获取昵称
        String nickname=et_nickname.getText().toString().trim();
        if(StringUtils.isBlank(nickname)){
            ToastUtil.errorShortToast(R.string.enter_nickname);
            return;
        }

        //判断昵称格式
        if(!StringUtil.isNickname(nickname)){
            ToastUtil.errorShortToast(R.string.error_nickname_format);
            return;
        }

        //获取电话
        String phone=et_phone.getText().toString().trim();
        if(StringUtils.isBlank(phone)){
            ToastUtil.errorShortToast(R.string.enter_phone);
            return;
        }

        //判断电话格式
        if(!StringUtil.isPhone(phone)){
            ToastUtil.errorShortToast(R.string.error_phone_format);
            return;
        }

        //获取邮件
        String email=et_email.getText().toString().trim();
        if(StringUtils.isBlank(email)){
            ToastUtil.errorShortToast(R.string.enter_email);
            return;
        }

        //判断邮箱格式
        if(!StringUtil.isEmail(email)){
            ToastUtil.errorShortToast(R.string.error_email_format);
            return;
        }

        //获取邮箱
        String password=et_password.getText().toString().trim();
        if(StringUtils.isBlank(password)){
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        //判断邮箱格式
        if(!StringUtil.isPassword(password)){
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //获取邮箱
        String confirmPassword=et_confirm_password.getText().toString().trim();
        if(StringUtils.isBlank(confirmPassword)){
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        //判断邮箱格式
        if(!StringUtil.isPassword(confirmPassword)){
            ToastUtil.errorShortToast(R.string.error_confirm_password_format);
            return;
        }

        //判断密码和确认密码是否一样
        if(!password.equals(confirmPassword)){
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        User user = new User();
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        Api.getInstance()
                .register(user)
                .subscribe(new HttpObserver<DetailResponse<BaseModel>>(){
                    @Override
                    public void onSucceeded(DetailResponse<BaseModel> data) {
                        LogUtil.d(TAG,"onSucceeded:"+data.getData().getId());

                        //TODO 自动登录
                    }
                });

    }
}