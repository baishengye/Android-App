package com.bo.boweather.android.logic.network

import com.bo.boweather.android.BoWeatherApplication
import com.bo.boweather.android.logic.model.DailyResponse
import com.bo.boweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//通过经纬度来获取地区的信息
interface WeatherService {
    @GET("v2.5/${BoWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String, @Path("lat") lat:String): Call<RealtimeResponse>
    //根据@Path注解把经纬度参数填充到路径里面，请求返回的数据是一个json文件，然后回调Call并且gson解析到对应的数据模型里面

    @GET("v2.5/${BoWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat") lat:String):Call<DailyResponse>
}