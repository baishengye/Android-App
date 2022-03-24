package com.bo.boweather.android.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bo.boweather.android.R
import com.bo.boweather.android.logic.model.Weather
import com.bo.boweather.android.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView=window.decorView//获取到当前Activity的DecorView(Android视图树的根节点视图)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE//布局回显示在状态栏上面
        window.statusBarColor= Color.TRANSPARENT//状态栏变透明

        setContentView(R.layout.activity_weather)

        //首先从intent 中取出地区的经纬度的名称
        if(viewModel.locationLng.isEmpty()){//只有viewModel中的位置信息是空的时候才会更新
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if(viewModel.locationlat.isEmpty()){
            viewModel.locationlat=intent.getStringExtra("location_lat")?:""
        }
        if(viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }

        //开始监控weatherLiveData，当发生变化的时候就是获取到了信息
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if(weather!=null){
                showWeatherInfo(weather)//获取到信息之后就把信息展示到UI上
            }else{
                Toast.makeText(this,"无法成功获取天气信息",Toast.LENGTH_SHORT)
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationlat)//刷新天气的请求
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text=viewModel.placeName
        val realtime=weather.realtime
        val daily=weather.daily

        //填充now.xml中的数据
        val currentTempText="${realtime.temperature.toInt()}"
        currentTemp.text=currentTempText
        currentSky.text=getSky(realtime.skycon).info

        val currentPM25Text="空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text=currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //填充forecast.xml数据
        forecastLayout.removeAllViews()
        val days=daily.skycon.size
        for (i in 0 until days){//由于获取的是未来几天的数据，就用一个foreach循环来获取每一天
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]//获取那一天的天气
            val view= LayoutInflater.from(this).inflate(R.layout.forecast_item
                ,forecastLayout,false)
            val dateInfo=view.findViewById(R.id.dateInfo) as TextView
            val skyIcon=view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo=view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo=view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())//把日期格式化
            dateInfo.text=simpleDateFormat.format(skycon.date)
            val sky= getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min.toInt()}~${temperature.max.toInt()} 摄氏度"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }

        //填充life_index.xml中的数据
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc//只要当天的生活指数
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc
        weatherLayout.visibility= View.VISIBLE
    }
}