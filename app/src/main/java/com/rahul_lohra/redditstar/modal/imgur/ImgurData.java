
package com.rahul_lohra.redditstar.modal.imgur;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgurData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("animated")
    @Expose
    private Boolean animated;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("vote")
    @Expose
    private Object vote;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;
    @SerializedName("nsfw")
    @Expose
    private Boolean nsfw;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("account_url")
    @Expose
    private Object accountUrl;
    @SerializedName("account_id")
    @Expose
    private Object accountId;
    @SerializedName("is_ad")
    @Expose
    private Boolean isAd;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("in_gallery")
    @Expose
    private Boolean inGallery;
    @SerializedName("mp4")
    @Expose
    private String mp4;
    @SerializedName("gifv")
    @Expose
    private String gifv;
    @SerializedName("mp4_size")
    @Expose
    private Integer mp4Size;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("looping")
    @Expose
    private Boolean looping;
    @SerializedName("images")
    @Expose
    public List<ImgurData> imagesList = new ArrayList<>();

    public List<ImgurData> getImagesList() {
        return imagesList;
    }

    public String getId() {
        return id;
    }

    public Object getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getSize() {
        return size;
    }

    public String getMp4() {
        return mp4;
    }

    public String getGifv() {
        return gifv;
    }

    public Integer getMp4Size() {
        return mp4Size;
    }

    public String getLink() {
        return link;
    }

    public Boolean getLooping() {
        return looping;
    }

}
