package com.bo.cloudmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bo.cloudmusic.domain.Song;
import com.bo.cloudmusic.fragment.MusicRecordFragment;


/**
 * 黑胶唱片列表适配器
 */
public class MusicPlayAdapter extends BaseFragmentPagerAdapter<Song>{


    /**
     * 构造方法
     * @param context
     * @param fm
     */
    public MusicPlayAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回当前位置的Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MusicRecordFragment.newInstance(datum.get(position));
    }
}
