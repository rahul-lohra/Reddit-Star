
package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class SubredditResponse {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private ListingDataStructure data;

    public String getKind() {
        return kind;
    }

    public ListingDataStructure getData() {
        return data;
    }
}