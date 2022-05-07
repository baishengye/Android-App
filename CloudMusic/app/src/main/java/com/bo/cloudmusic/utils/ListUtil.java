package com.bo.cloudmusic.utils;

import com.bo.cloudmusic.listener.Consumer;

import java.util.List;

/**
 * 列表⼯具类
 */
public class ListUtil {
    /**
     * 变量每⼀个接⼝
     *
     * @param datum
     * @param action
     * @param <T>
     */
    public static <T> void eachListener(List<T> datum, Consumer<T> action) {
        for (T listener : datum) {
            //将列表中每⼀个对象传递给action
            action.accept(listener);
        }
    }
}
