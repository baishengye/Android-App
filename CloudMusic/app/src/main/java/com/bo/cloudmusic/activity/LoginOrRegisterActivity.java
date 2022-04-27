package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.AppContext;
import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Session;
import com.bo.cloudmusic.domain.User;
import com.bo.cloudmusic.domain.event.LoginSuccessEvent;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.HandlerUtil;
import com.bo.cloudmusic.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ReflectablePlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
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
                data.setQq_id(openId);//服务端可以通过这个openId来判断是否注册了

                //跳转到注册界面
                toRegister();

                //继续登录
                continueLogin();

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

    @OnClick(R.id.iv_weibo)
    public void onWeiboLoginClick(){
        //初始化平台
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);

        //设置false表示SSO授权方式
        platform.SSOSetting(false);

        //回调信息
        //在这里获取基本的授权的信息
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //登录成功
                /*PlatformDb db = platform.getDb();
                String nickname = db.getUserName();
                String avatar = db.getUserIcon();
                String openId = db.getUserId();

                LogUtil.d(TAG, "other login success:nickname:" + nickname + ",avatar:" + avatar + ",openId:" + openId + "," + HandlerUtil.isMainThread());*/

                data = new User();
                PlatformDb db = platform.getDb();
                String nickname = db.getUserName();
                String avatar = db.getUserIcon();
                String openId = db.getUserId();

                data.setNickname(nickname);
                data.setAvatar(avatar);
                data.setWeibo_id(openId);//服务端可以通过这个openId来判断是否注册了

                //跳转到注册界面
                //toRegister();

                //继续登录
                continueLogin();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        platform.showUser(null);
    }

    /**
     * 继续登录
     */
    private void continueLogin() {
        Api.getInstance().login(data)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    /**
                     *登录成功
                     */
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        LogUtil.d(TAG, "onLoginClick success:" + data.getData());

                        //把登录成功的事件通知到AppContext
                        AppContext.getInstance().login(sp, data.getData());

                        //关闭当前界面，并且启动主界面
                        startActivityAfterFinishThis(MainActivity.class);
                    }

                    /**
                     * 登录失败
                     */
                    @Override
                    public boolean onFailed(DetailResponse<Session> data, Throwable e) {
                        if(data!=null){
                            //向服务器请求成功了
                            //并且服务器返回了错误信息

                            //判断错误码
                            if(1010==data.getStatus()){
                                //用户未注册

                                //跳转到补充用户资料界面
                                toRegister();

                                //返回true表示开发者手动处理了错误
                                return true;
                            }
                        }

                        //其他得错误让父类处理
                        return super.onFailed(data,e);
                    }
                });
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