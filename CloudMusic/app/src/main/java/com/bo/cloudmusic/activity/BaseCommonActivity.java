package com.bo.cloudmusic.activity;

import android.content.Intent;

/**
 * 通用逻辑
 */
public class BaseCommonActivity extends BaseActivity{
    /**
     * 启动界⾯
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
    }
    /**
     * 启动界⾯并关闭当前界⾯
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
        finish();
    }
    /**
     * 获取界⾯
     */
    public BaseCommonActivity getMainActivity() {
        return this;
    }
}
