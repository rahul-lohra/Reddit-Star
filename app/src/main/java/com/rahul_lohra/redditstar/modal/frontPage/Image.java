
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Image {

    @SerializedName("source")
    @Expose
    public Source source;
    @SerializedName("resolutions")
    @Expose
    public List<Resolution> resolutions = null;
    @SerializedName("variants")
    @Expose
    public Variants variants;
    @SerializedName("id")
    @Expose
    public String id;

    public Source getSource() {
        return source;
    }

    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public Variants getVariants() {
        return variants;
    }

    public String getId() {
        return id;
    }
}
