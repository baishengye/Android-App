package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告界面
 */
public class AdActivity extends BaseCommonActivity {

    /**
     *
     */
    private static final String TAG = "AdActivity";

    @BindView(R.id.bt_skip_ad)
    Button bt_skip_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initViews() {
        super.initViews();

        //全屏
        fullScreen();
    }

    /**
     * 广告按钮点击了
     */
    @OnClick(R.id.bt_ad)
    public void onAdClick(){
        LogUtil.d(TAG,"onAdClick");
    }

    /**
     * 跳过广告按钮点击了
     */
    @OnClick(R.id.bt_skip_ad)
    public void onSkipAdClick(){
        LogUtil.d(TAG,"onSkipAdClick");
    }
}