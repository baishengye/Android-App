package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.event.OnStartRecordEvent;
import com.bo.cloudmusic.domain.event.OnStopRecordEvent;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 音乐黑胶唱片界面
 */
public class MusicRecordFragment extends BaseCommonFragment{

    private static final String TAG = "MusicRecordFragment";
    /**
     * ⿊胶唱⽚容器
     */
    @BindView(R.id.cl_content)
    ConstraintLayout cl_content;
    /**
     * 歌曲封⾯
     */
    @BindView(R.id.iv_banner)
    CircleImageView iv_banner;

    /**
     * 当前界面播放的音乐
     */
    private Song data;

    /**
     * 定时器任务
     */
    private TimerTask timerTask;

    /**
     * 旋转角度
     */
    private float recordRotation;
    private static final float RoTATION_PER=0.2304F;
    private Timer timer;


    @Override
    public void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

        super.onDestroy();
    }
    /**
     * 初始化数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();

        if(!EventBus.getDefault().isRegistered(this)){
            //注册发布订阅
            EventBus.getDefault().register(this);
        }

        //获取传递的数据
        data = (Song) extraData();

        //显示封面
        ImageUtil.show(getMainActivity(),iv_banner, data.getBanner());
    }

    /**
     * ⿊胶唱⽚开始转动事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRecordEvent(OnStartRecordEvent event) {
            //由于Fragment放⼊ViewPager中
            //他的⽣命周期就改变了
            //所以不能通过onResume这样的⽅法判断
            //当前Fragment是否显示
            //所有这⾥解决⽅法是
            //通过事件传递当前⾳乐
            //如果当前⾳乐匹配
            //当前Fragment就是操作当前fragment
            //如果不是就忽略
        if(event.getData()== data){
            //如果黑胶唱片要转动的图片的歌曲是正在播放的音乐
            LogUtil.d(TAG,"onStartRecordEvent: "+data.getTitle());

            startRecordRotate();
        }

    }

    /**
     * 开始转动
     */
    private void startRecordRotate() {
        if(timerTask!=null){
            //定时器已经启动了
            return;
        }

        //创建一个定时器任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtil.d(TAG,"startRecordRotate: "+data.getTitle());

                //判断旋转角度
                if(recordRotation >=360){
                    //归0
                    recordRotation=0;
                }
                ////每次加旋转的偏移
                recordRotation += RoTATION_PER;

                cl_content.setRotation(recordRotation);
            }
        };

        //创建定时器
        timer = new Timer();

        //0:不延迟
        //16毫秒执行一次任务
        timer.schedule(timerTask,0,16);
    }

    /**
     * ⿊胶唱⽚停止转动事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRecordEvent(OnStopRecordEvent event) {
        //由于Fragment放⼊ViewPager中
        //他的⽣命周期就改变了
        //所以不能通过onResume这样的⽅法判断
        //当前Fragment是否显示
        //所有这⾥解决⽅法是
        //通过事件传递当前⾳乐
        //如果当前⾳乐匹配
        //当前Fragment就是操作当前fragment
        //如果不是就忽略
        if(event.getData()== data){
            //如果黑胶唱片要转动的图片的歌曲是正在播放的音乐
            LogUtil.d(TAG,"onStopRecordEvent: "+ data.getTitle());

            stopRecordRotate();
        }
    }

    /**
     * 停止转动
     */
    private void stopRecordRotate() {
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }

        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }




    /**
     * 返回要显示的控件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_music,container,false);
    }

    /**
     * 创建当前的Fragment
     * @param data
     * @return
     */
    public static MusicRecordFragment newInstance(Song data) {

        Bundle args = new Bundle();
        //传递数据
        args.putSerializable(Constant.DATA,data);

        MusicRecordFragment fragment = new MusicRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
