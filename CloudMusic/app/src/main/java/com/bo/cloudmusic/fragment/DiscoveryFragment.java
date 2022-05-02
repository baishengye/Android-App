package com.bo.cloudmusic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bo.cloudmusic.Adapter.DiscoveryAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.BaseMultiItemEntity;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.Title;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;


/**
 * 首页-我的界面
 */
public class DiscoveryFragment extends BaseCommonFragment {

    //列表控件用的布局管理器
    private GridLayoutManager layoutManager;

    //列表的适配器
    private DiscoveryAdapter adapter;
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initDatum() {
        super.initDatum();

        //创建列表的适配器
        adapter = new DiscoveryAdapter();

        //设置列宽度
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {

            /**
             * 占用多少列
             * @param gridLayoutManager
             * @param position
             * @return
             */
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                //获取模型的宽度
                return adapter.getItem(position).getSpanSize();
            }
        });

        //添加头部(addHeaderView是第三方框架的方法)
        adapter.addHeaderView(createHeaderView());

        //把适配器设置到列表中
        rv.setAdapter(adapter);

        //请求数据
        fetchData();

        adapter.notifyDataSetChanged();
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


    /**
     * 请求数据方法
     */
    private void fetchData() {
        //因为现在还没有请求数据
        //所以添加⼀些测试数据
        //⽬的是让列表显示出来
        /*List<BaseMultiItemEntity> datas = new ArrayList<>();

        //添加标题
        datas.add(new Title("推荐歌单"));

        //添加歌单数据
        for (int i = 0; i < 9; i++) {
            datas.add(new Sheet());
        }

        //添加标题
        datas.add(new Title("推荐单曲"));

        //添加单曲数据
        for (int i = 0; i < 9; i++) {
            datas.add(new Song());
        }

        //把数据设置到适配器
        adapter.replaceData(datas);*/

        //创建列表
        List<BaseMultiItemEntity> datum = new ArrayList<>();

        //歌单Api
        Observable<ListResponse<Sheet>> sheets= Api.getInstance().sheets();
        //单曲Api
        Observable<ListResponse<Song>> songs = Api.getInstance().songs();

        //请求歌单数据
        sheets.subscribe(new HttpObserver<ListResponse<Sheet>>() {
            @Override
            public void onSucceeded(ListResponse<Sheet> data) {
                datum.add(new Title("推荐歌单"));

                //添加歌单数据
                datum.addAll(data.getData());

                songs.subscribe(new HttpObserver<ListResponse<Song>>() {
                            @Override
                            public void onSucceeded(ListResponse<Song> data) {
                                datum.add(new Title("推荐单曲"));

                                datum.addAll(data.getData());

                                adapter.replaceData(datum);
                            }
                        });
            }
        });
    }

    /**
     * 创建头部布局
     * @return
     */
    private View createHeaderView() {
        //从xml创建view
        View view = getLayoutInflater().inflate(R.layout.header_discovery, (ViewGroup) rv.getParent(), false);

        //找到⽇期⽂本控件
        TextView tv_day = view.findViewById(R.id.tv_day);

        //设置⽇期
        //由于项⽬中没有其他位置使⽤到
        //所以可以不⽤重构
        //获取⽇历对象
        Calendar calendar=Calendar.getInstance();

        //获取当月的天
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_day.setText(String.valueOf(day));

        return view;
    }
}