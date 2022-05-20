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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

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


    //以下只有播放后才会有值
    /**
     * 总进度
     * 单位毫秒
     */
    private long duration;

    /**
     * 播放进度
     */
    private long progress;

    /**
     * 是否在播放列表
     */
    private boolean playList;

    /**
     * 音乐来源
     */
    private int source;

    @Override
    public int getItemType() {
        return Constant.TYPE_SONG;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
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

    public boolean isPlayList() {
        return playList;
    }

    public void setPlayList(boolean playList) {
        this.playList = playList;
    }

    /**
     * 将Song转为SongLocal对象
     *
     * @return
     */
    public SongLocal toSongLocal() {
        //创建对象
        SongLocal songLocal = new SongLocal();

        //赋值
        songLocal.setId(getId());
        songLocal.setTitle(title);
        songLocal.setBanner(banner);
        songLocal.setUri(uri);

        //歌手
        songLocal.setSinger_id(singer.getId());
        songLocal.setSinger_nickname(singer.getNickname());
        songLocal.setSinger_avatar(singer.getAvatar());

        //是否在播放列表
        songLocal.setPlayList(playList);

        //来源
        songLocal.setSource(source);

        //音乐时长
        songLocal.setDuration(duration);

        //播放进度
        songLocal.setProgress(progress);

        //返回
        return songLocal;
    }

    /**
     * 是否是本地音乐
     *
     * @return
     */
    public boolean isLocal() {
        return source == SongLocal.SOURCE_LOCAL;
    }
}
