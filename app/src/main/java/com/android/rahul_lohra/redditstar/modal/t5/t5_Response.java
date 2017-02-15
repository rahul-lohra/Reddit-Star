package com.android.rahul_lohra.redditstar.modal.t5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rkrde on 15-02-2017.
 */
@Data
public class t5_Response {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private com.android.rahul_lohra.redditstar.modal.t5.t5_Data data;
}
