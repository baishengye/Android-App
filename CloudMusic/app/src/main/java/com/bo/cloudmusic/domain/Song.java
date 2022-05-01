package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.utils.Constant;

public class Song extends BaseMultiItemEntity{
    @Override
    public int getItemType() {
        return Constant.TYPE_SONG;
    }
}
