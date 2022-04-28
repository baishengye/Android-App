package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.Constant;

import butterknife.BindView;

/**
 * 通用WebView界面
 */
public class WebViewActivity extends BaseTitleActivity {
    /**
     * WebView控件
     */
    @BindView(R.id.wv)
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //获取webView设置
        WebSettings webSettings=wv.getSettings();

        //允许运行访问文件
        webSettings.setAllowFileAccess(true);

        //支持多窗口
        webSettings.setSupportMultipleWindows(true);

        //开启js支持
        webSettings.setJavaScriptEnabled(true);

        //是否禁止显示图片
        webSettings.setBlockNetworkImage(false);

        //运行显示手机中的网页
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //运行文件访问
        webSettings.setAllowFileAccessFromFileURLs(true);

        //运行dmo储存
        webSettings.setDomStorageEnabled(true);

        //判断版本
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //由于一个网页可能即用到http协议也用到https协议,而webView默认不加载这种，
            //所以在这里设置要加载
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);//允许
        }
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的数据
        String title = extraString(Constant.TITLE);
        //获取传递的数据
        String url = extraString(Constant.URL);

        //设置标题
        setTitle(title);

        //加载url
        wv.loadUrl(url);
    }

    public static void start(Activity activity, String title, String url){
        //创建Intent
        Intent intent = new Intent(activity, WebViewActivity.class);//从activity跳转到WebViewActivity

        //添加标题
        intent.putExtra(Constant.TITLE,title);

        //添加Url
        intent.putExtra(Constant.URL,url);

        activity.startActivity(intent);
    }


}