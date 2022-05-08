package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.manager.impl.MusicPlayerManagerImpl;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ListUtil;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.NotificationUtil;
import com.bo.cloudmusic.utils.ServiceUtil;
import com.bo.cloudmusic.utils.TimeUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 简单的播放器实现
 * 主要测试音乐播放相关逻辑
 * 因为黑胶唱片界面的逻辑比较复杂
 * 如果在和播放相关逻辑混一起，不好实现
 * 所有我们可以先使用一个简单的播放器
 * 从而把播放器相关逻辑实现完成
 * 然后在对接的黑胶唱片，就相对来说简单一点
 */
public class SimplePlayerActivity extends BaseTitleActivity implements SeekBar.OnSeekBarChangeListener,MusicPlayerListener {

    private static final String TAG = "SimplePlayerActivity";

    /**
     * MusicPlayerManager的实例
     */
    private MusicPlayerManager musicPlayerManager;

    /**
     * 列表管理器的实例
     */
    private ListManager listManager;

    /**
     * 长在播放的音乐
     */
    private Song song;
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

    /**
     * 界面可见，准备和用户交互的时候
     */
    @Override
    protected void onResume() {
        super.onResume();

        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener((MusicPlayerListener) this);

        //显示音乐时长
        showDuration();

        //显示播放进度
        showProgress();

        //显示播放状态
        showMusicPlayStatus();
    }

    /**
     * 界面不再栈顶了(可见但是在对话框下面，或者不可见)
     */
    @Override
    protected void onPause() {
        super.onPause();

        //移除监听播放器
        musicPlayerManager.removeMusicPlayerListener((MusicPlayerListener) this);
    }


    @Override
    protected void initDatum() {
        super.initDatum();

        //测试单例模式
        //可以发现他们两次的内存地址都是⼀样
        //说明单例模式⽣效了
        /*MusicPlayerManager o1= MusicPlayerManagerImpl.getInstance(getApplicationContext());
        MusicPlayerManager o2= MusicPlayerManagerImpl.getInstance(getApplicationContext());
        LogUtil.d(TAG,"initDatum test single:"+(o1==o2));*/

        //使用MusicPlayerService获取播放管理器
        /*MusicPlayerManager o1 = MusicPlayerService.getMusicPlayerManager(getMainActivity());
        MusicPlayerManager o2 = MusicPlayerService.getMusicPlayerManager(getMainActivity());
        LogUtil.d(TAG, "initDatum test single:" + (o1 == o2));*/

        //初始化列表管理器
        listManager=MusicPlayerService.getListManager(getApplicationContext());

        //获取音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

        /*//测试音乐播放
        String songUrl="http://dev-courses-misuc.ixuea.com/assets/s1.mp3";
        song=new Song();
        song.setUri(songUrl);

        //播放音乐
        musicPlayerManager.play(songUrl,song);*/
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

    //点击事件
    /**
     * 上一曲按钮
     */
    @OnClick(R.id.bt_previous)
    public void onPreviousClick(){
        LogUtil.d(TAG,"bt_previous");

        //获取下一曲
        Song data=listManager.previous();
        if(data==null){
            //如果列表中没有音乐就土司提醒用户
            //播放界面只有有数据的时候才会进入
            ToastUtil.errorShortToast(getString(R.string.not_play_music));
        }else{
            listManager.play(data);
        }
    }

    /**
     * 播放按钮
     */
    @OnClick(R.id.bt_play)
    public void onPlayClick(){
        LogUtil.d(TAG,"onPlayClick");

        //获取通知
       /* Notification notification = NotificationUtil.getServiceForeground(getApplicationContext());

        NotificationUtil.showNotification(100,notification);*/

        playOrPause();
    }


    /**
     * 下一曲按钮
     */
    @SuppressLint("CheckResult")
    @OnClick(R.id.bt_next)
    public void onNextClick(){
        LogUtil.d(TAG,"bt_next");

        //获取下一曲
        Song data=listManager.next();
        if(data==null){
            //如果列表中没有音乐就土司提醒用户
            //播放界面只有有数据的时候才会进入
            ToastUtil.errorShortToast(getString(R.string.not_play_music));
        }else{
            listManager.play(data);
        }
    }

    @OnClick(R.id.bt_loop_model)
    public void onLoopModelClick(){
        LogUtil.d(TAG,"onLoopModelClick");

        //改变循环模式
        listManager.changeLoopModel();

        //显示循环模式
        showLoopModel();
    }
    //end点击事件

    //接口方法
    /**
     * 进度改变时
     * @param seekBar  进度条
     * @param progress  改变后的进度
     * @param fromUser  是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //LogUtil.d(TAG,"onProgressChanged");

        if(fromUser){
            //跳转到该位置播放
            musicPlayerManager.seekTo(progress);
        }
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


    //播放管理监听器
    @Override
    public void onPaused(Song song) {
        LogUtil.d(TAG,"onPaused");
        showPlayStatus();
    }

    @Override
    public void onPlaying(Song song) {
        showPauseStatus();
        LogUtil.d(TAG,"onPlaying");
    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        //LogUtil.d(TAG,"onPrepared:" + data.getProgress()+" ,"+data.getDuration());

        //显示时长
        showDuration();
    }

    @Override
    public void onProgress(Song data) {
        //LogUtil.d(TAG,"onProgress:" + data.getProgress()+" ,"+data.getDuration());

        showProgress();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtil.d(TAG, "onCompletion");
    }
    //end接口方法

    /**
     * 播放或暂停
     */
    private void playOrPause() {
        if(musicPlayerManager.isPlaying()){
            listManager.pause();
        }else{
            listManager.resume();
        }
    }

    /**
     * 显示播放进度
     */
    private void showProgress() {
        //获取播放进度
        long progress = musicPlayerManager.getData().getProgress();

        //格式化进度
        tv_start.setText(TimeUtil.formatMinuteSecond((int)progress));

        //设置到进度条
        sb_progress.setProgress((int) progress);
    }

    /**
     * 显示时长
     */
    private void showDuration() {
        //获取正在播放音乐的时长
        long end = musicPlayerManager.getData().getDuration();

        //将毫秒格式化成分钟:秒
        tv_end.setText(TimeUtil.formatMinuteSecond((int)end));

        //设置到进度条
        sb_progress.setMax((int)end);
    }
    //播放管理监听器

    private void showPlayStatus() {
        showMusicPlayStatus();
    }

    private void showPauseStatus() {
        showMusicPlayStatus();
    }

    /**
     * 显示音乐播放状态
     */
    private void showMusicPlayStatus(){
        if(musicPlayerManager.isPlaying()){
            bt_play.setText("播放");
        }else{
            bt_play.setText("暂停");
        }
    }


    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        //获取到当前的循环模式
        int model = listManager.getLoopModel();

        //显示模式
        switch (model){
            case Constant.MODEL_LOOP_LIST:
                bt_loop_model.setText("列表循环");
                break;
            case Constant.MODEL_LOOP_ONE:
                bt_loop_model.setText("单曲循环");
                break;
            default:
                bt_loop_model.setText("随机循环");
                break;
        }
    }

}