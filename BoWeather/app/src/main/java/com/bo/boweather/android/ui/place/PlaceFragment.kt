package com.bo.boweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bo.boweather.android.MainActivity
import com.bo.boweather.android.R
import com.bo.boweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //如果是在MainActivity中并且有保存的地区数组才会打开默认的地区天气页面
        if(activity is MainActivity&&viewModel.isSavedPlace()){
            /*kotlin中API提供的 is 运算符类似于Java中的 instanceof 关键字的用法。
            is 运算符可以检查对象是否与特定的类型兼容(兼容：此对象是该类型，或者派生类)，
            同时也用来检查对象（变量）是否属于某数据类型（如Int、String、Boolean等）。
            !is运算符是它的否定形式。*/

            val place=viewModel.getSavedPlace()
            val intent= Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if(content.isNotEmpty()) {//当搜索框中的内容发生变化的时候就获取新的内容
                viewModel.searchPlaces(content)
            }else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer{ result ->
            //监控ViewData中的数据变化,当数据有变化就回调到observe中
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)//把地址添加到placeList中后适配器就会感应到，然后把数据添加到UI中
                adapter.notifyDataSetChanged()//刷新UI
            }else{
                Toast.makeText(activity,"未能查询到任何地点", Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()//数据为空就toast一个提示
            }
        })
    }

}