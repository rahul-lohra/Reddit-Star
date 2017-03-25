package com.rahul_lohra.redditstar.Utility;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by rkrde on 02-03-2017.
 */

public class SpConstants {
    public final static String WIDGET_SUBSCRIBE = "widget_subscribe";
    @Retention(SOURCE)
    @IntDef({POPULAR, FRONT_PAGE, SUBREDDIT,FAV})
    public @interface SubMode {}
    public static final int POPULAR = 1;
    public static final int FRONT_PAGE = 2;
    public static final int SUBREDDIT = 3;
    public static final int FAV = 4;

    public final static String WIDGET_AFTER = "widget_after";

    public final static String SORT = "sort";
    public final static String TIME = "time";
    public final static String OVER_18 = "over18";


    public final static String LAYOUT_TYPE = "layout_type";





}
