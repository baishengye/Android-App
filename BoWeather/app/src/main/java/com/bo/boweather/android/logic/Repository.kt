package com.bo.boweather.android.logic

import androidx.lifecycle.liveData
import com.bo.boweather.android.logic.model.Place
import com.bo.boweather.android.logic.network.BoWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    /*liveData()函数是lifecycle-livedata-ktx库中提供的一个功能,他能自动构建一并返回一个liveData对象,
    然后在他的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数中调用任意的挂起函数*/
    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
        //由于网络请求是无法在主线程中运行的，所以我们用Dispatchers.IO把liveData代码块中的逻辑求换到子线程中运行
        val result=try {
            val placeResponse= BoWeatherNetwork.searchPlaces(query)//发起请求接收数据
            if(placeResponse.status=="ok"){//如果请求的结果是成功的话
                val places=placeResponse.places///就把请求的结果数据保存下来
                Result.success(places)//把成功获取的城市数据列表包装起来
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))//如果没成功就把异常信息包装起来
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)//如果没成功就把异常信息包装起来
        }
        emit(result)//类似调用LiveData.setValue()，通知数据变化相当于把result设置到livedata中
    }
}