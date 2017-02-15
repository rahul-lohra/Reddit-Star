package com.android.rahul_lohra.redditstar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.subreddit.SubredditFragment;

public class SubredditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit);

        if(null==savedInstanceState){
            String name = getIntent().getStringExtra("name");
            showSubredditFragment(name);
        }
    }

    void showSubredditFragment(String name){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SubredditFragment.newInstance(name,""), SubredditFragment.class.getSimpleName())
                .commit();
    }
}
