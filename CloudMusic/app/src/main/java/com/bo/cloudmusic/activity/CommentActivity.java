package com.bo.cloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.utils.Constant;

public class CommentActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }

    /**
     * 启动评论界面
     * @param activity 从哪个页面启动
     * @param sheetId 传入的歌单id
     */
    public static void start(Activity activity, String sheetId) {
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra(Constant.SHEET_ID,sheetId);
        activity.startActivity(intent);
    }
}