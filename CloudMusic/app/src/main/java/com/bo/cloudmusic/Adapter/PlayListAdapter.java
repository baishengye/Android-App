package com.bo.cloudmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 迷你播放列表
 */
public class PlayListAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    public PlayListAdapter(int layoutResId) {
        super(layoutResId);
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
    }
}
