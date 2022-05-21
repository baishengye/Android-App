package com.bo.cloudmusic.activity;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.event.PlayListChangedEvent;
import com.bo.cloudmusic.fragment.PlayListDialogFragment;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通⽤⾳乐播放界⾯
 * 主要是显示迷你控制栏播放状态
 */
public class BaseMusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener {
    private static final String TAG = "BaseMusicPlayerActivity";
    /**
     * 迷你播放控制器 容器
     */
    @BindView(R.id.ll_play_small_control)
    LinearLayout ll_play_control_small;
    /**
     * 迷你播放控制器 封⾯
     */
    @BindView(R.id.iv_banner_small_control)
    ImageView iv_banner_small_control;
    /**
     * 迷你播放控制器 标题
     */
    @BindView(R.id.tv_title_small_control)
    TextView tv_title_small_control;
    /**
     * 迷你播放控制器 播放暂停按钮
     */
    @BindView(R.id.iv_play_small_control)
    ImageView iv_play_small_control;
    /**
     * 迷你播放控制器 进度条
     */
    @BindView(R.id.pb_progress_small_control)
    ProgressBar pb_progress_small_control;


    /**
     * 迷你播放控制器 容器点击
     */
    @OnClick(R.id.ll_play_small_control)
    public void onPlayControlSmallClick() {
        LogUtil.d(TAG, "onPlayControlSmallClick");

        //跳转到简单播放界面
        startMusicPlayerActivity();
    }
    /**
     * 迷你播放控制器 播放暂停按钮点击
     */
    @OnClick(R.id.iv_play_small_control)
    public void onPlaySmallClick() {
        LogUtil.d(TAG, "onPlaySmallClick");

        if(musicPlayerManager.isPlaying()){
            //是正在播放就暂停
            listManager.pause();
        }else{
            listManager.resume();
        }
    }
    /**
     * 迷你播放控制器 下⼀曲按钮点击
     */
    @OnClick(R.id.iv_next_small_control)
    public void onNextSmallClick() {
        LogUtil.d(TAG, "onNextSmallClick");

        //获取下一首
        Song data=listManager.next();

        //播放
        listManager.play(data);
    }
    /**
     * 迷你播放控制器 播放列表按钮点击
     */
    @OnClick(R.id.iv_list_small_control)
    public void onListSmallClick() {
        LogUtil.d(TAG, "onListSmallClick");

        showPlayListDialog();
    }

    /**
     * 列表管理器
     */
    protected ListManager listManager;
    /**
     * ⾳乐播放管理器
     */
    protected MusicPlayerManager musicPlayerManager;

    @Override
    protected void initDatum() {
        super.initDatum();
        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());
        //初始化⾳乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());
    }

    /**
     * 界⾯显示了
     */
    @Override
    protected void onResume() {
        super.onResume();
        //添加播放管理器监听器
        musicPlayerManager.addMusicPlayerListener(this);
        //显示迷你播放控制器数据
        showSmallPlayControlData();

        //注册播放列表改变了监听
        //之所以在这⾥注册是因为
        //只是当前显示的界⾯监听就⾏了
        //其他界⾯再次显示的时候会执⾏onResume⽅法
        EventBus.getDefault().register(this);
    }

    /**
     * 界⾯隐藏了
     */
    @Override
    protected void onPause() {
        super.onPause();
        //移除播放管理器监听器
        musicPlayerManager.removeMusicPlayerListener(this);

        //离开这个界面后要注销
        EventBus.getDefault().unregister(this);
    }

    /**
     * 事件:播放列表改变
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayListChangedEvent(PlayListChangedEvent event){
        //显示迷你播放控制器数据
        showSmallPlayControlData();
    }

    /**
     * 显示迷你播放控制器数据
     */
    private void showSmallPlayControlData() {
        if (listManager.getDatum() != null && listManager.getDatum().size() > 0) {
            //有音乐
            //显示迷你控制器
            ll_play_control_small.setVisibility(View.VISIBLE);
            //获取当前播放的⾳乐
            Song data = listManager.getData();
            if (data != null) {
                //显示初始化数据
                showInitData(data);
                //显示⾳乐时⻓
                showDuration(data);
                //显示播放进度
                showProgress(data);
                //显示播放状态
                showMusicPlayStatus();
            }
        } else {
            //隐藏迷你控制器
            ll_play_control_small.setVisibility(View.GONE);
        }
    }
    /**
     * 显示播放状态
     */
    private void showMusicPlayStatus() {
        if (musicPlayerManager.isPlaying()) {
            showPauseStatus();
        } else {
            showPlayStatus();
        }
    }
    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        //这种图⽚切换可以使⽤Selector来实现
        iv_play_small_control.setSelected(false);
    }
    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        iv_play_small_control.setSelected(true);
    }
    /**
     * 显示播放进度
     *
     * @param data
     */
    private void showProgress(Song data) {
        //设置到进度条
        pb_progress_small_control.setProgress((int) data.getProgress());
    }
    /**
     * 显示⾳乐时⻓
     *
     * @param data
     */
    private void showDuration(Song data) {
        //获取当前⾳乐时⻓
        int end = (int) data.getDuration();
        //设置到进度条
        pb_progress_small_control.setMax(end);
    }
    /**
     * 显示初始化数据
     *
     * @param data
     */
    private void showInitData(Song data) {
        //封⾯
        ImageUtil.show(getMainActivity(), iv_banner_small_control, data.getBanner());
        //标题
        tv_title_small_control.setText(data.getTitle());
    }


    /**
     * 显示对话框
     */
    private void showPlayListDialog() {
        PlayListDialogFragment.show(getSupportFragmentManager());//兼容
    }

    /**
     * 启动音乐播放界面
     */
    public void startMusicPlayerActivity() {
        //启动音乐播放界面
        SimplePlayerActivity.start(getMainActivity());
    }

    @Override
    public void onPaused(Song data) {
        showPlayStatus();
    }
    @Override
    public void onPlaying(Song data) {
        showPauseStatus();
    }
    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        showInitData(data);
    }
    @Override
    public void onProgress(Song data) {
        showProgress(data);
    }
}
