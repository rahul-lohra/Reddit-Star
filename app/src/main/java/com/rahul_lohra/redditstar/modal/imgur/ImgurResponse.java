
package com.rahul_lohra.redditstar.modal.imgur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rahul_lohra.redditstar.contract.IMedia;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getMediaUrlList() {
        List<String> urlList = new ArrayList<>();
        if(status ==200){

            if(data.getImagesList().size()<=1){
                String mediaType = data.getType();
                if(mediaType.equals("image/jpeg")||mediaType.equals("image/jpg")||mediaType.equals("image/png")||mediaType.equals("image/webp")){
                    urlList.add(data.getLink()) ;
                }else if(data.getType().equals("image/gif")){
                    urlList.add(data.getMp4());
                }
            }else {
                for (ImgurData imgurData:data.getImagesList()){
                    String mediaType = imgurData.getType();
                    if(mediaType.equals("image/jpeg")||mediaType.equals("image/jpg")||mediaType.equals("image/png")||mediaType.equals("image/webp")){
                        urlList.add(imgurData.getLink()) ;
                    }else if(imgurData.getType().equals("image/gif")){
                        urlList.add(imgurData.getMp4());
                    }

                }
            }

        }
        return urlList;
    }
}
