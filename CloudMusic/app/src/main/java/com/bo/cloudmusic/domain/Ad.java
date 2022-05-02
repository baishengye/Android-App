package com.bo.cloudmusic.domain;

/**
 * 广告模型
 */
public class Ad extends BaseModel{
    /**
     * 标题
     */
    private String title;
    /**
     * 图⽚
     */
    private String banner;

    /**
     * Uri
     */
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
