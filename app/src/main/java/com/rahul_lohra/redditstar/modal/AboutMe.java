
package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;


public class AboutMe {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("over_18")
    @Expose
    private Boolean over18;
    @SerializedName("id")
    @Expose
    private String redditId;

    public String getName() {
        return name;
    }

    public Boolean getOver18() {
        return over18;
    }

    public String getRedditId() {
        return redditId;
    }
}
