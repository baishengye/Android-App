package com.bo.cloudmusic.utils;

import android.widget.Button;
import android.widget.TextView;

import com.bo.cloudmusic.manager.ListManager;

/**
 * 播放列表工具类
 */
public class PlayListUtil {
    /**
     * 显示循环模式
     * @param listManager
     * @param view
     */
    public static void showLoopModel(ListManager listManager, TextView view) {
        //获取到当前的循环模式
        int model = listManager.getLoopModel();

        //显示模式
        switch (model){
            case Constant.MODEL_LOOP_LIST:
                view.setText("列表循环");
                break;
            case Constant.MODEL_LOOP_ONE:
                view.setText("单曲循环");
                break;
            default:
                view.setText("随机循环");
                break;
        }
    }
}
