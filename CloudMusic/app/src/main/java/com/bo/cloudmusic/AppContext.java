package com.bo.cloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.event.LoginSuccessEvent;
import com.bo.cloudmusic.utils.PreferencesUtil;
import com.bo.cloudmusic.utils.ToastUtil;
import com.facebook.stetho.Stetho;
import com.mob.MobSDK;

import org.greenrobot.eventbus.EventBus;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends Application {
    private static AppContext context;

    /**
     *创建App
     */
    @Override
    public void onCreate() {
        super.onCreate();

        context=this;

        //初始化第三方toasty工具类
        Toasty.Config.getInstance().apply();

        //初始化Toast工具类
        ToastUtil.init(getApplicationContext());

        //初始化stetho抓包工具
        //使用默认参数初始化
        Stetho.initializeWithDefaults(this);

        //初始化SharedSDK
        MobSDK.init(this);

        //为保证您的在集成MobSDK之后，能够满足工信部相关合规要求，
        // 您应确保在App安装后首次冷启动时，在用户阅读您的《隐私政策》并取得用户授权之后，
        // 调用提交隐私协议函数MobSDK.submitPolicyGrantResult提交隐私协议。
        // 反之，如果用户不同意《隐私政策》授权，则不能调用MobSDK.submitPolicyGrantResult提交隐私协议。
        MobSDK.submitPolicyGrantResult(true,null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //初始化MultiDex
        MultiDex.install(base);
    }

    //获取全局的上下文
    public static AppContext getInstance() {
        return context;
    }

    /**
     * 当用户登录过了
     * @param sp
     * @param data
     */
    public void login(PreferencesUtil sp, Session data){
        //保存登录后的Session
        sp.setSession(data.getSession());

        //保存用户id
        sp.setUserId(data.getUser());

        //初始化其他登录之后才需要初始化的内容
        onLogin();

        //发送登录成功通知
        //⽬的是⼀些界⾯需要接收该事件
        EventBus.getDefault().post(new LoginSuccessEvent());
    }

    private void onLogin() {
    }

}
