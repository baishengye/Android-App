package com.bo.cloudmusic.manager;

import com.bo.cloudmusic.domain.Song;

import java.util.List;

/**
 * 列表管理器
 * 主要是封装列表相关的操作
 */
public interface ListManager {

    /**
     * 设置播放列表
     * @param datum
     */
    void setDatum(List<Song> datum);

    /**
     * 获取播放列表
     */
    List<Song> getDatum();

    /**
     * 播放
     */
    void play(Song data);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 获取上一个音乐
     */
    Song previous();

    /**
     * 获取下一个音乐
     */
    Song next();

    /**
     * 更改循环模式
     */
    int changeLoopModel();

    /**
     * 获取循环模式
     */
    int getLoopModel();

    /**
     * 获取正在播放的音乐
     * @return
     */
    Song getData();

    /**
     * 从列表中删除音乐
     * @param position
     */
    void delete(int position);

    /**
     * 删除所有音乐
     */
    void deleteAll();

    /**
     * 跳转到该位置播放
     * @param progress
     */
    void seekTo(int progress);
}
