package com.bo.cloudmusic.listener;

import com.bo.cloudmusic.utils.LogUtil;

/**
 * ⽹络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";
    /**
     * 请求成功
     * @param data
     */
    public abstract void onSucceeded(T data) ;

    /**
     * 请求失败
     * @param t
     * @param e
     * @return
     */
    public boolean onFailed(T t, Throwable e) {
        return false;
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);
        //TODO 处理错误
        //请求正常
        onSucceeded(t);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());
        //TODO 处理错误
    }
}