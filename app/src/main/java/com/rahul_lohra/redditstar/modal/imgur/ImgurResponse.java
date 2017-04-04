
package com.rahul_lohra.redditstar.modal.imgur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rahul_lohra.redditstar.contract.IMedia;

public class ImgurResponse implements IMedia{

    @SerializedName("data")
    @Expose
    private ImgurData data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public ImgurData getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMediaUrl() {
        if(status ==200){
            return data.getMp4();
        }
        return null;
    }
}
