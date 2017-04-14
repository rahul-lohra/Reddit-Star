
package com.rahul_lohra.redditstar.modal.frontPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrontPageChild {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private FrontPageChildData data;

    public String getKind() {
        return kind;
    }

    public FrontPageChildData getData() {
        return data;
    }
}
