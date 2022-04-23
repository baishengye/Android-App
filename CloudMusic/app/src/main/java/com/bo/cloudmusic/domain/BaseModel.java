package com.bo.cloudmusic.domain;

import java.io.Serializable;

/**
 * 所有模型的父类
 */
public class BaseModel implements Serializable {
    /**
     * id
     */
    protected String id;

    /**
     * 创建时间
     */
    protected String created_at;

    /**
     * 更新时间
     */
    protected String update_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
