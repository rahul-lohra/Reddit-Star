
package com.rahul_lohra.redditstar.modal.frontPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@lombok.Data
public class Resolution_ {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;

}
