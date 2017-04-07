package com.rahul_lohra.redditstar.viewHolder.modal;

import android.app.Activity;

/**
 * Created by rkrde on 06-04-2017.
 */

public class PostViewData{
    Activity activity;
    String thumbnail;
    String url;
    String domain;
    String bigImageUrl;
    Integer likes;
    String subreddit;

    public PostViewData(Activity activity, String thumbnail, String url, String domain, String bigImageUrl, Integer likes, String subreddit) {
        this.activity = activity;
        this.thumbnail = thumbnail;
        this.url = url;
        this.domain = domain;
        this.bigImageUrl = bigImageUrl;
        this.likes = likes;
        this.subreddit = subreddit;
    }

    public Integer getLikes() {
        return likes;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }
}
