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
     * 歌单封面
     */
    private String banner;

    /**
     * 歌单描述
     */
    private String description;

    /**
     * 歌单点击数
     */
    private String clicks_count;

    /**
     * 歌单收藏数
     */
    private String collections_count;

    /**
     * 歌单评论数
     */
    private String comment_count;

    /**
     * 音乐数量
     */
    private int songs_count;

    /**
     * 歌单创建者
     */
    private User user;

    /**
     * 歌单中的歌曲
     */
    private List<Song> songs;

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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(String clicks_count) {
        this.clicks_count = clicks_count;
    }

    public String getCollections_count() {
        return collections_count;
    }

    public void setCollections_count(String collections_count) {
        this.collections_count = collections_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public int getSongs_count() {
        return songs_count;
    }

    public void setSongs_count(int songs_count) {
        this.songs_count = songs_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
