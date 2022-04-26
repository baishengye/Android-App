package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.event.LoginSuccessEvent;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.HandlerUtil;
import com.bo.cloudmusic.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LoginOrRegisterActivity extends BaseCommonActivity{

    private static final String TAG = "LoginOrRegisterActivity";

    /**
     * 第三方登录后的数据
     */
    private User data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//注册后结束运行的时候一定要注销
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //注册发布订阅框架
        //注册在哪个界面就会在哪个界面响应这个事件
        EventBus.getDefault().register(this);
    }

    /**
     * 点击登录按钮
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_login)
    public void onLoginClick(){
        startActivity(LoginActivity.class);
    }

    /**
     * QQ第三⽅登录
     */
    @OnClick(R.id.iv_qq)
    public void onQQLoginClick() {
        //初始化具体的平台
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        //设置false表示使⽤SSO授权⽅式
        platform.SSOSetting(false);

        //platform.authorize();
        //回调信息
        //可以在这⾥获取基本的授权返回的信息
        platform.setPlatformActionListener(new PlatformActionListener() {
            /**
             * 登录成功了
             * @param platform
             * @param i
             * @param hashMap
             */
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //登录成功了
                //就可以获取到昵称，头像，OpenId
                //该⽅法回调不是在主线程
                //从数据库获取信息
                //也可以通过user参数获取
                data = new User();
                PlatformDb db = platform.getDb();
                String nickname = db.getUserName();
                String avatar = db.getUserIcon();
                String openId = db.getUserId();

                data.setNickname(nickname);
                data.setAvatar(avatar);
                data.setQq_id(openId);

                //跳转到注册界面
                toRegister();

                //LogUtil.d(TAG, "other login success:nickname:" + nickname + ",avatar:" + avatar + ",openId:" + openId + "," + HandlerUtil.isMainThread());
            }
            /**
             * 登录失败了
             * @param platform
             * @param i
             * @param throwable
             */
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.d(TAG, "other login error:" + throwable.getLocalizedMessage() + "," + HandlerUtil.isMainThread());
            }
            /**
             * 取消登录了
             * @param platform
             * @param i
             */
            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.d(TAG, "other login cancel:" + i + "," + HandlerUtil.isMainThread());
            }
        });
        //authorize与showUser单独调⽤⼀个即可
        //授权并获取⽤户信息
        platform.showUser(null);
    }

    /**
     * 点击注册按钮
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        //把data重置成null
        data=null;

        toRegister();
    }

    private void toRegister() {
        Intent intent=new Intent(this,RegisterActivity.class);

        //传递用户数据
        intent.putExtra(Constant.DATA,data);

        startActivity(intent);
    }

    /**
     * 登录成功事件
     * 接受该事件的目的是关闭该界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//这个函数在哪种线程中响应(运行)
    public void onLoginSuccessEvent(LoginSuccessEvent loginSuccessEvent){
        finish();
    }
}