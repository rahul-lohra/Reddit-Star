package com.rahul_lohra.redditstar.modal.reply;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



/**
 * Created by rkrde on 01-03-2017.
 */
public class JsonData {
    @SerializedName("json")
    @Expose
    private ArrayList<String> mError = new ArrayList<>();

    @SerializedName("data")
    @Expose
    private Object mData;

    public ArrayList<String> getmError() {
        return mError;
    }

    public Object getmData() {
        return mData;
    }
}
