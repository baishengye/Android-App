package com.bo.cloudmusic.domain.event;

import com.bo.cloudmusic.domain.Song;

/**
 * 黑胶唱片开始滚动事件
 */
public class OnStartRecordEvent {
    private Song data;

    public OnStartRecordEvent(Song data) {
        this.data=data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}
