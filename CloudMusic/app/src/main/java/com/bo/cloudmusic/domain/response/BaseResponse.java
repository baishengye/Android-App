package com.bo.cloudmusic.domain.response;

/**
 * 通⽤⽹络请求响应模型
 */
public class BaseResponse {
    /**
     * 状态码
     * 只有发⽣了错误才会有
     */
    private Integer status;

    /**
     * 出错的提示信息
     *
     * 发⽣了错误不⼀定有
     */
    private String message;

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
