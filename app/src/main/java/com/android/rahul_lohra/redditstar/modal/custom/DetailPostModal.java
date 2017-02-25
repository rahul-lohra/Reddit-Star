package com.android.rahul_lohra.redditstar.modal.custom;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkrde on 22-02-2017.
 */

public class DetailPostModal implements Parcelable {
    String id;
    String subreddit;
    String ups;
    String title;
    String commentsCount;
    String thumbnail;
    String time;
    String author;
    List<String> bigImageUrlList = new ArrayList<>();

    public DetailPostModal(String id, String subreddit, String ups, String title, String commentsCount, String thumbnail, String time, String author, List<String> bigImageUrlList) {
        this.id = id;
        this.subreddit = subreddit;
        this.ups = ups;
        this.title = title;
        this.commentsCount = commentsCount;
        this.thumbnail = thumbnail;
        this.time = time;
        this.author = author;
        this.bigImageUrlList = bigImageUrlList;
    }

    public List<String> getBigImageUrlList() {
        return bigImageUrlList;
    }

    public void setBigImageUrlList(List<String> bigImageUrlList) {
        this.bigImageUrlList = bigImageUrlList;
    }

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
        dest.writeStringList(this.bigImageUrlList);
    }

    protected DetailPostModal(Parcel in) {
        this.id = in.readString();
        this.subreddit = in.readString();
        this.ups = in.readString();
        this.title = in.readString();
        this.commentsCount = in.readString();
        this.thumbnail = in.readString();
        this.time = in.readString();
        this.author = in.readString();
        this.bigImageUrlList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<DetailPostModal> CREATOR = new Parcelable.Creator<DetailPostModal>() {
        @Override
        public DetailPostModal createFromParcel(Parcel source) {
            return new DetailPostModal(source);
        }

        @Override
        public DetailPostModal[] newArray(int size) {
            return new DetailPostModal[size];
        }
    };
}
