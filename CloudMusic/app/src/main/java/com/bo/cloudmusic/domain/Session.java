package com.bo.cloudmusic.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 登录后返回的数据的模型
 */
public class Session extends BaseModel {//Serializable序列化接口
    /**
     * 用户id
     */
    private String user;

    /**
     * 登录后的session
     */
    private String session;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user", user)
                .append("session", session)
                .toString();
    }
}
