package com.bo.cloudmusic.listener;

import com.bo.cloudmusic.domain.Song;

/**
 * 播放器接口
 */
public interface MusicPlayerListener {
    /**
     * 已经暂停了
     */
    void onPaused(Song song);

    /**
     * 已经播放了
     */
    void onPlaying(Song song);
}
