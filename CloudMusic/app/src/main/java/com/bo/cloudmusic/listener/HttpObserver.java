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

public class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";

    /**
     * 请求成功
     */
    public void onSucceeded(T data) {
    }

    /**
     * 请求错误
     *
     * @param t
     * @param e
     * @return 如果返回true就表示子类要处理错误
     * 如果返回false就表示父类自动要处理错误
     */
    public boolean onFailed(T t, Throwable e) {
        return false;
    }

    /**
     * onNext中也需要判断是否出错,比如200错误会回调到onNext中
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);

        //判断是否请求正常
        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //有状态码
            //表示请求出错了
            handlerRequest(t, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        handlerRequest(null, e);
    }

    /**
     * 判断请求是否成功
     * 有状态码表示请求失败
     * 该方法和HttpUtil的HandleRequest有重复会重构
     *
     * @param t 请求成功的时候就是自定义的Response,比如BaseResponse
     *          请求失败的时候(比如500错误，就是Retrofit中的Response)
     * @return
     */
    private boolean isSucceeded(T t) {
        if (t instanceof BaseResponse) {
            //判断具体的业务请求
            BaseResponse baseResponse = (BaseResponse) t;
            //没有状态码表示请求成功
            //这是服务端的规定
            return baseResponse.getStatus() == null;
        }
        return false;
    }

    /**
     * 处理请求错误
     *
     * @param data  请求返回的对象
     * @param error 错误信息
     */
    private void handlerRequest(T data, Throwable error) {
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
