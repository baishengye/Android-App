package com.bo.cloudmusic.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Song;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

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

        //先加载图⽚
        //因为我们的图⽚是在线的
        //⽽显示通知时没法直接显示⽹络图⽚
        //所以需要我们先把图⽚下载下来
        //创建请求选项
        RequestOptions options = ImageUtil.getCommonRequestOptions();

        //下载图片
        Glide.with(context)
                .asBitmap()
                .load(ResourceUtil.resourceUri(song.getBanner()))
                .apply(options)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //图片下载完成
                        showMusicNotification(context, song, isPlaying,resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        //清除下载
                    }
                });

    }

    private static void showMusicNotification(Context context, Song song, boolean isPlaying,Bitmap resource) {
        //获取通知管理器
        getNotificationManager(context);

        //创建一个通知渠道
        createNotificationChannel();

        //创建RemoteView
        //显示自定义通知固定写法
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play);

        setNotificationData(song, contentView, isPlaying,resource);

        //播放按钮点击事件
        //PendingIntent
        //可以理解为某个时间
        //就会触发的事件

        //FLAG_UPDATE_CURRENT
        //会替换掉原来的⼴播
        //更深⼊的原理在详解课程中设置
        onNotificationChildClick(context, contentView,Constant.ACTION_PLAY,R.id.iv_play);
        onNotificationChildClick(context, contentView,Constant.ACTION_NEXT,R.id.iv_next);
        onNotificationChildClick(context, contentView,Constant.ACTION_LYRIC,R.id.iv_lyric);

        //创建大通知
        RemoteViews contentBigView = new RemoteViews(context.getPackageName(), R.layout.notification_music_play_large);

        setNotificationData(song, contentBigView, isPlaying,resource);

        onNotificationChildClick(context, contentBigView,Constant.ACTION_PLAY,R.id.iv_play);
        onNotificationChildClick(context, contentBigView,Constant.ACTION_NEXT,R.id.iv_next);
        onNotificationChildClick(context, contentBigView,Constant.ACTION_LYRIC,R.id.iv_lyric);
        onNotificationChildClick(context, contentBigView,Constant.ACTION_LIKE,R.id.iv_like);
        onNotificationChildClick(context, contentBigView,Constant.ACTION_PREVIOUS,R.id.iv_previous);

        //设置通知点击后启动的界面
        Intent intent = new Intent(context, MainActivity.class);
        //传递一个动作
        intent.setAction(Constant.ACTION_MUSIC_PLAY_CLICK);
        //在activity以外打开一个activity需要添加一个flag，作为启动方式的标识
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //创建通知点击的广播意图
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,
                Constant.ACTION_MUSIC_PLAY_CLICK.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //构建一个通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(contentView)
                .setCustomBigContentView(contentBigView)
                .setContentIntent(contentPendingIntent);//点击通知栏发生的行为

        //显示通知
        NotificationUtil.notify(context,Constant.NOTIFICATION_MUSIC_ID, builder.build());
    }

    /**
     * 点击通知栏中的按钮时调用发送广播
     * @param context
     * @param contentView
     * @param actionKey
     * @param viewId
     */
    private static void onNotificationChildClick(Context context, RemoteViews contentView, String actionKey, int viewId) {
        //新建一个广播的意图
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                actionKey.hashCode(),
                new Intent(actionKey),
                PendingIntent.FLAG_UPDATE_CURRENT);

        //设置到播放按钮
        //点击按钮通知栏就发送一个广播
        contentView.setOnClickPendingIntent(viewId,pendingIntent);
    }

    /**
     * 设置通知里的数据
     * @param song
     * @param contentView
     */
    private static void setNotificationData(Song song, RemoteViews contentView ,boolean isPlaying,Bitmap resource) {
        //显示数据
        contentView.setImageViewBitmap(R.id.iv_banner,resource);

        //标题
        contentView.setTextViewText(R.id.tv_title, song.getTitle());

        //专辑信息
        contentView.setTextViewText(R.id.tv_info,String.format("%s - 专辑1", song.getSinger().getNickname()));

        //显示播放按钮
        int playButtonResourceId = isPlaying ? R.drawable.ic_music_notification_pause:R.drawable.ic_music_notification_play;

        contentView.setImageViewResource(R.id.iv_play,playButtonResourceId);
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
