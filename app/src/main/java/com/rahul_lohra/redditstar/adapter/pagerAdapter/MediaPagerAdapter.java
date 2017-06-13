package com.rahul_lohra.redditstar.adapter.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rahul_lohra.redditstar.fragments.media.ExoPlayerFragment;
import com.rahul_lohra.redditstar.fragments.media.ImageFragment;

import java.util.List;

/**
 * Created by rkrde on 15-04-2017.
 */

public class MediaPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> urlList;
    public MediaPagerAdapter(FragmentManager fm,List<String> urlList) {
        super(fm);
        this.urlList = urlList;
    }

    @Override
    public Fragment getItem(int position) {
        return determineFragment(position);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    private Fragment determineFragment(int pos){
        String str = urlList.get(pos);
        Fragment f = ImageFragment.newInstance(str);
        if(isUrlVideoType(str)){
            f =  ExoPlayerFragment.newInstance(str);
        }
        return f;
    }
    
    private boolean isUrlVideoType(String url){
        return(url.endsWith("mp4")||url.endsWith("webM")||url.endsWith("gif")||url.endsWith("gifv"));
    }
}
