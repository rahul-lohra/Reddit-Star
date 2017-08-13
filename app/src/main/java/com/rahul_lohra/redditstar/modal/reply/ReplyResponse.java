package com.rahul_lohra.redditstar.modal.reply;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * Created by rkrde on 01-03-2017.
 */
public class ReplyResponse {
    @SerializedName("json")
    @Expose
    private JsonData jsonData;

    public JsonData getJsonData() {
        return jsonData;
    }
}
