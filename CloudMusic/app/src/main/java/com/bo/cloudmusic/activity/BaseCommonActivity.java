package com.bo.cloudmusic.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.bo.cloudmusic.utils.PreferencesUtil;

/**
 * 通用逻辑
 */
public class BaseCommonActivity extends BaseActivity{

    /**
     * 偏好设置字段
     */
    protected PreferencesUtil sp;

    @Override
    protected void initDatum() {
        super.initDatum();

        //出生化偏好设置
        sp = PreferencesUtil.getInstance(getMainActivity());
    }

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

    /**
     *将页面设置成全屏
     */
    protected void fullScreen(){
        //获取屏幕view
        View decorView = getWindow().getDecorView();
        //判断版本
        if(Build.VERSION.SDK_INT>11&&Build.VERSION.SDK_INT<19){
            //11~18版本
            decorView.setSystemUiVisibility(View.GONE);//gone是不显示
        }else if(Build.VERSION.SDK_INT>=19){
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |//隐藏导航栏
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |//从状态栏下拉半透明悬浮显示一会二状态栏和导航栏
                    View.SYSTEM_UI_FLAG_FULLSCREEN;//全屏

            decorView.setSystemUiVisibility(options);
        }
    }

    /**
     * 隐藏系统状态栏
     */
    protected void hideStatusBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
