
package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ListingChild {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private SubredditDataStructure data;

    public String getKind() {
        return kind;
    }

    public SubredditDataStructure getData() {
        return data;
    }
}
