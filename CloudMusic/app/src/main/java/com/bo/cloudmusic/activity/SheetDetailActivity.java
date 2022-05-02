package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.bo.cloudmusic.Adapter.SongAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.LogUtil;

import java.util.List;

import butterknife.BindView;

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

    /**
     * 歌曲适配器
     */
    private SongAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //尺寸固定
        rv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);

        /*//分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getMainActivity(), RecyclerView.VERTICAL);

        //添加到控件
        //可以添加多个
        rv.addItemDecoration(decoration);*/
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的参数
        id = extraId();

        //new 出适配器
        adapter = new SongAdapter(R.layout.item_song_detail);

        //把适配器添加到rv中
        rv.setAdapter(adapter);

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
       // LogUtil.d(TAG,data.toString());

        //设置数据
        List<Song> songs=data.getSongs();
        if(songs!=null&&songs.size()>0){
            adapter.replaceData(songs);
        }

    }

    /**
     * 获取intent中的id
     * @return
     */
    private String extraId() {
        return getIntent().getStringExtra(Constant.ID);
    }
}