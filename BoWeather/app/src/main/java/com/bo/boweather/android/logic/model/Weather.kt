package com.bo.boweather.android.logic.model

//天气这个类把实时天气和天气预报封装起来，显示再一个页面里面
data class Weather(val realtime: RealtimeResponse.Realtime,val daily: DailyResponse.Daily) {
}