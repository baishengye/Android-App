package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.Constant;
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

        //创建意图
        /*为了使用户退出广告的时候直接退回到主页面所以可以先到MainActivity中，然后从MainActivity跳转到广告界面*/
        Intent intent = new Intent(getMainActivity(), MainActivity.class);

        //添加广告地址,正式项目中广告地址应该需要从服务端请求回来
        intent.putExtra(Constant.URL,"http://www.ixuea.com");

        //给intent加一个Action，便于在MainActivity判断需不需要跳转到广告
        intent.setAction(Constant.ACTION_AD);

        //启动界面
        startActivity(intent);

        //关闭当前界面
        finish();
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