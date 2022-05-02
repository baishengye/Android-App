package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;

import java.util.List;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    private static final String TAG = "SheetDetailActivity";
    /**
     * 歌单id
     */
    private String id;

    /**
     * 歌单数据
     */
    private Sheet data;

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

        fetchData();
    }

    /**
     * 请求歌单数据
     */
    private void fetchData(){
        Api.getInstance().sheetDetail(id)
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> data) {
                        next(data.getData());
                    }
                });
    }

    private void next(Sheet data) {
        this.data=data;
        LogUtil.d(TAG,data.toString());
    }

    /**
     * 获取intent中的id
     * @return
     */
    private String extraId() {
        return getIntent().getStringExtra(Constant.ID);
    }
}