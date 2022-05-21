package com.bo.cloudmusic.api;

import androidx.annotation.NonNull;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.domain.Ad;
import com.bo.cloudmusic.domain.BaseModel;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.BaseResponse;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.PreferencesUtil;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ⽹络请求接⼝包装类
 * 对外部提供⼀个和框架⽆关的接⼝
 */
public class Api {
    private static final String TAG = "Api";
    /**
     * Api单例字段
     */
    private static Api instance;

    /**
     * service单例
     */
    private final Service service;

    /**
     * 懒汉线程安全单例返回当前对象的唯⼀实例
     */
    public static Api getInstance() {
        if(instance==null){
            synchronized (Api.class){
                if(instance==null){
                    instance=new Api();
                }
            }
        }
        return instance;
    }

    public Api() {
        //初始化一个okhHttp
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();


        //请求公共参数
        okHttpClientBuilder.addNetworkInterceptor(new Interceptor() {
            //使用拦截器

            /**
             * 请求返回数据的时候调用
             * @param chain
             * @return
             * @throws IOException
             */
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                /*okHttp去网络请求是一层一层的进行的,而Chain就是这一层一层连起来的链路
                 * 叫做拦截器链*/

                //获取到偏好数据工具类
                PreferencesUtil sp = PreferencesUtil.getInstance(AppContext.getInstance());

                //获取到request
                Request request = chain.request();

                if(sp.isLogin()){
                    //已经登录了

                    //获取出用户的id和token
                    String user=sp.getUserId();
                    String session=sp.getSession();

                    //打印日志方便调试
                    LogUtil.d(TAG,"user:"+user+" \n session:"+session);

                    //将用户userid和session设置到请求头
                    request = request.newBuilder()
                            .addHeader("User", user)
                            .addHeader("Authorization", session)
                            .build();
                }

                //继续进行请求
                return chain.proceed(request);
            }
        });

        if(LogUtil.isDebug){
            //只有在调试模式下进行

            //创建OkHttp日志拦截器
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();

            //设置日志等级
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

            //添加到网络框架中
            okHttpClientBuilder.addInterceptor(loggingInterceptor);

            //添加Stetho抓包拦截器
            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());

            //添加Chucker实现应用内显示网络请求信息拦截器
            okHttpClientBuilder.addInterceptor(new ChuckerInterceptor(AppContext.getInstance()));
        }

        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())//retrofit使用okHttp客户端
                .baseUrl(Constant.ENDPOINT)//api的地址
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配Rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson来解析得到的json字符串
                .build();//创建retrofit

        //创建service
        service = retrofit.create(Service.class);//通过retrofit来创建Service.class接口对应的实例

    }

    /**
     * 歌单详情
     */
    public Observable<DetailResponse<Sheet>> sheetDetail(String id){
        return service.sheetDetail(id)
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     */
    public Observable<DetailResponse<BaseModel>> register(User data){
        return service.register(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     */
    public Observable<DetailResponse<Session>> login(User data){
        return service.login(data)
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 重置密码
     */
    public Observable<BaseResponse> resetPassword(User data){
        return service.resetPassword(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送信息要求服务器发送短信验证码
     */
    public Observable<DetailResponse<BaseModel>> sendSMSCode(User data){
        return service.sendSMSCode(data)
                .subscribeOn(Schedulers.io())//网络请求在子线程中
                .observeOn(AndroidSchedulers.mainThread());//请求回来的信息回调在android主线程中

    }

    /**
     * 发送信息要求服务器发送邮箱验证码
     */
    public Observable<DetailResponse<BaseModel>> sendEmailCode(User data){
        return service.sendEmailCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     */
    public Observable<DetailResponse<User>> userDetail(String id,String nickname){

        Map<String, String> data = new HashMap<>();

        if(StringUtils.isNoneBlank(nickname)){
            data.put(Constant.NICKNAME,nickname);
        }

        return service.userDetail(id,data)
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 无昵称获取用户详情
     */
    public Observable<DetailResponse<User>> userDetail(String id){
        return userDetail(id,null);
    }

    /**
     * 歌单列表
     */
    public Observable<ListResponse<Sheet>> sheets() {
        return service.sheets()
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 所有单曲
     */
    public Observable<ListResponse<Song>> songs(){
        return service.songs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 单曲详情
     */
    public Observable<DetailResponse<Song>> songDetail(String id){
        return service.songDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 广告单曲
     */
    public Observable<ListResponse<Ad>> ads(){
        return service.ads()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 收藏歌单
     * @param id  歌单id
     * @return 成功就是null
     */
    public Observable<retrofit2.Response<Void>> collect(String id){
        return service.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消收藏歌单
     * @param id
     * @return
     */
    public Observable<retrofit2.Response<Void>> deleteCollect(String id){
        return service.deleteCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
