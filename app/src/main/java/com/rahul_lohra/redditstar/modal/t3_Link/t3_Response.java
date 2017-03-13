package com.rahul_lohra.redditstar.modal.t3_Link;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 20-02-2017.
 */

public class t3_Response {

    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("data")
    @Expose
    public T3_Data t3data;
}
