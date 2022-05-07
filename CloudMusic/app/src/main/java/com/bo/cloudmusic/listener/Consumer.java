package com.bo.cloudmusic.listener;

/**
 * 消费者接⼝
 *
 * 名字可以随便取
 * 只是Java中是这样命名的
 * 但他的类只有在API为24才能使⽤
 * 所以我们就⾃定义⼀个接⼝
 * @param <T>
 */
public interface Consumer<T> {
    /**
     * ⽅法名也可随便定义
     * @param t
     */
    void accept(T t);
}
