
package com.android.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;

import com.android.rahul_lohra.redditstar.modal.ListingDS;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@lombok.Data
public class FrontPageResponseData extends ListingDS {


    @SerializedName("children")
    @Expose
    private List<FrontPageChild> children = null;


}
