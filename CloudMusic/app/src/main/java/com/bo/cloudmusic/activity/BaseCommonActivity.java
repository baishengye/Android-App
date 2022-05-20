package com.bo.cloudmusic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.bo.cloudmusic.fragment.BaseCommonFragment;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ORMUtil;
import com.bo.cloudmusic.utils.PreferencesUtil;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 通用逻辑
 */
public class BaseCommonActivity extends BaseActivity{

    /**
     * 偏好设置字段
     */
    protected PreferencesUtil sp;

    /**
     * 数据库操作工具类
     */
    protected ORMUtil orm;

    @Override
    protected void initViews() {
        super.initViews();

        if(isBindView()){
            bindView();
        }
    }

    /**
     * 绑定View
     */
    protected void bindView() {
        ButterKnife.bind(this);
    }


    /**
     * 是否绑定View
     */
    protected boolean isBindView() {
        return true;
    }


    @Override
    protected void initDatum() {
        super.initDatum();

        //出生化偏好设置
        sp = PreferencesUtil.getInstance(getMainActivity());

        //初始化Realm数据库工具类
        orm = ORMUtil.getInstance(getApplicationContext());
    }

    /**
     * 启动界⾯
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
    }

    /**
     * 加上用户id启动Activity
     */
    protected void startActivityExtraId(Class<?> clazz,String id) {
        Intent intent = new Intent(getMainActivity(), clazz);

        if(!TextUtils.isEmpty(id))
            intent.putExtra(Constant.ID,id);

        startActivity(intent);
    }

    /**
     * 启动界⾯并关闭当前界⾯
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getMainActivity(), clazz));
        finish();
    }
    /**
     * 获取当前界⾯
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

    /**
     * 状态栏文字显示白色，内容显示再状态栏下面
     */
    protected void lightStatusBar(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //获取Window
            Window window = getWindow();

            //设置状态栏背景颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);

            //去除半透明效果
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：让内容显示到状态栏
            //SYSTEM_UI_FLAG_LAYOUT_STABLE：状态栏⽂字显示⽩⾊
            //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：状态栏⽂字显示⿊⾊
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    /**
     * 获取用户数据
     * @return
     */
    @Nullable
    protected Serializable ExtraData() {
        return getIntent().getSerializableExtra(Constant.DATA);
    }


    /**
     *获取字符串
     *
     */
    protected String extraString(String key) {
        return getIntent().getStringExtra(key);
    }

}
