package com.bo.cloudmusic.api;

import android.media.browse.MediaBrowser;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ⽹络请求接⼝包装类
 * 对外部提供⼀个和框架⽆关的接⼝
 */
public class Api {
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
            okHttpClientBuilder.addInterceptor(new ChuckerInterceptor(AppContext.getContext()));
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
     * 歌单详情
     */
    public Observable<DetailResponse<Session>> login(User data){
        return service.login(data)
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
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

        return service.UserDetail(id,data)
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 歌单列表
     */
    public Observable<ListResponse<Sheet>> sheets() {
        return service.sheets()
                .subscribeOn(Schedulers.io())//设置网络请求在子线程中使用
                .observeOn(AndroidSchedulers.mainThread());
    }
}
