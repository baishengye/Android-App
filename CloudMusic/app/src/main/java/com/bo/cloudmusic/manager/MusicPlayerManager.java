package com.bo.cloudmusic.manager;

import com.bo.cloudmusic.activity.SimplePlayerActivity;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.MusicPlayerListener;

/**
 * 音乐播放器对外暴露的接口
 */
public interface MusicPlayerManager {

    /**
     * 播放
     * @param uri  音乐的绝对路径
     * @param data  音乐对象
     */
    void play(String uri, Song data);

    /**
     * 是否正在播放
     * @return
     */
    boolean isPlaying();

    /**
     * 停止播放
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 移除播放监听器
     */
    void removeMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 添加播放监听器
     */
    void addMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 获取当前播放的音乐
     */
    Song getData();

    /**
     * 从指定位置播放
     * @param progress  毫秒
     */
    void seekTo(int progress);
}
