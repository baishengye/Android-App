package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bo.cloudmusic.Adapter.SongAdapter;
import com.bo.cloudmusic.R;
import com.bo.cloudmusic.api.Api;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.domain.response.DetailResponse;
import com.bo.cloudmusic.domain.response.ListResponse;
import com.bo.cloudmusic.listener.HttpObserver;
import com.bo.cloudmusic.utils.Constant;
import com.bo.cloudmusic.utils.ImageUtil;
import com.bo.cloudmusic.utils.LogUtil;

import org.apache.commons.lang3.StringUtils;

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

    /**
     * 头部容器
     */
    private LinearLayout ll_header;
    /**
     * 封面banner
     */
    private ImageView iv_banner;
    /**
     * 标题
     */
    private TextView tv_title;
    /**
     * 头像
     */
    private ImageView iv_avatar;
    /**
     *按钮容器
     */
    private LinearLayout ll_comment_container;
    /**
     * 昵称
     */
    private TextView tv_nickname;
    /**
     * 评论数
     */
    private TextView tv_comment_count;
    /**
     * 收藏按钮
     */
    private Button bt_collection;
    /**
     * 水平容器
     */
    private LinearLayout ll_play_all_container;
    /**
     * 歌曲数量
     */
    private TextView tv_count;

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

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //把适配器添加到rv中
        rv.setAdapter(adapter);

        fetchData();
    }

    /**
     * 创建头部
     * @return
     */
    private View createHeaderView(){
        //从xml填充布局
        View view=getLayoutInflater().inflate(R.layout.header_sheet_detail, (ViewGroup) rv.getParent(),false);

        //头部容器
        ll_header = view.findViewById(R.id.ll_header);
        
        //封⾯图
        iv_banner = view.findViewById(R.id.iv_banner);
        
        //标题
        tv_title = view.findViewById(R.id.tv_title);

        //歌单创建者头像
        iv_avatar = view.findViewById(R.id.iv_avatar);

        //歌单创建者昵称
        tv_nickname = view.findViewById(R.id.tv_nickname);

        //评论容器
        ll_comment_container = view.findViewById(R.id.ll_comment_container);

        //评论数
        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        //收藏按钮
        bt_collection = view.findViewById(R.id.bt_collection);

        //播放全部容器
        ll_play_all_container = view.findViewById(R.id.ll_play_all_container);

        //歌曲数
        tv_count = view.findViewById(R.id.tv_count);
        

        //返回view
        return view;
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

        //显示封面
        if(StringUtils.isBlank(data.getBanner())){
            //空的就显示默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        }else{
            //有图片
            ImageUtil.show(getMainActivity(),iv_banner,data.getBanner());
        }

        //显示标题
        tv_title.setText(data.getTitle());

        //头像
        ImageUtil.showAvatar(getMainActivity(),iv_avatar,data.getUser().getAvatar());

        //昵称
        tv_nickname.setText(data.getUser().getNickname());

        //评论数
        tv_comment_count.setText(String.valueOf(data.getComments_count()));

        //音乐数
        tv_count.setText(String.valueOf(data.getSongs_count()));

    }

    /**
     * 获取intent中的id
     * @return
     */
    private String extraId() {
        return getIntent().getStringExtra(Constant.ID);
    }
}