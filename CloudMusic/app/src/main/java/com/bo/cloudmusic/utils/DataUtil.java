package com.bo.cloudmusic.utils;

import com.bo.cloudmusic.domain.Song;

import java.util.List;

/**
 * 数据处理工具类
 */
public class DataUtil {

    /**
     * 改变数据库中歌曲的播放列表标识
     * @param songs
     * @param value
     */
    public static void changePlayListFlag(List<Song> songs, boolean value) {
        for (Song song:songs) {
            song.setPlayList(value);
        }
    }
}
