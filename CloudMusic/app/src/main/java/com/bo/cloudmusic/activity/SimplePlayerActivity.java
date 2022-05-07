package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 简单的播放器实现
 * 主要测试音乐播放相关逻辑
 * 因为黑胶唱片界面的逻辑比较复杂
 * 如果在和播放相关逻辑混一起，不好实现
 * 所有我们可以先使用一个简单的播放器
 * 从而把播放器相关逻辑实现完成
 * 然后在对接的黑胶唱片，就相对来说简单一点
 */
public class SimplePlayerActivity extends BaseTitleActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "SimplePlayerActivity";
    /**
     * 列表
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView tv_title;

    /**
     * 开始时间
     */
    @BindView(R.id.tv_start)
    TextView tv_start;

    /**
     * 拖拽进度控件
     */
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;

    /**
     * 结束时间
     */
    @BindView(R.id.tv_end)
    TextView tv_end;

    /**
     * 播放按钮
     */
    @BindView(R.id.bt_play)
    Button bt_play;

    /**
     * 循环模式
     */
    @BindView(R.id.bt_loop_model)
    Button bt_loop_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }


    @Override
    protected void initListeners() {
        super.initListeners();

        //拖拽进度条控件
        sb_progress.setOnSeekBarChangeListener(this);
    }

    /**
     * 启动界面
     * @param activity
     */
    public static void start(Activity activity) {
        Intent intent=new Intent(activity,SimplePlayerActivity.class);

        activity.startActivity(intent);
    }

    /**
     * 上一曲按钮
     */
    @OnClick(R.id.bt_previous)
    public void onPreviousClick(){
        LogUtil.d(TAG,"bt_previous");
    }

    /**
     * 播放按钮
     */
    @OnClick(R.id.bt_play)
    public void onPlayClick(){
        LogUtil.d(TAG,"onPlayClick");
    }

    /**
     * 下一曲按钮
     */
    @OnClick(R.id.bt_next)
    public void onNextClick(){
        LogUtil.d(TAG,"bt_next");
    }


    /**
     * 进度改变时
     * @param seekBar  进度条
     * @param progress  改变后的进度
     * @param fromUser  是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LogUtil.d(TAG,"onProgressChanged");
    }

    /**
     * 开始拖拽进度条
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG,"onStartTrackingTouch");
    }

    /**
     * 停止拖拽进度条
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG,"onStopTrackingTouch");
    }
}