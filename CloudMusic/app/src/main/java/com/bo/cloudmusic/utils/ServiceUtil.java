package com.bo.cloudmusic.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.bo.cloudmusic.service.MusicPlayerService;

import java.util.List;

/**
 * 服务相关工具类
 */
public class ServiceUtil {
    public static void startService(Context context, Class<?> clazz) {
        if(!ServiceUtil.isServiceRunning(context,clazz)){
            //创建intent
            Intent intent = new Intent(context, clazz);

            //启动服务
            context.startService(intent);
        }
    }

    private static boolean isServiceRunning(Context context, Class<?> clazz) {
        //判断服务是否在运行

        //获取所有运行的服务
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获取在运行的服务
        List<ActivityManager.RunningServiceInfo> services=activityManager.getRunningServices(Integer.MAX_VALUE);

        if(services==null||services.size()==0){
            //没有在运行的服务
            return false;
        }

        //遍历所有服务，查看有没有我们需要的服务
        for (ActivityManager.RunningServiceInfo service:services) {
            if(service.service.getClassName().equals(clazz.getName())){
                //如果在运行的五福的类名和要找的服务类名一致就使在运行
                return true;
            }
        }

        return false;
    }
}
