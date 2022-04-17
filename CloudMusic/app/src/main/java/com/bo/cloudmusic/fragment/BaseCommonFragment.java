package com.bo.cloudmusic.fragment;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.bo.cloudmusic.activity.BaseCommonActivity;
import com.bo.cloudmusic.utils.PreferencesUtil;

/**
 * 通用公共Fragment
 */
public abstract class BaseCommonFragment extends BaseFragment {
    protected PreferencesUtil sp;
    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化偏好设置工具类
        sp = PreferencesUtil.getInstance(getMainActivity());
    }

    /**
     * 启动界⾯
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 启动界⾯并关闭当前界⾯
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
        getActivity().finish();
    }

    /**
     * 获取当前Fragment所在的Activity
     * @return
     */
    protected BaseCommonActivity getMainActivity() {
        return (BaseCommonActivity) getActivity();
    }
}
