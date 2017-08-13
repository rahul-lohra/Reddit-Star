
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gif {

    @SerializedName("source")
    @Expose
    public Source_ source;
    @SerializedName("resolutions")
    @Expose
    public List<Resolution_> resolutions = null;

}
