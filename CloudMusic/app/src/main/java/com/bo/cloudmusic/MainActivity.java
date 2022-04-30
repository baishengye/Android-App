package com.bo.cloudmusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bo.cloudmusic.activity.BaseCommonActivity;
import com.bo.cloudmusic.activity.BaseTitleActivity;
import com.bo.cloudmusic.activity.WebViewActivity;
import com.bo.cloudmusic.databinding.ActivityMainBinding;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;
import com.bo.cloudmusic.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseTitleActivity {

    private static final String TAG = "MainActivity";

    /*侧滑布局*/
    @BindView(R.id.dl)
    DrawerLayout dl;

    /*昵称*/
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    /*描述*/
    @BindView(R.id.tv_description)
    TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理动作
        processIntent(getIntent());
    }

    @Override
    protected void initViews() {
        super.initViews();

        //侧滑配置
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getMainActivity(),
                dl,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        //添加侧滑监听器
        dl.addDrawerListener(actionBarDrawerToggle);

        //同步状态
        actionBarDrawerToggle.syncState();
    }

    /**
     * 处理动作
     * @param intent
     */
    private void processIntent(Intent intent) {
        if(Constant.ACTION_AD.equals(intent.getAction())){
            //广告点击了

            //显示广告界面
            WebViewActivity.start(getMainActivity(),"活动标题",intent.getStringExtra(Constant.URL));
        }
    }

    /**
     * 界⾯已经显示过了(这个Activity已经在返回栈里了)
     * 不需要再次创建新界⾯的时候调⽤
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "onNewIntent");
        //处理动作
        processIntent(intent);
    }

    @OnClick(R.id.ll_user)
    public void onUserClick(){
        /*ToastUtil.errorShortToast("onUserClick");*/
    }
}