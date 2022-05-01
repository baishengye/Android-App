package com.bo.cloudmusic.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.BaseMultiItemEntity;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Title;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
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
        switch (helper.getItemViewType()){
            case Constant.TYPE_TITLE:
                //标题
                Title title=(Title) item;

                //设置标题
                helper.setText(R.id.tv_title,title.getTitle());
                break;
            case Constant.TYPE_SHEET:
                //歌单
                Sheet sheet=(Sheet) item;

                //显示图片
                ImageUtil.show((Activity)mContext,helper.getView(R.id.iv_banner),sheet.getBanner());

                //设置歌单标题
                helper.setText(R.id.tv_title,sheet.getTitle());

                //播放量
                helper.setText(R.id.tv_info,String.valueOf(sheet.getClicks_count()));
                break;
            case Constant.TYPE_SONG:
                //单曲
                break;
        }
    }
}
