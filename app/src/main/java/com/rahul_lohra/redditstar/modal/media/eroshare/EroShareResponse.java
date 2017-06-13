
package com.rahul_lohra.redditstar.modal.media.eroshare;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rahul_lohra.redditstar.contract.IMedia;

public class EroShareResponse implements IMedia{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public Object title;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("items")
    @Expose
    public List<EroshareItem> items = new ArrayList<>();

    @Override
    public List<String> getMediaUrlList() {
        List<String> urlList = new ArrayList<>();
        if(items.size()>0){
            for (EroshareItem eroshareItem:items){
                if(eroshareItem.type.equals("Video")){
                    urlList.add(eroshareItem.urlMp4);
                }else if(eroshareItem.type.equals("Image")) {
                    urlList.add(eroshareItem.urlFullProtocol);
                }
            }
        }
        return urlList;
    }
}
