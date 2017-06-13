
package com.rahul_lohra.redditstar.modal.t1_Comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("data")
    @Expose
    public ParentData data;

    public Example(String kind, ParentData data) {
        this.kind = kind;
        this.data = data;
    }
}
