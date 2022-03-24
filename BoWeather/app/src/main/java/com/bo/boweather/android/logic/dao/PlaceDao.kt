package com.bo.boweather.android.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.bo.boweather.android.BoWeatherApplication
import com.bo.boweather.android.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    private fun sharedPreferences(): SharedPreferences {
        //获取SharedPreferences对象
        return BoWeatherApplication.context.getSharedPreferences("bo_weather", Context.MODE_PRIVATE)
    }
    fun savePlace(place: Place){
        //把place对象通过Gson对象转化成Json字符串，存到sharedPreferences中
        sharedPreferences().edit{
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        //从sharedPreferences中获取json字符串用Gson转化成Place对象
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    //判断sharedPreferences中是否有Place已经存储
    fun isPlaceSaved()= sharedPreferences().contains("place")
}