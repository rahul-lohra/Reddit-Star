
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@lombok.Data
public class Mp4 {

    @SerializedName("source")
    @Expose
    public Source__ source;
    @SerializedName("resolutions")
    @Expose
    public List<Resolution__> resolutions = null;

}
