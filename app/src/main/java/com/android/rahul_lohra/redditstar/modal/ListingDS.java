package com.android.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rkrde on 05-02-2017.
 */
@Data
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
}
