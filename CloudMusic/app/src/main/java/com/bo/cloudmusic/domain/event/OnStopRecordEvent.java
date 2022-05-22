package com.bo.cloudmusic.domain.event;

import com.bo.cloudmusic.domain.Song;

/**
 * 黑胶唱片停止播放事件
 */
public class OnStopRecordEvent {
    private Song data;

    public OnStopRecordEvent(Song data) {
        this.data=data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}
