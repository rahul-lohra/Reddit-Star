
package com.rahul_lohra.redditstar.modal.GifyCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rahul_lohra.redditstar.contract.IMedia;

public class GifyCatResponse implements IMedia {

    @SerializedName("gfyItem")
    @Expose
    private GfyItem gfyItem;

    public GfyItem getGfyItem() {
        return gfyItem;
    }

    public void setGfyItem(GfyItem gfyItem) {
        this.gfyItem = gfyItem;
    }

    @Override
    public String getMediaUrl() {
        if(gfyItem!=null)
        {
            return gfyItem.getMp4Url();
        }
        return null;
    }
}
