package com.bo.cloudmusic.utils;

import com.bo.cloudmusic.domain.event.PlayListChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 发布订阅工具类
 */
public class EventBusUtil {

    /**
     * 发送音乐列表改变通知
     * @param event
     */
    public static void post(PlayListChangedEvent event) {
        EventBus.getDefault().post(event);
    }
}
