package com.bo.cloudmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.domain.Song;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * 歌单详情-歌曲适配器
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

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
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {

    }
}
