package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rkrde on 05-02-2017.
 */
public class ListingDS {

    @SerializedName("modhash")
    @Expose
    protected Object modhash;
    @SerializedName("after")
    @Expose
    protected String after;
    @SerializedName("before")
    @Expose
    protected Object before;

    public Object getModhash() {
        return modhash;
    }

    public String getAfter() {
        return after;
    }

    public Object getBefore() {
        return before;
    }
}
