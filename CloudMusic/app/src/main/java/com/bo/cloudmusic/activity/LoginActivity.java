package com.bo.cloudmusic.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.StringUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseLoginActivity {

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

        ////初始化okhttp
        //OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        //
        ////初始化retrofit
        //Retrofit retrofit = new Retrofit.Builder()
        //        //让retrofit使用okhttp
        //        .client(okhttpClientBuilder.build())
        //
        //        //api地址
        //        .baseUrl(Constant.ENDPOINT)
        //
        //        //适配rxjava
        //        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        //
        //        //使用gson解析json
        //        //包括请求参数和响应
        //        .addConverterFactory(GsonConverterFactory.create())
        //
        //        //创建retrofit
        //        .build();
        //
        ////创建service
        //Service service = retrofit.create(Service.class);

        ////请求歌单详情
        //service.sheetDetail("1")
        //        .subscribeOn(Schedulers.io())
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Observer<SheetDetailWrapper>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            /**
        //             * 请求成功了
        //             *
        //             * @param sheetDetailWrapper
        //             */
        //            @Override
        //            public void onNext(SheetDetailWrapper sheetDetailWrapper) {
        //                LogUtil.d(TAG, "request sheet detail success:" + sheetDetailWrapper.getData().getTitle());
        //            }
        //
        //            /**
        //             * 请求失败
        //             *
        //             * @param e
        //             */
        //            @Override
        //            public void onError(Throwable e) {
        //                e.printStackTrace();
        //                LogUtil.d(TAG, "request sheet detail failed:" + e.getLocalizedMessage());
        //
        //                //判断错误类型
        //                if (e instanceof UnknownHostException) {
        //                    ToastUtil.errorShortToast(R.string.error_network_unknown_host);
        //                } else if (e instanceof ConnectException) {
        //                    ToastUtil.errorShortToast(R.string.error_network_connect);
        //                } else if (e instanceof SocketTimeoutException) {
        //                    ToastUtil.errorShortToast(R.string.error_network_timeout);
        //                } else if (e instanceof HttpException) {
        //                    HttpException exception = (HttpException) e;
        //
        //                    //获取响应码
        //                    int code = exception.code();
        //
        //                    if (code == 401) {
        //                        ToastUtil.errorShortToast(R.string.error_network_not_auth);
        //                    } else if (code == 403) {
        //                        ToastUtil.errorShortToast(R.string.error_network_not_permission);
        //                    } else if (code == 404) {
        //                        ToastUtil.errorShortToast(R.string.error_network_not_found);
        //                    } else if (code > 500) {
        //                        ToastUtil.errorShortToast(R.string.error_network_server);
        //                    } else {
        //                        ToastUtil.errorShortToast(R.string.error_network_unknown);
        //                    }
        //                } else {
        //                    ToastUtil.errorShortToast(R.string.error_network_unknown);
        //                }
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //
        //            }
        //        });

        //////使用重构后的api
        //Api.getInstance()
        //        .sheetDetail("1")
        //        .subscribe(new Observer<SheetDetailWrapper>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //                LogUtil.d(TAG,"onSubscribe");
        //
        //                //显示加载提示框
        //                LoadingUtil.showLoading(getMainActivity());
        //            }
        //
        //            /**
        //             * 请求成功
        //             * @param sheetDetailWrapper
        //             */
        //            @Override
        //            public void onNext(SheetDetailWrapper sheetDetailWrapper) {
        //                LogUtil.d(TAG,"onNext:"+sheetDetailWrapper.getData().getTitle());
        //
        //                //隐藏加载提示框
        //                LoadingUtil.hideLoading();
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                LogUtil.d(TAG,"onError");
        //
        //                //隐藏加载提示框
        //                LoadingUtil.hideLoading();
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                LogUtil.d(TAG,"onComplete");
        //            }
        //        });

        ////测试加载提示框
        //LoadingUtil.showLoading(getMainActivity());
        //
        ////3秒钟后隐藏加载提示框
        ////因为显示后无法点击后面的按钮
        //new Handler().postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        LoadingUtil.hideLoading();
        //    }
        //},3000);

        //请求歌单列表数据
        //Api.getInstance().sheets()
        //        .subscribe(new Observer<SheetListWrapper>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            @Override
        //            public void onNext(SheetListWrapper sheetListWrapper) {
        //                LogUtil.d(TAG, "onNext:" + sheetListWrapper.getData().size());
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //
        //            }
        //        });

        //使用DetailResponse
        //Api.getInstance().sheetDetail("1")
        //        .subscribe(new Observer<DetailResponse<Sheet>>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            @Override
        //            public void onNext(DetailResponse<Sheet> sheetDetailResponse) {
        //                LogUtil.d(TAG, "onNext：" + sheetDetailResponse.getData().getTitle());
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //
        //            }
        //        });

        //使用ListResponse
        //Api.getInstance().sheets()
        //        .subscribe(new Observer<ListResponse<Sheet>>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            @Override
        //            public void onNext(ListResponse<Sheet> sheetListResponse) {
        //                LogUtil.d(TAG, "onNext:" + sheetListResponse.getData().size());
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //
        //            }
        //        });

        //使用ObserverAdapter
        //Api.getInstance().sheetDetail("1")
        //        .subscribe(new ObserverAdapter<DetailResponse<Sheet>>() {
        //            @Override
        //            public void onNext(DetailResponse<Sheet> sheetDetailResponse) {
        //                super.onNext(sheetDetailResponse);
        //                LogUtil.d(TAG, "onNext:" + sheetDetailResponse.getData().getTitle());
        //            }
        //        });

        //使用HttpObserver
        //Api.getInstance().sheetDetail("1")
        //        .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
        //            @Override
        //            public void onSucceeded(DetailResponse<Sheet> data) {
        //                LogUtil.d(TAG, "onSucceeded:" + data.getData().getTitle());
        //            }
        //        });

        ////模拟500错误
        //Api.getInstance().userDetail("-1", "111111111111111111")
        //        .subscribe(new HttpObserver<DetailResponse<User>>() {
        //            @Override
        //            public void onSucceeded(DetailResponse<User> data) {
        //                LogUtil.d(TAG, "onNext:" + data.getData());
        //            }
        //
        //            @Override
        //            public boolean onFailed(DetailResponse<User> data, Throwable e) {
        //                LogUtil.d(TAG,"onFailed:"+e);
        //                return super.onFailed(data, e);
        //            }
        //        });

        ////模拟500错误
        ////手动处理错误
        //Api.getInstance().userDetail("-1", "111111111111111111")
        //        .subscribe(new HttpObserver<DetailResponse<User>>() {
        //            @Override
        //            public void onSucceeded(DetailResponse<User> data) {
        //                LogUtil.d(TAG, "onNext:" + data.getData());
        //            }
        //
        //            @Override
        //            public boolean onFailed(DetailResponse<User> data, Throwable e) {
        //                LogUtil.d(TAG, "onFailed:" + e);
        //
        //                //调用工具类处理错误
        //                HttpUtil.handlerRequest(data, e);
        //
        //                //返回true表示手动处理错误
        //                return true;
        //            }
        //        });

        //测试自动显示加载对话框
        //Api.getInstance().sheetDetail("1")
        //        .subscribe(new HttpObserver<DetailResponse<Sheet>>(getMainActivity(), true) {
        //            @Override
        //            public void onSucceeded(DetailResponse<Sheet> data) {
        //                LogUtil.d(TAG, "onNext:" + data.getData().getTitle());
        //            }
        //        });

        //获取用户名
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)) {
            LogUtil.w(TAG, "onLoginClick username empty");
            //Toast.makeText(getMainActivity(),R.string.enter_username,Toast.LENGTH_SHORT).show();

            //Toasty.error(getMainActivity(),R.string.enter_username,Toasty.LENGTH_SHORT).show();

            ToastUtil.errorShortToast(R.string.enter_username);

            return;
        }

        //如果用户名
        //不是手机号也不是邮箱
        //就是格式错误
        if (!(StringUtil.isPhone(username) || StringUtil.isEmail(username))) {
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //获取密码
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            LogUtil.w(TAG, "onLoginClick password empty");
            //Toast.makeText(getMainActivity(), R.string.enter_password, Toast.LENGTH_SHORT).show();

            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        //判断密码格式
        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //判断是手机号还有邮箱
        String phone = null;
        String email = null;

        if (StringUtil.isPhone(username)) {
            //手机号
            phone = username;
        } else {
            //邮箱
            email = username;
        }

        User user = new User();

        //这里虽然同时传递了手机号和邮箱
        //但服务端登录的时候有先后顺序
        //调用父类的登录方法
        login(phone,email,password);
    }

    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick(Button view){
        LogUtil.d(TAG,"点击登录");

        //跳转到找到密码界面
        startActivity(ForgetPasswordActivity.class);
    }
}