package com.bo.boweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object BoWeatherNetwork {
    private val placeService=ServiceCreator.create(PlaceService::class.java)
    //利用ServiceCreator这个单例类(看作静态)创建出一个placeService的动态代理对象

    suspend fun searchPlaces(query:String)= placeService.searchPlaces(query).await()
    //发起搜索城市的请求，searchPlaces是一个挂起函数
    //暂停函数是可以控制启动，暂停和恢复函数。
    // 关于挂起函数要记住的最重要的一点是，仅允许从协程或另一个挂起函数。
    //这个函数的流程就是:
    //发起网络请求之后await就会把当前的协程阻塞，searchPlaces就会挂起，当await中获取到数据之后就会恢复当前协程的执行


    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if(body!=null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}