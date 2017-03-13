package com.rahul_lohra.redditstar.modal.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 20-02-2017.
 */

public class T5_SearchResponse {
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("data")
    @Expose
    public T5_ListChild data;
}
