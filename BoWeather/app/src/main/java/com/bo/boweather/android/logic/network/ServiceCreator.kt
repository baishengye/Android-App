package com.bo.boweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL="https://api.caiyunapp.com/"

    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)//根路径
        .addConverterFactory(GsonConverterFactory.create())//指定解析时的转换库(gson解析json字符串)
        .build()

    fun<T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)//创建接口的动态代理对象

    inline fun <reified T> create():T= create(T::class.java)//内联函数+泛型实化创建接口的动态代理对象
}