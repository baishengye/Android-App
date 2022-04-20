package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.api.Service;
import com.bo.cloudmusic.domain.SheetDetailWrapper;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LoadingUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.StringUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

        //使用重构的Api
        Api.getInstance()
                .sheetDetail("1")
                .subscribe(new Observer<SheetDetailWrapper>() {//订阅请求的歌单详情的数据
                    /**
                     * 开始请求的时候调用，所以可以在这里显示请求对话框
                     */
                    @Override
                    public void onSubscribe(Disposable d) {

                        LoadingUtil.showLoading(getMainActivity());

                        //3秒后隐藏加载提示框
                        //因为显示对话框后无法电视后面的按钮
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingUtil.hideLoading();
                            }
                        },3000);
                    }

                    /**
                     * 请求成功
                     */
                    @Override
                    public void onNext(SheetDetailWrapper sheetDetailWrapper) {
                        LogUtil.d(TAG,"歌单请求成功"+sheetDetailWrapper.getData().getTitle());
                    }

                    /**
                     * 请求失败
                     */
                    public void onError(Throwable e) {
                        //请求失败
                        LogUtil.d(TAG,"request sheet detail failed:"+e.getLocalizedMessage());

                        //判断错误类型
                        if (e instanceof UnknownHostException) {
                            ToastUtil.errorShortToast(R.string.error_network_unkown_host);
                        }else if (e instanceof ConnectException) {
                            ToastUtil.errorShortToast(R.string.error_network_connect);
                        }else if(e instanceof SocketTimeoutException){
                            ToastUtil.errorShortToast(R.string.error_network_timeout);
                        }else if (e instanceof HttpException){
                            HttpException exception = (HttpException) e;
                            int code = exception.code();
                            if (code == 401) {
                                ToastUtil.errorShortToast(R.string.error_network_not_auth);
                            } else if (code == 403) {
                                ToastUtil.errorShortToast(R.string.error_network_not_permission);
                            } else if (code == 404) {
                                ToastUtil.errorShortToast(R.string.error_network_not_found);
                            } else if (code >= 500) {
                                ToastUtil.errorShortToast(R.string.error_network_server);
                            } else {
                                ToastUtil.errorShortToast(R.string.error_network_unkown);
                            }
                        } else{
                            ToastUtil.errorShortToast(R.string.error_network_unkown);
                        }
                    }

                    /**
                     * 请求成功结束时会显示，但是请求失败结束时不会显示
                     */
                    @Override
                    public void onComplete() {

                    }
                });

        /*String username = et_username.getText().toString().trim();
        //如果username字符串是空的
        if(StringUtils.isAllBlank(username)){
            LogUtil.w(TAG,"点击登录");
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        String password = et_password.getText().toString().trim();
        if(StringUtils.isAllBlank(password)){
            LogUtil.w(TAG,"点击登录");
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        //如果输入的用户名既不是邮箱也不是电话
        if(!(StringUtil.isPhone(username)||StringUtil.isEmail(username))){
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //如果输入的密码格式错误
        if(!(StringUtil.isPassword(password))){
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }*/
    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        LogUtil.d(TAG,"点击登录");
    }
}