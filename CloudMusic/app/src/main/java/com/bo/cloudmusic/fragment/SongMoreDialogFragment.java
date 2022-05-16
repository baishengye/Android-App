package com.bo.cloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.domain.Sheet;
import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.utils.Constant;

public class SongMoreDialogFragment extends BaseBottomSheetDialogFragment{
    @Override
    public View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_song_more,null,false);
    }

    public static SongMoreDialogFragment newInstance(Sheet sheet, Song song) {

        //创建bundle
        Bundle args = new Bundle();

        //创建fragment
        SongMoreDialogFragment fragment = new SongMoreDialogFragment();

        //添加参数
        args.putSerializable(Constant.SHEET,sheet);
        args.putSerializable(Constant.SONG,song);

        //设置参数
        fragment.setArguments(args);

        //返回fragment
        return fragment;
    }

    /**
     * 显示对话框
     * @param fragmentManager
     * @param sheet
     * @param song
     */
    public static void show(FragmentManager fragmentManager,Sheet sheet,Song song){
        //创建fragment
        SongMoreDialogFragment fragment = newInstance(sheet, song);

        //显示
        fragment.show(fragmentManager,"song_more_dialog");
    }
}
