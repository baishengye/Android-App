package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 所有Fragment通⽤⽗类
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 找控件
     */
    protected void initViews(){
    }

    /**
     * 设置数据
     */
    protected void initDatum(){
    }

    /**
     * 绑定监听器
     */
    protected void initListeners() {
    }

    /**
     * 返回要显示的View
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutView(inflater,container,savedInstanceState);
    }

    /**
     * 返回View
     */
    protected abstract View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * View创建完毕了
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initDatum();
        initListeners();
    }


    /**
     * 找控件
     */
    public final <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }
}

