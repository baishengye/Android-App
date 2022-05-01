package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bo.cloudmusic.R;

import butterknife.BindView;


/**
 * 首页-我的界面
 */
public class DiscoveryFragment extends BaseCommonFragment {

    //列表控件用的布局管理器
    private GridLayoutManager layoutManager;

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void initViews() {
        super.initViews();

        //⾼度固定
        //可以提交性能
        //但由于这⾥是项⽬课程
        //所以这⾥不讲解
        //会在《详解RecyclerView》课程中讲解
        //http://www.ixuea.com/courses/8
        rv.setHasFixedSize(true);

        //设置显示3列,用布局管理器设置列表布局能装几行
        layoutManager = new GridLayoutManager(getMainActivity(), 3);

        //把列表布局设置到列表控件中
        rv.setLayoutManager(layoutManager);
    }

    /**
     * 构造方法
     * 固定写法
     * @return
     */
    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回布局文件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery,container,false);
    }
}