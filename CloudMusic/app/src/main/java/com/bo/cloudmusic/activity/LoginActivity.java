package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Service;
import com.bo.cloudmusic.domain.SheetDetailWrapper;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.StringUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
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

        //测试网络请求

        //初始化一个okhHttp
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())//retrofit使用okHttp客户端
                .baseUrl(Constant.ENDPOINT)//api的地址
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配Rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson来解析得到的json字符串
                .build();//创建retrofit

        //创建service
        Service service = retrofit.create(Service.class);//通过retrofit来创建Service.class接口对应的实例

        //请求歌单详情
        service.sheetDetail("1")
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread())//观察者模式，在android的主线程中观察，UI只能在主线程中使用
                .subscribe(new Observer<SheetDetailWrapper>() {//订阅请求的歌单详情的数据
                    @Override
                    public void onSubscribe(Disposable d) {

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
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG,"歌单请求失败: "+e.getLocalizedMessage());
                    }

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