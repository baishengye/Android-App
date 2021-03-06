package com.bo.cloudmusic.listener;

import android.media.MediaPlayer;

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

    /**
     * 播放器准备完毕了
     * @param mp
     * @param data
     */
    void onPrepared(MediaPlayer mp, Song data);

    /**
     * 播放进度回调
     * @param data
     */
    void onProgress(Song data);

    /**
     * 播放完毕回调
     */
    default void onCompletion(MediaPlayer mp){};


    /**
     * 歌词数据改变了
     * @param song
     */
    default void onLyricChanged(Song song){

    }
}
