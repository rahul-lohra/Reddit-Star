package com.rahul_lohra.redditstar.adapter.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rahul_lohra.redditstar.fragments.media.ExoPlayerFragment;
import com.rahul_lohra.redditstar.fragments.media.ImageFragment;
import com.rahul_lohra.redditstar.fragments.profile.CommentsFragment;
import com.rahul_lohra.redditstar.fragments.profile.GlidedFragment;
import com.rahul_lohra.redditstar.fragments.profile.OverviewFragment;
import com.rahul_lohra.redditstar.fragments.profile.SubmittedFragment;

import java.util.List;

/**
 * Created by rkrde on 15-04-2017.
 */

public class ProfileAdapter extends FragmentStatePagerAdapter {

    private int count;
    public ProfileAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position){
            case 1: f = SubmittedFragment.newInstance("","");
                break;
            case 2: f = CommentsFragment.newInstance("","");
                break;
            case 3: f = GlidedFragment.newInstance("","");
                    break;
            case 0:
                default:
                    f =  OverviewFragment.newInstance("","");
                break;

        }
        return f;
    }

    @Override
    public int getCount() {
        return count;
    }

    

}
