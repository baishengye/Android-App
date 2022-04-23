package com.bo.cloudmusic.domain;

/**
 * 歌单对象
 */
public class Sheet extends BaseModel{
    /**
     * 歌单标题
     */
    private String title;

    /**
     * 歌单封面
     */
    private String banner;

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
