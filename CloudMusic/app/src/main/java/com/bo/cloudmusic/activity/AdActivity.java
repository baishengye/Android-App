package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.bo.cloudmusic.MainActivity;
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
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * 创建倒计时
         */
        countDownTimer = new CountDownTimer(5000, 1000){

            /**
             * 每次时间变化的时候发生的事情
             */
            @Override
            public void onTick(long millisUntilFinished) {
                bt_skip_ad.setText(getString(R.string.count_second,millisUntilFinished/1000+1));
            }

            /**
             *
             */
            @Override
            public void onFinish() {
                next();
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        cancelCountDown();

        super.onDestroy();
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

    private void cancelCountDown() {
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
    }

    private void next() {
        startActivityAfterFinishThis(MainActivity.class);
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

        //取消倒计时并且进入主页面
        cancelCountDown();
        next();
    }
}