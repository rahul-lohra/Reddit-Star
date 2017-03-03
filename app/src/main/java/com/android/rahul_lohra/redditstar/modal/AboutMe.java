
package com.android.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
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

}
