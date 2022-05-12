package com.bo.cloudmusic.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SimplePlayerAdapter extends BaseItemDraggableAdapter<Song, BaseViewHolder> {

    /**
     * 选中索引
     */
    private int selectedIndex=-1;

    /**
     * 构造方法
     * @param layoutResId
     */
    public SimplePlayerAdapter(int layoutResId) {
        super(layoutResId, new ArrayList<>());
    }

    /*public SimplePlayerAdapter(int layoutResId) {
        super(layoutResId,new ArrayList<>());
    }*/

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        //获取到文本控件
        TextView tv_title = helper.getView(android.R.id.text1);

        //显示标题
        helper.setText(android.R.id.text1,item.getTitle());

        //处理选中状态
        if(selectedIndex==helper.getAdapterPosition()){
            //选中行
            tv_title.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            //未选中(第三方框架中的API)
            helper.setTextColor(android.R.id.text1,mContext.getResources().getColor(R.color.black));
        }
    }

    /**
     * 选中⾳乐
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {

        //先刷新上一行数据
        selectIndex();

        //点击选中了index这个行
        this.selectedIndex=selectedIndex;

        //先刷新当前数据
        selectIndex();
    }

    private void selectIndex() {
        if (selectedIndex!=-1){
            notifyItemChanged(selectedIndex);
        }
    }
}
