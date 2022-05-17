package com.bo.cloudmusic.domain.event;

import com.bo.cloudmusic.domain.Song;

/**
 * 收藏歌曲到歌单按钮
 */
public class CollectSongClickEvent {
    /**
     * 音乐
     */
    private Song song;

    public CollectSongClickEvent(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
