
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@lombok.Data
public class Preview {

    @SerializedName("images")
    @Expose
    public List<Image> images = null;

}
