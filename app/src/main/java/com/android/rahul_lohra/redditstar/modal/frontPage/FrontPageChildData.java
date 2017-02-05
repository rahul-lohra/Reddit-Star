
package com.android.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.*;

@lombok.Data
public class FrontPageChildData {

    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("likes")
    @Expose
    private Object likes;
    @SerializedName("secure_media")
    @Expose
    private Object secureMedia;
    @SerializedName("saved")
    @Expose
    private Boolean saved;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("secure_media_embed")
    @Expose
    private SecureMediaEmbed secureMediaEmbed;
    @SerializedName("clicked")
    @Expose
    private Boolean clicked;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("media")
    @Expose
    private Object media;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("approved_by")
    @Expose
    private Object approvedBy;
    @SerializedName("over_18")
    @Expose
    private Boolean over18;
    @SerializedName("removal_reason")
    @Expose
    private Object removalReason;
    @SerializedName("hidden")
    @Expose
    private Boolean hidden;
    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("subreddit_id")
    @Expose
    private String subredditId;
    @SerializedName("edited")
    @Expose
    private Boolean edited;
    @SerializedName("downs")
    @Expose
    private Integer downs;
    @SerializedName("mod_reports")
    @Expose
    private List<Object> modReports = null;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("media_embed")
    @Expose
    private MediaEmbed mediaEmbed;
    @SerializedName("post_hint")
    @Expose
    private String postHint;
    @SerializedName("is_self")
    @Expose
    private Boolean isSelf;
    @SerializedName("hide_score")
    @Expose
    private Boolean hideScore;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_utc")
    @Expose
    private Integer createdUtc;
    @SerializedName("num_comments")
    @Expose
    private Integer numComments;
    @SerializedName("visited")
    @Expose
    private Boolean visited;
    @SerializedName("num_reports")
    @Expose
    private Object numReports;
    @SerializedName("ups")
    @Expose
    private Integer ups;

}
