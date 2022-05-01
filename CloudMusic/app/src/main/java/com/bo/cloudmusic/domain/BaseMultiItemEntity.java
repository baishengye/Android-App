package com.bo.cloudmusic.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public abstract class BaseMultiItemEntity extends BaseModel implements MultiItemEntity {

    /**
     * 占用多少列
     */
    public int getSpanSize(){
        return 3;
    }

    /**
     * 获取这个元素的类型
     * @return
     */
    @Override
    public int getItemType() {
        return 0;
    }
}
