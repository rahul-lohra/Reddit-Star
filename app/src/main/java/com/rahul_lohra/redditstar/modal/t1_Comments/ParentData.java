
package com.rahul_lohra.redditstar.modal.t1_Comments;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentData {

    @SerializedName("modhash")
    @Expose
    public String modhash;
    @SerializedName("children")
    @Expose
    public List<Child> children = null;
    @SerializedName("after")
    @Expose
    public String after;
    @SerializedName("before")
    @Expose
    public String before;

    public ParentData(String modhash, List<Child> children, String after, String before) {
        this.modhash = modhash;
        this.children = children;
        this.after = after;
        this.before = before;
    }
}
