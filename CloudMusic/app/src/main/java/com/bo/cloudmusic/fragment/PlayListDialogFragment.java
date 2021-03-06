package com.bo.cloudmusic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bo.cloudmusic.Adapter.PlayListAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.event.PlayListChangedEvent;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.EventBusUtil;
import com.bo.cloudmusic.utils.PlayListUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 迷你控制器 播放列表
 * <p>
 * 由于BottomSheetDialogFragment在现在这个项⽬中
 * 使⽤的⽐较少
 * 所以不⽤像BaseFragment那样封装
 * 如果使⽤的⽐较多
 * 可以进⼀步封装
 */
public class PlayListDialogFragment extends BaseBottomSheetDialogFragment {

    /**
     * 循环模式
     */
    @BindView(R.id.tv_loop_model)
    TextView tv_loop_model;

    /**
     * 音乐数量
     */
    @BindView(R.id.tv_count)
    TextView tv_count;

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 适配器
     */
    private PlayListAdapter playListAdapter;

    /**
     * 布局管理器
     */
    private LinearLayoutManager linearLayoutManager;

    /**
     * 列表管理器
     */
    private ListManager listManager;

    @Override
    protected void initView() {
        super.initView();

        //固定尺寸
        rv.setHasFixedSize(true);

        //设置布局管理器
        linearLayoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(linearLayoutManager);

        //添加分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getMainActivity(), RecyclerView.VERTICAL);
        rv.addItemDecoration(decoration);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initDatum() {
        super.initDatum();

        //创建列表管理器
        listManager = MusicPlayerService.getListManager(getMainActivity());

        //创建适配器
        playListAdapter = new PlayListAdapter(R.layout.item_play_list,listManager);

        rv.setAdapter(playListAdapter);

        //设置数据
        playListAdapter.replaceData(listManager.getDatum());

        //显示循环模式
        PlayListUtil.showLoopModel(listManager,tv_loop_model);

        //显示⾳乐数据
        //真实项⽬中建议字符串都放到strings.xml⽂件中
        //因为这样更⽅便复⽤，汉化
        //这⾥只是给⼤家演示
        //也可以直接这样写
        tv_count.setText(String.format("(%d)", listManager.getDatum().size()));
    }

    @Override
    protected void initListener() {
        super.initListener();

        //点击控件播放
        playListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //关闭dialog
                dismiss();

                //播放点击的音乐
                listManager.play(listManager.getDatum().get(position));
            }
        });

        //点击子控件播放
        playListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //关闭弹窗
                //dismiss();

                //如果点击的删除按钮
                if(R.id.iv_remove==view.getId()){
                    //删除这个歌
                    listManager.delete(position);

                    //并且从adapter中删除数据，实现ui变化
                    playListAdapter.notifyItemRemoved(position);
                    playListAdapter.remove(position);
                    tv_count.setText(String.format("(%d)", listManager.getDatum().size()));
                }
            }
        });
    }

    /**
     * 创建布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_play_list,null,false);//null标识没有父布局
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

    /**
     * 删除列表中所有音乐
     */
    @OnClick(R.id.ib_delete_all)
    public void OnDeleteAllClick(){
        dismiss();
        listManager.deleteAll();

        //发送⾳乐列表改变通知
        EventBusUtil.post(new PlayListChangedEvent());
    }
}
