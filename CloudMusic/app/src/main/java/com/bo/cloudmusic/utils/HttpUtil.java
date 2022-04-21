package com.bo.cloudmusic.utils;

import android.text.TextUtils;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.response.BaseResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 网络请求的相关方法
 */
public class HttpUtil {
    /**
     * 处理网络请求
     */
    public static void handleRequest(Object data,Throwable error) {
        if (error != null) {
            //先处理有异常的请求
            //判断错误类型
            if (error instanceof UnknownHostException) {
                ToastUtil.errorShortToast(R.string.error_network_unkown_host);
            } else if (error instanceof ConnectException) {
                ToastUtil.errorShortToast(R.string.error_network_connect);
            } else if (error instanceof SocketTimeoutException) {
                ToastUtil.errorShortToast(R.string.error_network_timeout);
            } else if (error instanceof HttpException) {
                HttpException exception = (HttpException) error;
                int code = exception.code();
                if (code == 401) {
                    ToastUtil.errorShortToast(R.string.error_network_not_auth);
                } else if (code == 403) {
                    ToastUtil.errorShortToast(R.string.error_network_not_permission);
                } else if (code == 404) {
                    ToastUtil.errorShortToast(R.string.error_network_not_found);
                } else if (code >= 500) {
                    ToastUtil.errorShortToast(R.string.error_network_server);
                } else {
                    ToastUtil.errorShortToast(R.string.error_network_unkown);
                }
            } else {
                ToastUtil.errorShortToast(R.string.error_network_unkown);
            }
        } else {
            if (data instanceof BaseResponse) {
                //判断具体的业务请求是否成功
                BaseResponse response = (BaseResponse) data;

                if (TextUtils.isEmpty(response.getMessage())) {
                    //如果没有错误提示信息
                    ToastUtil.errorShortToast(R.string.error_network_unkown);
                } else {
                    //有错误提示
                    ToastUtil.errorShortToast(response.getMessage());
                }
            }
        }
    }
}
