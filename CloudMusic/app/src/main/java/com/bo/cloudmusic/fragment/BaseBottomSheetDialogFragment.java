package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;

/**
 * 所有BottomSheetDialogFragment度的通用基类
 */
public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    /**
     * 找控件
     */
    protected void initView(){

    }

    /**
     * 找数据
     */
    protected void initDatum(){

    }

    /**
     * 找监听
     */
    protected void initListener(){

    }

    /**
     *创建view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutView(inflater, container, savedInstanceState);

        //初始化ButterKnife
        ButterKnife.bind(this,view);

        return view;
    }


    /**
     * view创建完了就进行其他操作
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initDatum();
        initListener();
    }

    /**
     * 外界实现创建view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
