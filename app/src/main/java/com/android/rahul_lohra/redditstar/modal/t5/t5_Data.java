package com.android.rahul_lohra.redditstar.modal.t5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rkrde on 15-02-2017.
 */
@Data
public class t5_Data {
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("submit_text_html")
    @Expose
    private Object submitTextHtml;
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
    private Object userIsContributor;
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
    @SerializedName("icon_size")
    @Expose
    private Object iconSize;
    @SerializedName("suggested_comment_sort")
    @Expose
    private Object suggestedCommentSort;
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
}
