package com.bo.cloudmusic.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 保存歌曲信息
 * <p>
 * 为什么单独创建⼀个对象保存歌曲信息呢？
 * 第⼀个原因是：因为Realm框架
 * 从数据库中查询回来的对象
 * 如果更改字段的值
 * 他会⾃动同步到数据中
 * <p>
 * 真实项⽬中，最好还是使⽤第⼀种，
 * 因为使⽤第⼆种，中间多了⼀层转换
 * 理论上效率要低⼀点
 * 但⽤单独⼀个对象保存
 * 将逻辑分散到多个类
 * 降低了难度
 * <p>
 * 所以真实项⽬中还要做⼀个权衡
 */
public class SongLocal extends RealmObject {
    /**
     * 其他来源⾳乐
     * 包括在线的，下载的
     */
    public static final int SOURCE_OTHER = 0;

    /**
     * 本地⾳乐
     */
    public static final int SOURCE_LOCAL = 1;

    /**
     * 歌曲Id
     * 数据库主键
     */
    @PrimaryKey
    private String id;

    /**
     * 歌曲标题
     */
    private String title;
    /**
     * 歌曲封⾯
     */
    private String banner;
    /**
     * 歌曲路径
     */
    private String uri;

    /**
     * 歌⼿Id
     *
     * 在sqlite
     * 在sqlite，mysql这样的数据库中
     * 字段名建议⽤下划线
     * ⽽不是驼峰命名
     *
     * 但Realm中可以使⽤驼峰命名
     * 但我们还是使⽤下划线命名
     */
    private String singer_id;
    /**
     * 歌⼿名称
     */
    private String singer_nickname;
    /**
     * 歌⼿头像
     * 可选值
     */
    private String singer_avatar;
    /**
     * 是否在播放列表中
     * true：在
     */
    private boolean playList;
    /**
     * ⾳乐来源
     */
    private int source;

    //播放音乐后才有值
    /**
     * 总进度
     * 单位：毫秒
     */
    private long duration;

    /**
     * ⾳乐播放进度
     */
    private long progress;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getSinger_nickname() {
        return singer_nickname;
    }

    public void setSinger_nickname(String singer_nickname) {
        this.singer_nickname = singer_nickname;
    }

    public String getSinger_avatar() {
        return singer_avatar;
    }

    public void setSinger_avatar(String singer_avatar) {
        this.singer_avatar = singer_avatar;
    }

    public boolean isPlayList() {
        return playList;
    }

    public void setPlayList(boolean playList) {
        this.playList = playList;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
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

    /**
     * 将SongLocal转为Song对象
     *
     * @return
     */
    public Song toSong() {
        //创建一个对象
        Song song = new Song();

        //赋值
        song.setId(getId());
        song.setTitle(title);
        song.setBanner(banner);
        song.setUri(uri);

        //歌手
        User singer = new User();
        singer.setId(singer_id);
        singer.setNickname(singer_nickname);
        singer.setAvatar(singer_avatar);
        song.setSinger(singer);

        //播放列表标志
        song.setPlayList(playList);

        //来源
        song.setSource(source);

        //音乐长度
        song.setDuration(duration);

        //播放进度
        song.setProgress(progress);

        //返回数据
        return song;
    }
}
