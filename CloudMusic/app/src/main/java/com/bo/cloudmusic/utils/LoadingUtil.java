package com.bo.cloudmusic.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import com.bo.cloudmusic.R;

public class LoadingUtil {
    private static ProgressDialog progressDialog;

    /**
     * 显示一个加载对话框,使用默认提示文字
     */
    public static void showLoading(Activity activity){
        showLoading(activity,activity.getString(R.string.loading));
    }

    public static void showLoading(Activity activity,String message){
        //判断这个activity是不是已经被销毁了，没被销毁才会显示对话框
        if(activity==null||activity.isFinishing()){
            return;
        }

        //判断是不是已经显示了对话框
        if(progressDialog!=null){
            //已经显示了就不显示
            return;
        }

        //创建一个进度对话框
        progressDialog = new ProgressDialog(activity);

        //提示标题
        progressDialog.setTitle("提示");

        //提示信息
        progressDialog.setMessage(message);

        //点击弹窗外不会自动隐藏
        progressDialog.setCancelable(false);

        //显示
        progressDialog.show();
    }

    /**
     * 隐藏加载对话框
     */
    public static void hideLoading(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog=null;
        }
    }
}
