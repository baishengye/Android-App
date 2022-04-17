package com.bo.cloudmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bo.cloudmusic.fragment.GuideFragment;

public class GuideAdapter extends BaseFragmentPagerAdapter<Integer> {
    public GuideAdapter(Context context,@NonNull FragmentManager fm){
        super(context,fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(getData(position));
    }
}
