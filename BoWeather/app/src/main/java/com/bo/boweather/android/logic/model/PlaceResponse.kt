package com.bo.boweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status:String,val places:List<Place>) {
    /***
     * status:请求的状态
     * places:位置
     */
}

data class Place(val name: String, val location:Location,
                 @SerializedName("formatted_address") val address:String){
    /***
     * name:位置名称
     * location:位置的经纬度
     * @SerializedName("formatted_address") address:位置的地址,
     * 可能请求返回的字段json命名和kotlin命名规范不一样，用注解的方式给json字段和kotlin字段建立一个映射关系
     */

}

data class Location(val lng:String,val lat:String){
    /***
     * lng:位置的经度
     * lat:位置的纬度
     */
}