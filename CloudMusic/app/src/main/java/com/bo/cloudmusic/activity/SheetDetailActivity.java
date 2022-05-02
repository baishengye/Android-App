package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.Constant;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的参数
        id = extraId();
    }

    /**
     * 获取intent中的id
     * @return
     */
    private String extraId() {
        return getIntent().getStringExtra(Constant.ID);
    }
}