package com.android.rahul_lohra.redditstar.utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.WebViewActivity;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.AUTH_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.CLIENT_ID;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.REDIRECT_URI;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.STATE;

/**
 * Created by rkrde on 28-02-2017.
 */

public class CommonOperations {
    public static void addNewAccount(Activity activity){
        String scopeArray[] = activity.getResources().getStringArray(R.array.scope);
        String scope = MyUrl.getProperScope(scopeArray);
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }
}
