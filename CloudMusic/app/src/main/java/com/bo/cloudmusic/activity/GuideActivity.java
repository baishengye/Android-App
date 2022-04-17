package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import com.bo.cloudmusic.Adapter.GuideAdapter;
import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.databinding.ActivityGuideBinding;
import com.bo.cloudmusic.fragment.GuideFragment;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.PreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button btLoginOrRegister;
    private Button btEnter;
    private ViewPager vp;
    private GuideAdapter adapter;
    private CircleIndicator ci;

    @Override
    protected void initViews() {
        super.initViews();

        hideStatusBar();

        //登录体验按钮
        btLoginOrRegister = findViewById(R.id.bt_login_or_register);
        btEnter = findViewById(R.id.bt_enter);

        //ViewPager控件
        vp=findViewById(R.id.vp);

        //找到指示器控件
        ci=findViewById(R.id.ci);

        //测试显示GuideFragment
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, GuideFragment.newInstance(R.drawable.guide1))
                .commit();*/
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //创建适配器
        adapter = new GuideAdapter(getSupportFragmentManager());

        //设置适配器到控件
        vp.setAdapter(adapter);

        //让指示器根据列表控件配合⼯作
        ci.setViewPager(vp);

        //适配器注册数据源观察者
        adapter.registerDataSetObserver(ci.getDataSetObserver());

        //准备数据
        List<Integer> datum=new ArrayList<>();
        datum.add(R.drawable.guide1);
        datum.add(R.drawable.guide2);
        datum.add(R.drawable.guide3);
        datum.add(R.drawable.guide4);
        datum.add(R.drawable.guide5);

        //设置数据到适配器
        adapter.setDatum(datum);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btLoginOrRegister.setOnClickListener(this);
        btEnter.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_or_register:
                startActivityAfterFinishThis(LoginOrRegisterActivity.class);
                setShowGuide(true);
                break;
            case R.id.bt_enter:
                startActivityAfterFinishThis(MainActivity.class);
                setShowGuide(false);
                break;
        }
    }

    /**
     * 设置已经显示了引导界⾯
     */
    private void setShowGuide(boolean isShowGuide) {
        sp.setShowGuide(isShowGuide);
    }
}