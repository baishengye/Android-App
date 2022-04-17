package com.bo.cloudmusic.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.databinding.FragmentGuideBinding;
import com.bo.cloudmusic.utils.Constant;

/**
 * 引导界面的Fragment,使用androidx可以适配
 */
public class GuideFragment extends Fragment {
    private ImageView iv;
    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * 单例方法产生fragment
     */
    public static GuideFragment newInstance(int id) {
        //创建fragment
        GuideFragment fragment = new GuideFragment();
        //创建bundle
        Bundle args = new Bundle();
        //添加数据
        args.putInt(Constant.ID,id);
        //把bundle设置到fragment中
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 通过布局填充器将一个布局加载成view
     * 返回要显示的view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 通过布局填充器将一个布局加载成view
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    /**
     * view创建完毕了就执行的操作
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //找到控件
        iv=getView().findViewById(R.id.iv);
        
        //取出数据
        int id = getArguments().getInt(Constant.ID,-1);

        //判断数据格式
        if(id==-1){
            //如果图⽚不存在
            //就关闭当前界⾯
            //但在我们这⾥不会发⽣该情况
            getActivity().finish();
            return;
        }

        iv.setImageResource(id);
    }
}