package com.rahul_lohra.redditstar.Utility;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.rahul_lohra.redditstar.activity.SubredditActivity;

/**
 * Created by rkrde on 15-04-2017.
 */

public class ActivityPortal {

    public static void openSubredditActivity(@NonNull String name,@NonNull String fullName,@NonNull Activity activity){
        Intent intent = new Intent(activity, SubredditActivity.class);
        intent.putExtra("name", name);//aww
        intent.putExtra("fullName", fullName);//t5_2qh1o
        intent.putExtra("subredditId", fullName.substring(3));//2qh1o
        activity.startActivity(intent);
    }
}
