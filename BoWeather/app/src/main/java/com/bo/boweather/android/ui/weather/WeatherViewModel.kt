package com.bo.boweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bo.boweather.android.logic.Repository
import com.bo.boweather.android.logic.model.Location

class WeatherViewModel: ViewModel() {
    private val localLiveData= MutableLiveData<Location>()

    var locationLng=""
    var locationlat=""
    var placeName=""

    val weatherLiveData= Transformations.switchMap(localLiveData){ location->
        Repository.refreshWeather(location.lng,location.lat)
    }

    /***
     * 一旦localLivaData中的数据被外部用refreshWeather()刷新，也就是点击的位置发生变化，switchMap实时监控感应到，
     * 然后通过Repository去请求新的地址的天气信息，获取的天气信息会通过weatherLiveData展示给外界
     */

    fun refreshWeather(lng:String,lat:String){
        localLiveData.value= Location(lng, lat)
    }
}