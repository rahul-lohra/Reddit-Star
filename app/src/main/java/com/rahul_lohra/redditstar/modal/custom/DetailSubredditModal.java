package com.rahul_lohra.redditstar.modal.custom;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 24-02-2017.
 */

public class DetailSubredditModal implements Parcelable {
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("user_is_banned")
    @Expose
    private Boolean userIsBanned;
    @SerializedName("wiki_enabled")
    @Expose
    private Boolean wikiEnabled;
    @SerializedName("show_media")
    @Expose
    private Boolean showMedia;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_is_contributor")
    @Expose
    private Boolean userIsContributor ;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("header_img")
    @Expose
    private String headerImg;
    @SerializedName("description_html")
    @Expose
    private String descriptionHtml;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("collapse_deleted_comments")
    @Expose
    private Boolean collapseDeletedComments;
    @SerializedName("private_description")
    @Expose
    private String privateDescription;
    @SerializedName("over18")
    @Expose
    private Boolean over18;
    @SerializedName("spoilers_enabled")
    @Expose
    private Boolean spoilersEnabled;
    @SerializedName("icon_img")
    @Expose
    private String iconImg;
    @SerializedName("user_is_muted")
    @Expose
    private Boolean userIsMuted;
    @SerializedName("submit_link_label")
    @Expose
    private String submitLinkLabel;
    @SerializedName("accounts_active")
    @Expose
    private Integer accountsActive;
    @SerializedName("private_traffic")
    @Expose
    private Boolean privateTraffic;
    @SerializedName("subscribers")
    @Expose
    private Integer subscribers;
    @SerializedName("key_color")
    @Expose
    private String keyColor;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("whitelist_status")
    @Expose
    private String whitelistStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("quarantine")
    @Expose
    private Boolean quarantine;
    @SerializedName("created_utc")
    @Expose
    private Integer createdUtc;
    @SerializedName("user_is_moderator")
    @Expose
    private Boolean userIsModerator;
    @SerializedName("advertiser_category")
    @Expose
    private String advertiserCategory;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("submission_type")
    @Expose
    private String submissionType;
    @SerializedName("user_is_subscriber")
    @Expose
    private Boolean userIsSubscriber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bannerImg);
        dest.writeValue(this.userIsBanned);
        dest.writeValue(this.wikiEnabled);
        dest.writeValue(this.showMedia);
        dest.writeString(this.id);
        dest.writeValue(this.userIsContributor);
        dest.writeString(this.displayName);
        dest.writeString(this.headerImg);
        dest.writeString(this.descriptionHtml);
        dest.writeString(this.title);
        dest.writeValue(this.collapseDeletedComments);
        dest.writeString(this.privateDescription);
        dest.writeValue(this.over18);
        dest.writeValue(this.spoilersEnabled);
        dest.writeString(this.iconImg);
        dest.writeValue(this.userIsMuted);
        dest.writeString(this.submitLinkLabel);
        dest.writeValue(this.accountsActive);
        dest.writeValue(this.privateTraffic);
        dest.writeValue(this.subscribers);
        dest.writeString(this.keyColor);
        dest.writeString(this.lang);
        dest.writeString(this.whitelistStatus);
        dest.writeString(this.name);
        dest.writeValue(this.created);
        dest.writeString(this.url);
        dest.writeValue(this.quarantine);
        dest.writeValue(this.createdUtc);
        dest.writeValue(this.userIsModerator);
        dest.writeString(this.advertiserCategory);
        dest.writeString(this.subredditType);
        dest.writeString(this.submissionType);
        dest.writeValue(this.userIsSubscriber);
    }

    public DetailSubredditModal() {
    }

    protected DetailSubredditModal(Parcel in) {
        this.bannerImg = in.readString();
        this.userIsBanned = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.wikiEnabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.showMedia = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.id = in.readString();
        this.userIsContributor = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.displayName = in.readString();
        this.headerImg = in.readString();
        this.descriptionHtml = in.readString();
        this.title = in.readString();
        this.collapseDeletedComments = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.privateDescription = in.readString();
        this.over18 = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.spoilersEnabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.iconImg = in.readString();
        this.userIsMuted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.submitLinkLabel = in.readString();
        this.accountsActive = (Integer) in.readValue(Integer.class.getClassLoader());
        this.privateTraffic = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.subscribers = (Integer) in.readValue(Integer.class.getClassLoader());
        this.keyColor = in.readString();
        this.lang = in.readString();
        this.whitelistStatus = in.readString();
        this.name = in.readString();
        this.created = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url = in.readString();
        this.quarantine = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.createdUtc = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userIsModerator = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.advertiserCategory = in.readString();
        this.subredditType = in.readString();
        this.submissionType = in.readString();
        this.userIsSubscriber = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailSubredditModal> CREATOR = new Parcelable.Creator<DetailSubredditModal>() {
        @Override
        public DetailSubredditModal createFromParcel(Parcel source) {
            return new DetailSubredditModal(source);
        }

        @Override
        public DetailSubredditModal[] newArray(int size) {
            return new DetailSubredditModal[size];
        }
    };
}
