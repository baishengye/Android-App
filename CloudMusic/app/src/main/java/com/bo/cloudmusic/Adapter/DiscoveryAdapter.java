package com.bo.cloudmusic.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.BaseMultiItemEntity;
import com.bo.cloudmusic.utils.Constant;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面的适配器
 */
public class DiscoveryAdapter extends BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder> {

    /**
     * 构造方法
     */
    public DiscoveryAdapter() {
        //第⼀次他要传⼊数据
        //⽽这时候我们还没有准备好数据
        //所以传递⼀个空列表
        super(new ArrayList<>());

        //添加多类型布局
        addItemType(Constant.TYPE_TITLE, R.layout.item_title);
        addItemType(Constant.TYPE_SHEET, R.layout.item_sheet);
        addItemType(Constant.TYPE_SONG, R.layout.item_song);
    }

    /**
     * 绑定数据⽅法
     *
     * 复⽤等步骤不⽤管
     * 框架内部⾃动处理
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseMultiItemEntity item) {

    }
}
