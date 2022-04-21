package com.bo.cloudmusic.listener;

import android.text.TextUtils;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.response.BaseResponse;
import com.bo.cloudmusic.utils.HttpUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * ⽹络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";

    /**
     * 请求成功
     */
    public abstract void onSucceeded(T data);

    /**
     * 请求失败
     */
    public boolean onFailed(T t, Throwable e) {
        return false;
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);
        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //请求出错
            requestErrorHandle(t, null);
        }

    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        //处理错误
        requestErrorHandle(null, e);
    }

    /**
     * 判断网络请求是否成功
     */
    private boolean isSucceeded(T t) {
        if (t instanceof BaseResponse) {
            //判断具体的业务请求是否成功
            BaseResponse baseResponse = (BaseResponse) t;

            //没有状态码就表示成功
            return baseResponse.getStatus() == null;
        }
        return false;
    }

    /**
     * 处理网络请求错误
     */
    private void requestErrorHandle(T data, Throwable error) {
        if (onFailed(data, error)) {
            //回调了请求失败⽅法
            //并且该⽅法返回了true

            //返回true就表示外部⼿动处理错误
            //那我们框架内部就不⽤做任何事情了
        } else {
            HttpUtil.handleRequest(data,error);
        }
    }
}