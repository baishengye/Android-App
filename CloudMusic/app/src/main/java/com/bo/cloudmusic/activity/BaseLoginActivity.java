package com.bo.cloudmusic.activity;

import androidx.annotation.NonNull;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.LogUtil;

/**
 * 登录相关基础功能
 */
public class BaseLoginActivity extends BaseTitleActivity{
    private static final String TAG = "BaseLoginActivity";

    /**
     * 登录
     */
    public void login(String phone,String email,String password){
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        //调用登录接口
        Api.getInstance()
                .login(user)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        onLogin(data.getData());
                    }
                });

    }

    private void onLogin(@NonNull Session data) {
        LogUtil.d(TAG, "onLoginClick success:" + data);

        //把登录成功的事件通知到AppContext
        AppContext.getInstance().login(sp, data);

        //关闭当前界面，并且启动主界面
        startActivityAfterFinishThis(MainActivity.class);
    }
}
