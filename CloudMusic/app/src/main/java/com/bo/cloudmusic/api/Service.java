package com.bo.cloudmusic.api;

import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.SheetDetailWrapper;
import com.bo.cloudmusic.domain.SheetListWrapper;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * ⽹络接⼝配置
 * <p>
 * 之所以调⽤接⼝还能返回数据
 * 是因为Retrofit框架内部处理了
 * 这⾥不讲解原理
 * 在《详解Retrofit》课程中讲解
 */
public interface Service {
    /**
     * 歌单详情
     * @param id
     * @return
     */
    /*@GET("v1/sheets/{id}")
    Observable<SheetDetailWrapper> sheetDetail(@Path("id") String id);*/
    //RxJava2中的Observable解析成SheetDetailWrapper

    /**
     * 歌单列表
     */
    /*@GET("v1/sheets")
    Observable<SheetListWrapper> sheets();*/

    /**
     * 歌单详情
     */
    /*@GET("v1/sheets11111111111/{id}")//模拟404错误*/
    @GET("v1/sheets/{id}")
    Observable<DetailResponse<Sheet>> sheetDetail(@Path("id") String id);

    /**
     * 歌单列表
     */
    @GET("v1/sheets")
    Observable<ListResponse<Sheet>> sheets();


    /**
     * 登录返回Session
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User data);

    /**
     * 用户详情
     */
    @GET("v1/users/{id}")
    Observable<DetailResponse<User>> UserDetail(@Path("id") String id, @QueryMap Map<String,String> data);
}
