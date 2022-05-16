package com.bo.cloudmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.manager.ListManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 迷你播放列表
 */
public class PlayListAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    private ListManager listManager;

    public PlayListAdapter(int layoutResId,ListManager listManager) {
        super(layoutResId);
        this.listManager=listManager;
    }


    /**
     * 显示数据
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        String title = String.format("%s - %s", item.getTitle(), item.getSinger().getNickname());
        helper.setText(R.id.tv_title, title);

        //处理选中状态
        if (item.getId().equals(listManager.getData().getId())) {
            //选中
            //颜⾊设置为主⾊调
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            //未选中
            //颜⾊设置为⿊⾊
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text));
        }

        //删除点击事件
        helper.addOnClickListener(R.id.iv_remove);
    }
}
