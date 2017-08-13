
package com.rahul_lohra.redditstar.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



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

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getOver18() {
        return over18;
    }

    public String getIconImg() {
        return iconImg;
    }

    public Integer getAccountsActive() {
        return accountsActive;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public String getLang() {
        return lang;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getUserIsModerator() {
        return userIsModerator;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public Boolean getUserIsSubscriber() {
        return userIsSubscriber;
    }
}