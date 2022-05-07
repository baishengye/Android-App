package com.bo.cloudmusic.manager;

import com.bo.cloudmusic.domain.Song;

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
}
