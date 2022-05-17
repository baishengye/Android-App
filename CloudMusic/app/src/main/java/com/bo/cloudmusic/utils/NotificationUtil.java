package com.bo.cloudmusic.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;

/**
 * 通知相关的工具类
 */
public class NotificationUtil {

    private final static String TAG="NotificationUtil";

    private static final String IMPORTANCE_LOW_CHANNEL_ID = "IMPORTANCE_LOW_CHANNEL_ID";

    private static NotificationManager notificationManager;

    /**
     * 获取设置Service为前台的通知
     * @param context
     */
    public static Notification getServiceForeground(Context context) {
        //渠道是8.0中新增的
        //简单来说就是
        //为了给通知分组
        //例如：我们这个应⽤有聊天消息
        //还有其他⼴告推送消息
        //那么如果要把这个应⽤做好
        //这两种类型的通知就应该设置不同的渠道
        //⼀个是：聊天消息渠道；优先级为紧急
        //另⼀个是：推送⼴告通知；优先级为⾼
        //好处是⽤户可以屏蔽某部分通知
        //还能不错过重要的通知
        //当然这只是Google希望⼤家遵循这个规则
        //但往往实际情况下
        //也会把⼴告设置为紧急的
        //因为推送⼴告就是要让⽤户看到
        //创建渠道
        //可以多次创建
        //但Id⼀样只会创建⼀个

        getNotificationManager(context);

        createNotificationChannel();

        //创建一个通知
        Notification notification = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .build();


        return notification;
    }

    private static void createNotificationChannel() {
        //创建通知渠道
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){//android8.0以上才会用
            //渠道Id,渠道名称,重要级别
            //配置一个通知渠道
            NotificationChannel channel = new NotificationChannel(IMPORTANCE_LOW_CHANNEL_ID, "通知名称", NotificationManager.IMPORTANCE_LOW);

            //创建一个通知渠道
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static void getNotificationManager(Context context) {
        if(notificationManager==null){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示通知
     * @param id 通知id
     * @param notification 通知
     */
    public static void showNotification(int id,Notification notification) {
        notificationManager.notify(id,notification);
    }

    /***
     * 显示音乐通知
     */
    public static void showMusicNotification(Context context, Song song,boolean isPlaying) {

        //获取通知管理器
        getNotificationManager(context);

        //创建一个通知渠道
        createNotificationChannel();

        //创建RemoteView
        //显示自定义通知固定写法
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play);

        //创建大通知
        RemoteViews contentBigView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play_large);

        //构建一个通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomBigContentView(contentView)
                .setCustomBigContentView(contentBigView);

        //显示通知
        NotificationUtil.notify(context,Constant.NOTIFICATION_MUSIC_ID, builder.build());


    }

    /**
     * 显示通知
     * @param context
     * @param id
     * @param notification
     */
    private static void notify(Context context, int id, Notification notification) {

        //获取通知管理器
        getNotificationManager(context);

        //显示通知
        notificationManager.notify(id,notification);
    }
}
