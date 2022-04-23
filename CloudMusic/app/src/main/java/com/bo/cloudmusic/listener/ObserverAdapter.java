package com.bo.cloudmusic.listener;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 对Observer空实现，那么我们只需要继承ObserverAdapter,然后只需要重写自己想重写的了
 *
 * @param <T>
 */
public class ObserverAdapter<T> implements Observer<T> {

    /**
     * 开始订阅了执行
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 当前Observer成功执行完了
     */
    @Override
    public void onNext(T t) {

    }

    /**
     * 当前Observer执行失败了
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * onNext执行完了就会执行这个
     */
    @Override
    public void onComplete() {

    }
}
