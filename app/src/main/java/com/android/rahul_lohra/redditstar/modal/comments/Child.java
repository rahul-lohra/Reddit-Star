
package com.android.rahul_lohra.redditstar.modal.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("t3data")
    @Expose
    public T3data t3data;
    @SerializedName("t1data")
    @Expose
    public T1data t1data;

    public Child(String kind, T3data t3data, T1data t1data) {
        this.kind = kind;
        this.t3data = t3data;
        this.t1data = t1data;
    }
}
