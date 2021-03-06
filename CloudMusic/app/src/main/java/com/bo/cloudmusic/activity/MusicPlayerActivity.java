package com.bo.cloudmusic.activity;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bo.cloudmusic.Adapter.MusicPlayAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.event.OnStopRecordEvent;
import com.bo.cloudmusic.domain.event.PlayListChangedEvent;
import com.bo.cloudmusic.fragment.PlayListDialogFragment;
import com.bo.cloudmusic.listener.MusicPlayerListener;
import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.service.MusicPlayerService;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.domain.event.OnPlayEvent;
import com.bo.cloudmusic.domain.event.OnStartRecordEvent;
import com.bo.cloudmusic.utils.ResourceUtil;
import com.bo.cloudmusic.utils.SwitchDrawableUtil;
import com.bo.cloudmusic.utils.TimeUtil;
import com.bo.cloudmusic.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicPlayerActivity extends BaseTitleActivity implements MusicPlayerListener, SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener, ValueAnimator.AnimatorUpdateListener {


    private static final String TAG = "MusicPlayerActivity";
    /**
     * 列表管理器
     */
    private ListManager listManager;

    /**
     * 音乐播放管理器
     */
    private MusicPlayerManager musicPlayerManager;

    /**
     * 背景
     */
    @BindView(R.id.iv_background)
    ImageView iv_background;

    /**
     * 歌词容器
     */
    @BindView(R.id.rl_lyric)
    View rl_lyric;

    /**
     * 歌词列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 歌词拖拽容器
     */
    @BindView(R.id.ll_lyric_drag)
    View ll_lyric_drag;

    /**
     * 当前歌词时间控件
     */
    @BindView(R.id.tv_lyric_time)
    TextView tv_lyric_time;

    /**
     * 黑胶唱片容器
     */
    @BindView(R.id.cl_record)
    View cl_record;

    /**
     * 黑胶唱片列表控件
     */
    @BindView(R.id.vp)
    ViewPager vp;

    /**
     * 黑胶唱片指针
     */
    @BindView(R.id.iv_record_thumb)
    ImageView iv_record_thumb;

    /**
     * 下载按钮
     */
    @BindView(R.id.ib_download)
    ImageButton ib_download;

    /**
     * 开始位置
     */
    @BindView(R.id.tv_start)
    TextView tv_start;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;

    /**
     * 结束位置
     */
    @BindView(R.id.tv_end)
    TextView tv_end;

    /**
     * 循环模式按钮
     */
    @BindView(R.id.ib_loop_model)
    ImageButton ib_loop_model;

    /**
     * 播放按钮
     */
    @BindView(R.id.ib_play)
    ImageButton ib_play;

    /**
     * 黑胶唱片适配器
     */
    private MusicPlayAdapter recordAdapter;

    /**
     * 黑胶唱片指针从停止到播放的转动动画
     */
    private ObjectAnimator toPlayThumbAnimator;

    /**
     * 黑胶唱片指针从播放到停止的转动动画
     */
    private ValueAnimator toPauseThumbAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //显示初始化数据
        showInitData();

        //显示音乐时长
        showDuration();

        //显示播放进度
        showProgress();

        //显示播放状态
        showMusicPlayStatus();

        //显示循环模式
        showLoopModel();

        //添加播放监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //注册发布订阅框架
        EventBus.getDefault().register(this);

        ////滚动到当前音乐位置
        scrollPosition();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //移除音乐播放监听
        musicPlayerManager.removeMusicPlayerListener(this);

        //注销订阅
        EventBus.getDefault().unregister(this);
    }

    /**
     * 播放列表改变时间
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayListChangedEvent(PlayListChangedEvent event){
        if(listManager.getDatum().size()==0){
            //没有音乐
            //关闭界面
            finish();
        }
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());
        //初始化播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

        //创建黑胶唱片列表适配器
        recordAdapter = new MusicPlayAdapter(getMainActivity(), getSupportFragmentManager());

        //设置到控件
        vp.setAdapter(recordAdapter);

        //设置数据recordAdapter
        recordAdapter.setDatum(listManager.getDatum());

        //创建

        //从暂停到播放状态动画
        //从-25到0
        //属性动画ObjectAnimator是ValueAnimator的子类
        toPlayThumbAnimator = ObjectAnimator.ofFloat(iv_record_thumb, "rotation", Constant.THUMB_ROTATION_PAUSEING, Constant.THUMB_ROTATION_PLAYING);

        //设置执行时间
        toPlayThumbAnimator.setDuration(Constant.THUMB_DURATION);

        //从播放到暂停状态动画
        //从0到-25
        //属性动画ObjectAnimator是ValueAnimator的子类
        toPauseThumbAnimator = ValueAnimator.ofFloat(Constant.THUMB_ROTATION_PLAYING,Constant.THUMB_ROTATION_PAUSEING);

        //设置执行时间
        toPauseThumbAnimator.setDuration(Constant.THUMB_DURATION);

        //设置更新监听器
        toPauseThumbAnimator.addUpdateListener(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListeners() {
        super.initListeners();

        //拖拽进度条监听
        sb_progress.setOnSeekBarChangeListener(this);

        //滚动黑胶唱片监听
        vp.addOnPageChangeListener(this);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //状态栏透明
        lightStatusBar();

        //缓存界面数量
        //todo 这个地方如果缓存界面数量设得小于歌曲总数，那么在viewPager切换界面的时候会发生内存泄露,目前还没有解决
        vp.setOffscreenPageLimit(1000);
    }

    /**
     * 记载菜单布局
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载布局文件
        getMenuInflater().inflate(R.menu.menu_music_player,menu);
        return true;
    }

    /**
     * 点击菜单回调
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_report:
                //举报
                break;
            case R.id.action_share:
                //分享
                ToastUtil.successShortToast("分享");
                break;
            case R.id.action_sort:
                //排序
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示初始化数据
     */
    private void showInitData(){
        //获取当前播放的音乐
        Song data = listManager.getData();

        //设置标题
        setTitle(data.getTitle());

        //设置子标题
        toolbar.setSubtitle(data.getSinger().getNickname());

        //显示背景
        //ImageUtil.show(getMainActivity(),iv_background,data.getBanner());
        //高斯模糊的背景
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).asDrawable();

        if(StringUtils.isBlank(data.getBanner())){
            //没有封面图
            //使用默认图片
            requestBuilder.load(R.drawable.default_album);
        }else{
            //使用真实图片
            requestBuilder.load(ResourceUtil.resourceUri(data.getBanner()));
        }

        //创建请求选项
        //传⼊了BlurTransformation
        //⽤来实现⾼斯模糊
        //radius:模糊半径；值越⼤越模糊
        //sampling:采样率；值越⼤越模糊
        RequestOptions options = bitmapTransform(new BlurTransformation(25, 3));

        //加载图片
        requestBuilder.apply(options)
                .into(new CustomTarget<Drawable>() {
                    /**
                     * 资源下载成功
                     * @param resource
                     * @param transition
                     */
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        //设置到背景图片上
                        iv_background.setImageDrawable(resource);

                        //创建切换动画工具类
                        SwitchDrawableUtil switchDrawableUtil = new SwitchDrawableUtil(iv_background.getDrawable(), resource);

                        //把新的Drawable设置到背景
                        iv_background.setImageDrawable(switchDrawableUtil.getDrawable());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 启动方法
     */
    public static void start(Activity activity){
        Intent intent=new Intent(activity,MusicPlayerActivity.class);
        activity.startActivity(intent);
    }


    /**
     * 循环模式按钮点击
     */
    @OnClick(R.id.ib_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");

        //更改模式
        listManager.changeLoopModel();

        //显示循环模式
        showLoopModel();
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        //获取当前循环模式
        int model = listManager.getLoopModel();

        switch (model) {
            case Constant.MODEL_LOOP_RANDOM:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_random);
                break;
            case Constant.MODEL_LOOP_LIST:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_list);
                break;
            case Constant.MODEL_LOOP_ONE:
                ib_loop_model.setImageResource(R.drawable.ic_music_repeat_one);
                break;
        }
    }

    /**
     * 下载按钮点击
     */
    @OnClick(R.id.ib_download)
    public void onDownloadClick() {

    }

    /**
     * 上一曲按钮点击
     */
    @OnClick(R.id.ib_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");

        listManager.play(listManager.previous());
    }

    /**
     * 播放按钮点击
     */
    @OnClick(R.id.ib_play)
    public void onPlayClick() {
        LogUtil.d(TAG, "onPlayClick");

        playOrPause();
    }

    /**
     * 播放或暂停
     */
    private void playOrPause() {
        if (musicPlayerManager.isPlaying()) {
            listManager.pause();
        } else {
            listManager.resume();
        }
    }

    /**
     * 下一曲按钮点击
     */
    @OnClick(R.id.ib_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

        listManager.play(listManager.next());
    }

    /**
     * 播放列表按钮点击
     */
    @OnClick(R.id.ib_list)
    public void onListClick() {
        LogUtil.d(TAG, "onListClick");


        PlayListDialogFragment.show(getSupportFragmentManager());
    }


    //音乐播放监听回调
    @Override
    public void onPaused(Song song) {
        showPlayStatus();

        //停止滚动
        stopRecordRotate();
    }

    @Override
    public void onPlaying(Song song) {
        showPlayStatus();

        //黑胶唱片开始滚动
        startRecordRotate();
    }

    /**
     * 显示音乐播放状态
     */
    private void showMusicPlayStatus() {
        if (musicPlayerManager.isPlaying()) {
            showPauseStatus();
        } else {
            showPlayStatus();
        }
    }

    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        ib_play.setImageResource(R.drawable.ic_music_pause);
    }

    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        ib_play.setImageResource(R.drawable.ic_music_play);
    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {

        //显示初始化标题
        showInitData();

        //显示时长
        showDuration();

        //选中当前音乐
        scrollPosition();
    }

    /**
     * 选中当前音乐
     */
    private void scrollPosition() {
        //选中当前播放的⾳乐
        //使⽤post⽅法是
        //将⽅法放到了消息循环
        //如果不这样做
        //在onCreate这样的⽅法中滚动⽆效
        //因为这时候列表的数据还没有显示完成
        //具体的这部分我们在《详解View》课程中讲解了
        vp.post(new Runnable() {
            @Override
            public void run() {
                int idx = listManager.getDatum().indexOf(listManager.getData());
                if(idx!=-1){
                    //滚动到该位置
                    vp.setCurrentItem(idx,false);
                }

                //判断是否需要转动黑胶唱片
                if(musicPlayerManager.isPlaying()){
                    startRecordRotate();
                }else{
                    stopRecordRotate();
                }
            }
        });
    }

    /**
     * 显示时长
     */
    private void showDuration() {
        long duration = listManager.getData().getDuration();

        tv_end.setText(TimeUtil.formatMinuteSecond((int) duration));

        sb_progress.setMax((int) duration);
    }

    @Override
    public void onProgress(Song data) {
        showProgress();
    }

    /**
     * 显示进度
     */
    private void showProgress() {
        long progress = listManager.getData().getProgress();

        tv_start.setText(TimeUtil.formatMinuteSecond((int) progress));

        sb_progress.setProgress((int) progress);
    }
    //end


    //start 进度条监听回调
    /**
     * 进度条改变
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            //跳转到这个位置
            listManager.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    //end 进度条监听回调

    //start page滚动回调
    /**
     * 滚动完成
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    /**
     * 滚动状态改变了
     * @param state 滚动状态
     * @see ViewPager#SCROLL_STATE_IDLE：空闲
     * @see ViewPager#SCROLL_STATE_DRAGGING：正在拖拽
     * @see ViewPager#SCROLL_STATE_SETTLING：滚动完成后
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtil.d(TAG,"onPageScrollStateChanged: "+state);

        if(state==SCROLL_STATE_DRAGGING){
            //如果正在拖拽
            stopRecordRotate();
        }else if (state==SCROLL_STATE_IDLE){
            //空闲状态

            //判断黑胶唱片位置对应的音乐是不是和现在播放的是同一首
            Song song = listManager.getDatum().get(vp.getCurrentItem());
            if(listManager.getData().getId().equals(song.getId())){
                //同一首
                //判断播放状态
                if(musicPlayerManager.isPlaying()){
                    //正在播放
                    startRecordRotate();
                }
            }else{
                //不一样
                //播放当前位置的音乐
                listManager.play(song);
            }
        }
    }

    /**
     * 黑胶唱片开始转动
     * 指针回到唱片，成为播放状态
     */
    private void startRecordRotate() {
        //获取当前播放的音乐
        Song data = listManager.getData();

        LogUtil.d(TAG,"startRecordRotate: "+data.getTitle());

        EventBus.getDefault().post(new OnStartRecordEvent(data));

        //旋转黑胶唱片指针
        startRecordThumbRotation();
    }


    /**
     * 黑胶唱片停止转动
     * 指针离开唱片，成为暂停状态
     */
    private void stopRecordRotate() {
        //获取当前播放的音乐
        Song data = listManager.getData();

        LogUtil.d(TAG,"stopRecordRotate: "+data.getTitle());

        EventBus.getDefault().post(new OnStopRecordEvent(data));

        //停止旋转黑胶唱片
        pauseRecordThumbRotation();
    }


    /**
     * 黑胶唱片指针默认状态(正在播放)
     */
    public void startRecordThumbRotation(){
        //开始播放由暂停到播放的动画
        toPlayThumbAnimator.start();
    }

    /**
     * 黑胶唱片指针暂停状态(正在暂停)
     */
    public void pauseRecordThumbRotation(){
        toPauseThumbAnimator.start();
    }

    /**
     * 播放前回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayEvent(OnPlayEvent event){
        //停止黑胶唱片滚动
        stopRecordRotate();
    }

    //end page滚动回调

    /**
     * 属性动画回调
     * @param animation
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        iv_record_thumb.setRotation((Float) animation.getAnimatedValue());
    }
}