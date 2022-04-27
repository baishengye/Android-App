package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.BaseResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.StringUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseLoginActivity {

    private static final String TAG = "ForgetPasswordActivity";

    private String phone;
    private String email;
    /**
     * ⽤户名输⼊框
     */
    @BindView(R.id.et_username)
    EditText et_username;
    /**
     * 验证码
     */
    @BindView(R.id.et_code)
    EditText et_code;
    /**
     * 发送验证码按钮
     */
    @BindView(R.id.bt_send_code)
    Button bt_send_code;
    /**
     * 密码
     */
    @BindView(R.id.et_password)
    EditText et_password;
    /**
     * 确认密码
     */
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    /**
     * 发送验证码按钮点击
     */
    @OnClick(R.id.bt_send_code)
    public void onSendCodeClick() {
        LogUtil.d(TAG, "onSendCodeClick");
    }

    /**
     * 重置密码按钮点击
     */
    @OnClick(R.id.bt_forget_password)
    public void onResetPasswordClick() {
        LogUtil.d(TAG, "onResetPasswordClick");

        //获取⽤户
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)) {
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        //如果⽤户名
        //不是⼿机号也不是邮箱
        //就是格式错误
        if (!(StringUtil.isPhone(username) || StringUtil.isEmail(username))) {
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //验证码
        String code = et_code.getText().toString().trim();
        if (StringUtils.isBlank(code)) {
            ToastUtil.errorShortToast(R.string.enter_code);
            return;
        }

        //验证码格式
        if (!StringUtil.isCode(code)) {
            ToastUtil.errorShortToast(R.string.error_code_format);
            return;
        }

        //获取密码
        String password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(password)) {
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        //密码格式
        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //获取确认密码
        String confirmPassword = et_confirm_password.getText().toString().trim();
        if (StringUtils.isBlank(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        //判断密码和确认密码是否⼀样
        if (!password.equals(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        //判断是⼿机号还是邮箱
        if (StringUtil.isPhone(username)) {
            //⼿机号
            phone = username;
        } else {
            //邮箱
            email = username;
        }

        //将信息设置到对象上
        User user=new User();
        user.setCode(code);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        Api.getInstance().resetPassword(user)
                .subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        //重置成功
                        //LogUtil.d(TAG,"onSucceeded");
                        //成功就直接登录进去
                        login(phone,email,password);
                    }
                });

    }
}