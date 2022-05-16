package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bo.cloudmusic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * 迷你控制器 播放列表
 * <p>
 * 由于BottomSheetDialogFragment在现在这个项⽬中
 * 使⽤的⽐较少
 * 所以不⽤像BaseFragment那样封装
 * 如果使⽤的⽐较多
 * 可以进⼀步封装
 */
public class PlayListDialogFragment extends BottomSheetDialogFragment {

    /**
     * 创建布局，并且返回
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_play_list,null,false);//null标识没有父布局
    }

    /**
     * 布局创建完了
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 静态构造方法
     * @return
     */
    public static PlayListDialogFragment newInstance() {

        Bundle args = new Bundle();

        PlayListDialogFragment fragment = new PlayListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 显示对话框
     * @param fragmentManager
     */
    public static void show(FragmentManager fragmentManager) {
        //创建对话框
        PlayListDialogFragment fragment = newInstance();

        //显示
        //TAG只是⽤Fragment
        fragment.show(fragmentManager,"song_play_list_dialog");
    }
}
