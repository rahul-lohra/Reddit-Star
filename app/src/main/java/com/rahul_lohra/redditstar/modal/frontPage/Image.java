
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@lombok.Data
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

}
