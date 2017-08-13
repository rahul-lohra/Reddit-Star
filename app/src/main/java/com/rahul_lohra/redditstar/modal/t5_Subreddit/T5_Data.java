package com.rahul_lohra.redditstar.modal.t5_Subreddit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * Created by rkrde on 15-02-2017.
 */
public class T5_Data {
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

    public String getBannerImg() {
        return bannerImg;
    }

    public Object getSubmitTextHtml() {
        return submitTextHtml;
    }

    public Boolean getUserIsBanned() {
        return userIsBanned;
    }

    public Boolean getWikiEnabled() {
        return wikiEnabled;
    }

    public Boolean getShowMedia() {
        return showMedia;
    }

    public String getId() {
        return id;
    }

    public Object getUserIsContributor() {
        return userIsContributor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCollapseDeletedComments() {
        return collapseDeletedComments;
    }

    public String getPrivateDescription() {
        return privateDescription;
    }

    public Boolean getOver18() {
        return over18;
    }

    public Boolean getSpoilersEnabled() {
        return spoilersEnabled;
    }

    public Object getIconSize() {
        return iconSize;
    }

    public Object getSuggestedCommentSort() {
        return suggestedCommentSort;
    }

    public String getIconImg() {
        return iconImg;
    }

    public Boolean getUserIsMuted() {
        return userIsMuted;
    }

    public String getSubmitLinkLabel() {
        return submitLinkLabel;
    }

    public Integer getAccountsActive() {
        return accountsActive;
    }

    public Boolean getPrivateTraffic() {
        return privateTraffic;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public String getLang() {
        return lang;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public String getName() {
        return name;
    }

    public Integer getCreated() {
        return created;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getQuarantine() {
        return quarantine;
    }

    public Integer getCreatedUtc() {
        return createdUtc;
    }

    public Boolean getUserIsModerator() {
        return userIsModerator;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
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
