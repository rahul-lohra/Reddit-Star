package com.rahul_lohra.redditstar.contract;

import android.content.Intent;

/**
 * Created by rkrde on 10-03-2017.
 */

public interface IActivity {
    void openActivity(Intent intent);
    void refreshToken();
}
