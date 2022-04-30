package com.bo.cloudmusic.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bo.cloudmusic.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtil {
    /**
     * 显示头像
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void showAvatar(Activity activity, ImageView view, String uri) {
        if (TextUtils.isEmpty(uri)) {
            //没有头像
            //显示默认头像
            //iv_avatar.setImageResource(R.drawable.placeholder);
            show(activity, view, R.drawable.placeholder);
        } else {
            //有头像
            if (uri.startsWith("http")) {
                //绝对路径
                showFull(activity, view, uri);
            } else {
                //相对路径
                show(activity, view, uri);
            }
        }
    }

    /**
     * 显示绝对路径图⽚
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void showFull(Activity activity, ImageView view, String uri) {
        //获取功能配置
        RequestOptions options = getCommonRequestOptions();
        //显示图⽚
        Glide.with(activity)
                .load(uri)
                .load(uri)
                .apply(options)
                .into(view);
    }

    /**
     * 显示相对路径图⽚
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void show(Activity activity, ImageView view, String uri) {
        //将图⽚地址转为绝对路径
        uri = String.format(Constant.RESOURCE_ENDPOINT, uri);
        showFull(activity, view, uri);
    }

    /**
     * 显示资源⽬录图⽚
     *
     * @param activity
     * @param view
     * @param resourceId
     */
    public static void show(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId){
        //获取公共配置
        RequestOptions options = getCommonRequestOptions();
        Glide.with(activity)
                    .load(resourceId)
                    .apply(options)
                    .into(view);
    }

    /**
     * 获取公共配置
     *
     * @return
     */
    private static RequestOptions getCommonRequestOptions() {
        //创建配置选项
        RequestOptions options = new RequestOptions();
        //占位图
        options.placeholder(R.drawable.placeholder);
        //出错后显示的图⽚
        //包括：图⽚不存在等情况
        options.error(R.drawable.placeholder);
        //从中⼼裁剪
        options.centerCrop();
        return options;
    }
}