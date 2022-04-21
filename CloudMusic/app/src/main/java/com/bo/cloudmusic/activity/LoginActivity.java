package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.api.Service;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.SheetDetailWrapper;
import com.bo.cloudmusic.domain.SheetListWrapper;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.listener.ObserverAdapter;
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


    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        LogUtil.d(TAG,"点击登录");
    }
}