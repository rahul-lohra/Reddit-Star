
package com.android.rahul_lohra.redditstar.modal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ListingDataStructure {

    @SerializedName("modhash")
    @Expose
    private Object modhash;
    @SerializedName("children")
    @Expose
    private List<ListingChild> children = null;
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;

}
