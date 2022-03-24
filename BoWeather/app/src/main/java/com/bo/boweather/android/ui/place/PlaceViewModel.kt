package com.bo.boweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bo.boweather.android.logic.Repository
import com.bo.boweather.android.logic.model.Place

class PlaceViewModel: ViewModel() {
    private val searchLiveData= MutableLiveData<String>()//存放的字符串是城市的名称

    val placeList=ArrayList<Place>()//用于缓存在界面上显示的城市信息

    val placeLiveData= Transformations.switchMap(searchLiveData){ query->
        Repository.searchPlaces(query)//根据城市的名称来查询
    }

    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}