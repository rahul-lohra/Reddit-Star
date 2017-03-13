
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;

import com.rahul_lohra.redditstar.modal.ListingDS;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrontPageResponseData extends ListingDS {

    @SerializedName("children")
    @Expose
    private List<FrontPageChild> children = null;

    public List<FrontPageChild> getChildren() {
        return children;
    }

    public void setChildren(List<FrontPageChild> children) {
        this.children = children;
    }
}
