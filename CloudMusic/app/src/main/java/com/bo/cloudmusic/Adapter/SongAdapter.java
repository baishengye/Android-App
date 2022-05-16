package com.bo.cloudmusic.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * 歌单详情-歌曲适配器
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {


    /**
     * 索引
     */
    private int selectedIndex=-1;

    /**
     * 音乐监听接口
     */
    private SongListener songListener;

    /**
     * 设置音乐监听器
     * @return
     */
    public void setSongListener(SongListener songListener) {
        this.songListener=songListener;
    }

    /**
     * 构造方法
     * @param layoutResId
     */
    public SongAdapter(int layoutResId) {
        super(layoutResId, new ArrayList<>());
    }

    /**
     * 显示数据
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song data) {
        //显示位置(helper.getAdapterPosition()是从0开始的,但是rv中显示事情从1开始)
        helper.setText(R.id.tv_position, String.valueOf(helper.getAdapterPosition()));

        //显示标题
        helper.setText(R.id.tv_title,data.getTitle());

        //显示信息
        helper.setText(R.id.tv_info,data.getSinger().getNickname());

        //处理选中状态
        if(selectedIndex==helper.getAdapterPosition()){
            helper.setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            helper.setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.black));
        }

        //设置更多点击事件
        //可以像PlayListAdapter中那样的方法实现
        //这⾥就⽤普通⽅法实现
        View iv_more = helper.getView(R.id.ib_more);

        //设置点击监听
        iv_more.setOnClickListener(view->songListener.onMoreClick(data));
    }

    public void setSelectedIndex(int selectedIndex) {
        //先通知原来的位置
        selectIndex();

        //保存选中索引
        this.selectedIndex=selectedIndex;

        //刷新状态
        selectIndex();
    }

    private void selectIndex() {
        if (selectedIndex!=-1){
            notifyItemChanged(selectedIndex);
        }
    }

    /**
     * 监听器
     */
    public interface SongListener{
        /**
         * 音乐更多点击
         */
        void onMoreClick(Song data);
    }
}
