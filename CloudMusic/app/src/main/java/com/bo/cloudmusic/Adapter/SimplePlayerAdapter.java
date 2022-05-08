package com.bo.cloudmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.domain.Song;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class SimplePlayerAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    public SimplePlayerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        //显示标题
        helper.setText(android.R.id.text1,item.getTitle());
    }
}
