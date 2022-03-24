package com.bo.boweather.android.logic.network

import com.bo.boweather.android.BoWeatherApplication
import com.bo.boweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${BoWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    /***
     * 当有类实现接口调用searchPlaces的时候Retrofit会自动发起一条请求，去访问@GET中的路径
     * 搜索的参数中只有query是动态指定的，有@Query注解自动构建到@GET的路径中
     * Call<PlaceResponse>就会把服务器返回的json数据解析成PlaceResponse
     */
}