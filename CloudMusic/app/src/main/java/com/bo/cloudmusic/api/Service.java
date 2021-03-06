package com.bo.cloudmusic.api;

import com.bo.cloudmusic.domain.Ad;
import com.bo.cloudmusic.domain.BaseModel;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.SheetDetailWrapper;
import com.bo.cloudmusic.domain.SheetListWrapper;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.response.BaseResponse;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
     * 注册返回id
     */
    @POST("v1/users")
    Observable<DetailResponse<BaseModel>> register(@Body User data);

    /**
     * 登录返回Session
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User data);

    /**
     * 重置密码,BaseResponse就只会接受返回的错误信息,成功其实不会返回信息
     */
    @POST("v1/users/reset_password")
    Observable<BaseResponse> resetPassword(@Body User user);

    /**
     * 发送信息要求服务器发送短信验证码
     */
    @POST("v1/codes/request_sms_code")
    Observable<DetailResponse<BaseModel>> sendSMSCode(@Body User user);

    /**
     * 发送信息要求服务器发送邮箱验证码
     */
    @POST("v1/codes/request_email_code")
    Observable<DetailResponse<BaseModel>> sendEmailCode(@Body User user);

    /**
     * 用户详情
     */
    @GET("v1/users/{id}")
    Observable<DetailResponse<User>> userDetail(@Path("id") String id, @QueryMap Map<String,String> data);

    /**
     * 单曲列表
     */
    @GET("v1/songs")
    Observable<ListResponse<Song>> songs();

    /**
     * 单曲详情
     */
    @GET("v1/songs/{id}")
    Observable<DetailResponse<Song>> songDetail(@Path("id") String id);

    /**
     * 广告列表
     */
    @GET("v1/ads")
    Observable<ListResponse<Ad>> ads();

    /**
     * 收藏歌单
     * Response<Void> 表示成功之后不会有返回值
     * @FormUrlEncoded 指用表单的形式传递数据,如果不写这个就会以json文件的形式来传递
     * @param id  歌单的Id
     * @return
     */
    @FormUrlEncoded
    @POST("v1/collections")
    Observable<Response<Void>> collect(@Field("sheet_id") String id);

    /**
     * 取消收藏歌单
     * Response<Void> 表示成功之后不会有返回值
     * @FormUrlEncoded 指用表单的形式传递数据,如果不写这个就会以json文件的形式来传递
     * @param id  歌单的Id
     * @return
     */
    @DELETE("v1/collections/{id}")
    Observable<Response<Void>> deleteCollect(@Path("id") String id);
}
