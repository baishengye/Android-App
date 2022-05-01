package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.Adapter.DiscoveryAdapter;
import com.bo.cloudmusic.utils.Constant;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class Title extends BaseMultiItemEntity {

    public Title(String title) {
        this.title = title;
    }
    public Title() {
    }

    /**
     * 歌单标题
     */
    private String title;

    /**
     * 返回item 的类型
     * @return
     */
    @Override
    public int getItemType() {
        return Constant.TYPE_TITLE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
