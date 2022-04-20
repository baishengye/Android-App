package com.bo.cloudmusic.domain.response;

/**
 * 详情⽹络请求解析类
 *
 * 继承BaseResponse
 * 定义了⼀个泛型T
 */
public class DetailResponse<T> extends BaseResponse {
    /**
     * 真实数据
     * 他的类型就是泛型
     */
    private T data;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
