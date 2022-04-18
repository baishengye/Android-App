package com.bo.cloudmusic.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
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

        //是否显示返回按钮
        if(isShowBackMenu()){
            ShowBackMenu();
        }
    }

    /**
     * 显示返回按钮
     */
    private void ShowBackMenu() {
        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 是否显示返回按钮
     */
    protected boolean isShowBackMenu() {
        return true;
    }

    /**
     * 菜单点击了返回按钮
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //Toolbar返回按钮点击
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
