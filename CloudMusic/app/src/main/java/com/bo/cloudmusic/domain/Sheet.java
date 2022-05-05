package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.utils.Constant;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 歌单对象
 */
public class Sheet extends BaseMultiItemEntity{

    /**
     * 标题
     */
    private String title;
    /**
     * 封⾯
     */
    private String banner;
    /**
     * 描述
     */
    private String description;
    /**
     * 点击数
     */
    private Integer clicks_count = 0;
    /**
     * 收藏数
     */
    private Integer collections_count = 0;
    /**
     * 评论数
     */
    private Integer comments_count = 0;
    /**
     * ⾳乐数
     */
    private Integer songs_count = 0;
    /**
     * 歌单创建者
     */
    private User user;
    /**
     * 歌曲
     */
    private List<Song> songs;
    /**
     * 是否收藏
     * 有值就表示收藏了
     */
    private Integer collection_id;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(Integer clicks_count) {
        this.clicks_count = clicks_count;
    }

    public Integer getCollections_count() {
        return collections_count;
    }

    public void setCollections_count(Integer collections_count) {
        this.collections_count = collections_count;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Integer getSongs_count() {
        return songs_count;
    }

    public void setSongs_count(Integer songs_count) {
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

    public Integer getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Integer collection_id) {
        this.collection_id = collection_id;
    }

    /**
     * 是否收藏
     *
     * @return true:收藏；false:没有收藏
     */
    public boolean isCollection() {
        return collection_id != null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("banner", banner)
                .append("description", description)
                .append("clicks_count", clicks_count)
                .append("collections_count", collections_count)
                .append("comments_count", comments_count)
                .append("songs_count", songs_count)
                .append("user", user)
                .append("songs", songs)
                .append("collection_id", collection_id)
                .toString();
    }
}
