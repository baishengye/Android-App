package com.bo.cloudmusic.domain;

import com.bo.cloudmusic.Adapter.DiscoveryAdapter;
import com.bo.cloudmusic.utils.Constant;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Title extends BaseMultiItemEntity {

    /**
     * 标题
     */
    private String title;

    public Title(String title) {
        this.title = title;
    }
    public Title() {
    }


    /**
     * 返回item 的类型
     * @return
     */
    @Override
    public int getItemType() {
        return Constant.TYPE_TITLE;
    }

    /**
     * 占用多少列
     */
    public int getSpanSize(){
        return 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
