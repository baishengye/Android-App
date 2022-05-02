package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.utils.Constant;

public class Song extends BaseMultiItemEntity{

    /**
     * 标题
     */
    private String title;
    /**
     * 封⾯图
     */
    private String banner;
    /**
     * ⾳乐地址
     */
    private String uri;
    /**
     * 点击数
     */
    private Integer clicks_count = 0;
    /**
     * 评论数
     */
    private Integer comments_count = 0;
    /**
     * 歌词类型
     */
    private Integer style;
    /**
     * 歌词内容
     */
    private String lyric;
    /**
     * 创建该⾳乐的⼈
     */
    private User user;
    /**
     * 歌⼿
     */
    private User singer;

    @Override
    public int getItemType() {
        return Constant.TYPE_SONG;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(Integer clicks_count) {
        this.clicks_count = clicks_count;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSinger() {
        return singer;
    }

    public void setSinger(User singer) {
        this.singer = singer;
    }
}
