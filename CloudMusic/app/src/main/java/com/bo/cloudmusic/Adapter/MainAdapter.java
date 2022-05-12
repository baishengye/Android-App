package com.bo.cloudmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bo.cloudmusic.R;
import com.bo.cloudmusic.fragment.DiscoveryFragment;
import com.bo.cloudmusic.fragment.FeedFragment;
import com.bo.cloudmusic.fragment.MeFragment;
import com.bo.cloudmusic.fragment.VideoFragment;

public class MainAdapter extends BaseFragmentPagerAdapter<Integer> {
    //指示器标题
    private static final Integer[] titleResources={R.string.me,R.string.discovery,R.string.friend,R.string.video};

    /**
     * 构造方法
     *
     * @param context
     * @param fm
     */
    public MainAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回当前位置的fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MeFragment.newInstance();
        } else if (position == 1) {
            return DiscoveryFragment.newInstance();
        } else if (position == 2) {
            return FeedFragment.newInstance();
        } else {
            return VideoFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titleResources[position]);
    }
}
