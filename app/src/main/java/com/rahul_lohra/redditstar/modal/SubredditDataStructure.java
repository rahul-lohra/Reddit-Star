
package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SubredditDataStructure {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("header_img")
    @Expose
    private String headerImg;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("over18")
    @Expose
    private Boolean over18;
    @SerializedName("icon_img")
    @Expose
    private String iconImg;
    @SerializedName("accounts_active")
    @Expose
    private Integer accountsActive;
    @SerializedName("subscribers")
    @Expose
    private Integer subscribers;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("key_color")
    @Expose
    private String keyColor;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("user_is_moderator")
    @Expose
    private Boolean userIsModerator;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("submission_type")
    @Expose
    private String submissionType;
    @SerializedName("user_is_subscriber")
    @Expose
    private Boolean userIsSubscriber;

} 