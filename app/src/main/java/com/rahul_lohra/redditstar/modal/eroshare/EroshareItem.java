
package com.rahul_lohra.redditstar.modal.eroshare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EroshareItem {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("conversion_progress")
    @Expose
    public Integer conversionProgress;
    @SerializedName("video_duration")
    @Expose
    public Integer videoDuration;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("url_full_protocol_encoded")
    @Expose
    public String urlFullProtocolEncoded;
    @SerializedName("url_full_protocol")
    @Expose
    public String urlFullProtocol;
    @SerializedName("url_full")
    @Expose
    public String urlFull;
    @SerializedName("url_thumb")
    @Expose
    public String urlThumb;
    @SerializedName("url_orig")
    @Expose
    public String urlOrig;
    @SerializedName("url_mp4")
    @Expose
    public String urlMp4;
    @SerializedName("url_mp4_lowres")
    @Expose
    public String urlMp4Lowres;
    @SerializedName("position")
    @Expose
    public Integer position;
    @SerializedName("is_portrait")
    @Expose
    public Boolean isPortrait;
    @SerializedName("lowres")
    @Expose
    public Boolean lowres;

}
