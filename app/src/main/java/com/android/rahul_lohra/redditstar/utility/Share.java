package com.android.rahul_lohra.redditstar.utility;

import android.app.Activity;
import android.content.Intent;

import com.android.rahul_lohra.redditstar.R;

/**
 * Created by rkrde on 27-02-2017.
 */

public class Share {

    public void shareUrl(Activity activity, String text){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,text);
        activity.startActivity(Intent.createChooser(shareIntent,activity.getString(R.string.share_using)));
    }
}
