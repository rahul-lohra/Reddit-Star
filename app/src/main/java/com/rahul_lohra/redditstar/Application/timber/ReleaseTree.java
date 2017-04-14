package com.rahul_lohra.redditstar.Application.timber;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by rkrde on 13-04-2017.
 */


public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

    }

    @Override
    protected boolean isLoggable(String tag, int priority) {
        if(priority == Log.VERBOSE ||priority == Log.INFO||priority == Log.DEBUG){
            return false;
        }
        return true;
    }
}
