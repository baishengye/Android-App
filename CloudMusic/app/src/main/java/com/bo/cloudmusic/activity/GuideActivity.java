package com.bo.cloudmusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.cloudmusic.MainActivity;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.databinding.ActivityGuideBinding;
import com.bo.cloudmusic.fragment.GuideFragment;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.PreferencesUtil;

public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button btLoginOrRegister;
    private Button btEnter;

    @Override
    protected void initViews() {
        super.initViews();

        hideStatusBar();

        btLoginOrRegister = findViewById(R.id.bt_login_or_register);
        btEnter = findViewById(R.id.bt_enter);

        //测试显示GuideFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, GuideFragment.newInstance(R.drawable.guide1))
                .commit();
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