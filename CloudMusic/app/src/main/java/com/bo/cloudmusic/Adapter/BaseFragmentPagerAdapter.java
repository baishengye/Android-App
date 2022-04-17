package com.bo.cloudmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bo.cloudmusic.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {
    /**
     * 上下文
     */
    protected final Context context;

    /**
     * 列表数据源
     */
    protected List<T> datum=new ArrayList<>();

    /**
     * 构造方法
     */
    public BaseFragmentPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context=context.getApplicationContext();
    }

    /**
     * 返回当前位置的page
     */
    @NonNull
    @Override
    public abstract Fragment getItem(int position);

    /**
     * 获取当前位置所需资源的id
     */
    protected T getData(int position) {
        return datum.get(position);
    }

    /**
     * 获取有多少个page
     */
    @Override
    public int getCount() {
        return datum.size();
    }

    /**
     * 设置数据
     */
    public void setDatum(List<T> datum){
        if(datum!=null&&datum.size()>0){
            this.datum.clear();
            this.datum.addAll(datum);

            //通知数据修改了
            notifyDataSetChanged();
        }
    }

    public void addDatum(List<T> datum){
        if(datum!=null&&datum.size()>0){
            this.datum.addAll(datum);

            //通知数据修改了
            notifyDataSetChanged();
        }
    }
}
