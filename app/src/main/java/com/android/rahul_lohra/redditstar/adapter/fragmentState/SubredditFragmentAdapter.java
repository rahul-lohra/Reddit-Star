package com.android.rahul_lohra.redditstar.adapter.fragmentState;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.rahul_lohra.redditstar.fragments.subreddit.HotFragment;

/**
 * Created by rkrde on 14-02-2017.
 */

public class SubredditFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    public SubredditFragmentAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HotFragment.newInstance("","");
            case 1:
                return HotFragment.newInstance("","");
            case 2:
                return HotFragment.newInstance("","");
            default:
                return HotFragment.newInstance("","");
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
