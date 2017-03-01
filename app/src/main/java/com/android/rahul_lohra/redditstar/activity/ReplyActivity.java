package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.ReplyFragment;

public class ReplyActivity extends BaseActivity {

    String thingId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        if(null==savedInstanceState){
            Intent intent = getIntent();
            thingId = intent.getStringExtra("thing_id");
            showReplyFragment();
        }
    }
    private void showReplyFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, ReplyFragment.newInstance(thingId), ReplyFragment.class.getSimpleName())
                .commit();
    }

}
