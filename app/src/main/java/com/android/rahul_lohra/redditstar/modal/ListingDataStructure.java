
package com.android.rahul_lohra.redditstar.modal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingDataStructure {

    @SerializedName("modhash")
    @Expose
    public Object modhash;
    @SerializedName("children")
    @Expose
    public List<ListingChild> children = null;
    @SerializedName("after")
    @Expose
    public String after;
    @SerializedName("before")
    @Expose
    public Object before;

}
