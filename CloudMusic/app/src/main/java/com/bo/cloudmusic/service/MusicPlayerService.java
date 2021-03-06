package com.bo.cloudmusic.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.bo.cloudmusic.manager.ListManager;
import com.bo.cloudmusic.manager.MusicPlayerManager;
import com.bo.cloudmusic.manager.impl.ListManagerImpl;
import com.bo.cloudmusic.manager.impl.MusicNotificationManager;
import com.bo.cloudmusic.manager.impl.MusicPlayerManagerImpl;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.NotificationUtil;
import com.bo.cloudmusic.utils.ServiceUtil;

/**
 * 音乐播放service
 */
public class MusicPlayerService extends Service {

    private static final String TAG = "MusicPlayerService";

    private MusicNotificationManager musicNotificationManager;

    /**
     * 构造函数
     */
    public MusicPlayerService() {
    }

    /**
     * 获取音乐播放管理器
     * @param context
     * @return
     */
    public static MusicPlayerManager getMusicPlayerManager(Context context){
        //尝试启动服务
        ServiceUtil.startService(context,MusicPlayerService.class);

        //返回一个音乐播放管理器
        return MusicPlayerManagerImpl.getInstance(context);
    }

    /**
     * 获取列表管理器
     */
    public static ListManager getListManager(Context context){
        context=context.getApplicationContext();

        //尝试启动服务
        ServiceUtil.startService(context,MusicPlayerService.class);

        //返回一个列表管理器
        return ListManagerImpl.getInstance(context);
    }

    /**
     * service创建了
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG,"onCreate");

        //初始化音乐通知管理器
        musicNotificationManager = MusicNotificationManager.getInstance(getApplicationContext());
    }

    /**
     * 销毁了service
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"onDestroy");

        //停止前台服务
        //移除之前的创建通知
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 启动service的时候调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG,"onStartCommand");

        ////因为这个API是8.0才有的
        //所以要这样判断版本
        //不然低版本会崩溃
        /*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //设置service为前台service，提高应用优先级
            Notification notification = NotificationUtil.getServiceForeground(getApplicationContext());

            //id==0的时候就不会显示这个通知
            startForeground(0,notification);
        }*/

        return super.onStartCommand(intent, flags, startId);
    }
}