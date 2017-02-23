package com.android.rahul_lohra.redditstar.modal.transfer;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by rkrde on 22-02-2017.
 */

public class DetailSubredditModal implements Parcelable {
    String id;
    String subreddit;
    String ups;
    String title;
    String commentsCount;
    String thumbnail;
    String time;
    String author;

    public DetailSubredditModal(String id, String subreddit, String ups, String title, String commentsCount, String thumbnail, String time, String author) {
        this.id = id;
        this.subreddit = subreddit;
        this.ups = ups;
        this.title = title;
        this.commentsCount = commentsCount;
        this.thumbnail = thumbnail;
        this.time = time;
        this.author = author;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.subreddit);
        dest.writeString(this.ups);
        dest.writeString(this.title);
        dest.writeString(this.commentsCount);
        dest.writeString(this.thumbnail);
        dest.writeString(this.time);
        dest.writeString(this.author);
    }

    protected DetailSubredditModal(Parcel in) {
        this.id = in.readString();
        this.subreddit = in.readString();
        this.ups = in.readString();
        this.title = in.readString();
        this.commentsCount = in.readString();
        this.thumbnail = in.readString();
        this.time = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<DetailSubredditModal> CREATOR = new Parcelable.Creator<DetailSubredditModal>() {
        @Override
        public DetailSubredditModal createFromParcel(Parcel source) {
            return new DetailSubredditModal(source);
        }

        @Override
        public DetailSubredditModal[] newArray(int size) {
            return new DetailSubredditModal[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
