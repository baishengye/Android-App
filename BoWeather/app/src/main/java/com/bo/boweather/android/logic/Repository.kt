package com.bo.boweather.android.logic

import androidx.lifecycle.liveData
import com.bo.boweather.android.logic.model.Place
import com.bo.boweather.android.logic.model.Weather
import com.bo.boweather.android.logic.network.BoWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    /*liveData()函数是lifecycle-livedata-ktx库中提供的一个功能,他能自动构建一并返回一个liveData对象,
    然后在他的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数中调用任意的挂起函数*/
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        //由于网络请求是无法在主线程中运行的，所以我们用Dispatchers.IO把liveData代码块中的逻辑求换到子线程中运行
        val placeResponse = BoWeatherNetwork.searchPlaces(query)//发起请求接收数据
        if (placeResponse.status == "ok") {//如果请求的结果是成功的话
            val places = placeResponse.places///就把请求的结果数据保存下来
            Result.success(places)//把成功获取的城市数据列表包装起来
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))//如果没成功就把异常信息包装起来
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                /*在async函数中发起请求，然后调用他的await()函数，就会有一个效果
                   只有网络请求发出后这个协程就会挂起，直到获取到结果后协程才会启动*/
                BoWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                BoWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtimeResponse status is ${realtimeResponse.status}" +
                                "dailyResponse is ${dailyResponse.status}"
                    )
                )
            }
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
    //值得注意的是liveData()函数中是由挂起函数的上下文的，但是一旦到lambda代码块中就没有挂起函数的上下文，也就是说
        //封装到函数中之后就不能使用await()了，所以我们需要把block定义成一个挂起函数
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}