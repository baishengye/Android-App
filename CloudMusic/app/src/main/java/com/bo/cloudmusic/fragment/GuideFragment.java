package com.bo.cloudmusic.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bo.cloudmusic.R;

/**
 * 引导界面的Fragment,使用androidx可以适配
 */
public class GuideFragment extends Fragment {

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * 单例方法产生fragment
     */
    public static GuideFragment newInstance() {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

}