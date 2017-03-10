package com.android.rahul_lohra.redditstar.modal.reply;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rkrde on 01-03-2017.
 */
@Data
public class ReplyResponse {
    @SerializedName("json")
    @Expose
    private JsonData jsonData;
}
