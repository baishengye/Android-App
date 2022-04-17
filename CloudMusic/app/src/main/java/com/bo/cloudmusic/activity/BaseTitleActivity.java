package com.bo.cloudmusic.activity;

import androidx.appcompat.widget.Toolbar;

import com.bo.cloudmusic.R;

import butterknife.BindView;

public class BaseTitleActivity extends BaseCommonActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化ToolBar
        setSupportActionBar(toolbar);
    }

}
