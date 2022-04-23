package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.HttpUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.GET;

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


        //模拟500错误
        //手动处理错误
        Api.getInstance().userDetail("-1","111111111111111111")
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    public void onSucceeded(DetailResponse<User> data) {
                        ToastUtil.errorShortToast("onSucceeded:"+data.getData());
                    }

                    @Override
                    public boolean onFailed(DetailResponse<User> data, Throwable e) {
                        LogUtil.d(TAG,"onFail:"+e);

                        HttpUtil.handleRequest(data,e);

                        return true;
                    }
                });


        //模拟500错误
        //父类处理错误
        /*Api.getInstance().userDetail("-1","111111111111111111")
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    public void onSucceeded(DetailResponse<User> data) {
                        ToastUtil.errorShortToast("onSucceeded:"+data.getData());
                    }

                    @Override
                    public boolean onFailed(DetailResponse<User> userDetailResponse, Throwable e) {
                        LogUtil.d(TAG,"onFail:"+e);
                        return super.onFailed(userDetailResponse, e);
                    }
                });*/

        //模拟404错误
        /*@GET("v1/sheets11111111111/{id}")//模拟404错误*/
        /*Api.getInstance().sheetDetail("1")
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> data) {
                        ToastUtil.errorShortToast("onSucceeded:"+data.getData().getTitle());
                    }
                });*/

        //模拟500错误
        /*Api.getInstance().userDetail("-1","111111111111111111")
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    public void onSucceeded(DetailResponse<User> data) {
                        ToastUtil.errorShortToast("onSucceeded:"+data.getData());
                    }
                });*/
    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        LogUtil.d(TAG,"点击登录");
    }
}