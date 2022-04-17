package com.bo.cloudmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends FragmentPagerAdapter {
    /**
     * 列表数据源
     */
    protected List<Integer> datum=new ArrayList<>();

    public GuideAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public GuideAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * 获取有多少个page
     */
    @Override
    public int getCount() {
        return datum.size();
    }

    /**
     * 返回当前位置的page
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(getData(position));
    }

    /**
     * 获取当前位置所需资源的id
     */
    private int getData(int position) {
        return datum.get(position);
    }

    /**
     * 设置数据
     */
    public void setDatum(List<Integer> datum){
        if(datum!=null&&datum.size()>0){
            this.datum.clear();
            this.datum.addAll(datum);

            //通知数据修改了
            notifyDataSetChanged();
        }
    }
}
