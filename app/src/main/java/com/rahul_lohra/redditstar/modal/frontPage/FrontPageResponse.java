
package com.rahul_lohra.redditstar.modal.frontPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class FrontPageResponse {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private FrontPageResponseData data;

    public String getKind() {
        return kind;
    }

    public FrontPageResponseData getData() {
        return data;
    }
}
