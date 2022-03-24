package com.bo.boweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bo.boweather.android.R
import com.bo.boweather.android.logic.model.Place
import com.bo.boweather.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val placeName: TextView =view.findViewById(R.id.placeName)
        val placeAddress:TextView=view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)

        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {//给place_item一个点击事件,当点击了地址就会跳转到天气界面
            val position=holder.adapterPosition
            val place=placeList[position]

            val intent= Intent(parent.context, WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            fragment.viewModel.savePlace(place)//保存地区
            fragment.startActivity(intent)
            fragment.activity?.finish()//如果跳转到天气界面的话就把当前的这个地区列表布局Activity退出栈，资源还没有释放
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place=placeList[position]
        holder.placeAddress.text=place.address
        holder.placeName.text=place.name
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}