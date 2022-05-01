package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.utils.Constant;

/**
 * 歌单对象
 */
public class Sheet extends BaseMultiItemEntity{
    /**
     * 歌单标题
     */
    private String title;

    /**
     * 歌单封面
     */
    private String banner;

    @Override
    public int getItemType() {
        return Constant.TYPE_SHEET;
    }

    /**
     * 控制item占用几列空间
     * @return
     */
    @Override
    public int getSpanSize() {
        return 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
