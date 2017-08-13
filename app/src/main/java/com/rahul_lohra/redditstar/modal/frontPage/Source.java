
package com.rahul_lohra.redditstar.modal.frontPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Source {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("width")
    @Expose
    public int width;
    @SerializedName("height")
    @Expose
    public int height;

    public String getUrl() {
        return url;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
