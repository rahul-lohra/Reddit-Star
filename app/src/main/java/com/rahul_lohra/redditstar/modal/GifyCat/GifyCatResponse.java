
package com.rahul_lohra.redditstar.modal.GifyCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rahul_lohra.redditstar.contract.IMedia;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getMediaUrlList() {
        List<String> urlList = new ArrayList<>();
        if(gfyItem!=null)
        {
             urlList.add(gfyItem.getMp4Url());;
        }
        return urlList;
    }
}
