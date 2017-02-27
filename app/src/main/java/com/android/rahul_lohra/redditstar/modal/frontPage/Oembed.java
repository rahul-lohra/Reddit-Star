package com.android.rahul_lohra.redditstar.modal.frontPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 27-02-2017.
 */

public class Oembed {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("provider_url")
    @Expose
    private String providerUrl;
    @SerializedName("thumbnail_height")
    @Expose
    private Integer thumbnailHeight;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("thumbnail_width")
    @Expose
    private Integer thumbnailWidth;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("html")
    @Expose
    private String html;

    public String getDescription() {
        return description;
    }

    public Integer getHeight() {
        return height;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public Integer getWidth() {
        return width;
    }

    public String getHtml() {
        return html;
    }
}
