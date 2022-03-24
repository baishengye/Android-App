package com.bo.boweather.android.logic.model

import com.google.gson.annotations.SerializedName

//实时天气情况的数据模型
data class RealtimeResponse(val status:String,val result:Result) {
    /*这里把所有的RealtimeResponse中用到的数据模型类都定义在RealtimeResponse里面
    可以避免和其他接口中的数据模型类同名冲突*/
    data class Result(val realtime:Realtime)

    data class Realtime(val skycon:String,val temperature:Float,
                        @SerializedName("air_quality") val airQuality:AirQuality)

    data class AirQuality(val aqi:Aqi)

    data class Aqi(val chn:Float)
}