package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.utils.Constant;

public class Song extends BaseMultiItemEntity{

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String banner;

    /**
     * Uri
     */
    private String uri;

    /**
     * 点击数
     */
    private String clicks_count;

    /**
     * 评论数
     */
    private String comments_count;

    /**
     * 歌词的风格
     */
    private String style;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 后台添加数据的人
     */
    private User user;

    /**
     * 歌手
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

    public String getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(String clicks_count) {
        this.clicks_count = clicks_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
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
