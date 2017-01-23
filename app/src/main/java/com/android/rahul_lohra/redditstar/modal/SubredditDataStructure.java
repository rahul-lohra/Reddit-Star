
package com.android.rahul_lohra.redditstar.modal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubredditDataStructure {

    @SerializedName("banner_img")
    @Expose
    public String bannerImg;
    @SerializedName("submit_text_html")
    @Expose
    public String submitTextHtml;
    @SerializedName("user_is_banned")
    @Expose
    public Boolean userIsBanned;
    @SerializedName("wiki_enabled")
    @Expose
    public Boolean wikiEnabled;
    @SerializedName("show_media")
    @Expose
    public Boolean showMedia;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_is_contributor")
    @Expose
    public Boolean userIsContributor;
    @SerializedName("submit_text")
    @Expose
    public String submitText;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("header_img")
    @Expose
    public String headerImg;
    @SerializedName("description_html")
    @Expose
    public String descriptionHtml;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("collapse_deleted_comments")
    @Expose
    public Boolean collapseDeletedComments;
    @SerializedName("public_description")
    @Expose
    public String publicDescription;
    @SerializedName("over18")
    @Expose
    public Boolean over18;
    @SerializedName("public_description_html")
    @Expose
    public String publicDescriptionHtml;
    @SerializedName("spoilers_enabled")
    @Expose
    public Boolean spoilersEnabled;
    @SerializedName("icon_size")
    @Expose
    public List<Integer> iconSize = null;
    @SerializedName("suggested_comment_sort")
    @Expose
    public Object suggestedCommentSort;
    @SerializedName("icon_img")
    @Expose
    public String iconImg;
    @SerializedName("header_title")
    @Expose
    public Object headerTitle;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("user_is_muted")
    @Expose
    public Boolean userIsMuted;
    @SerializedName("submit_link_label")
    @Expose
    public Object submitLinkLabel;
    @SerializedName("accounts_active")
    @Expose
    public Object accountsActive;
    @SerializedName("public_traffic")
    @Expose
    public Boolean publicTraffic;
    @SerializedName("header_size")
    @Expose
    public List<Integer> headerSize = null;
    @SerializedName("subscribers")
    @Expose
    public Integer subscribers;
    @SerializedName("submit_text_label")
    @Expose
    public Object submitTextLabel;
    @SerializedName("lang")
    @Expose
    public String lang;
    @SerializedName("key_color")
    @Expose
    public String keyColor;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("created")
    @Expose
    public Integer created;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("quarantine")
    @Expose
    public Boolean quarantine;
    @SerializedName("hide_ads")
    @Expose
    public Boolean hideAds;
    @SerializedName("created_utc")
    @Expose
    public Integer createdUtc;
    @SerializedName("banner_size")
    @Expose
    public Object bannerSize;
    @SerializedName("user_is_moderator")
    @Expose
    public Boolean userIsModerator;
    @SerializedName("user_sr_theme_enabled")
    @Expose
    public Boolean userSrThemeEnabled;
    @SerializedName("show_media_preview")
    @Expose
    public Boolean showMediaPreview;
    @SerializedName("comment_score_hide_mins")
    @Expose
    public Integer commentScoreHideMins;
    @SerializedName("subreddit_type")
    @Expose
    public String subredditType;
    @SerializedName("submission_type")
    @Expose
    public String submissionType;
    @SerializedName("user_is_subscriber")
    @Expose
    public Boolean userIsSubscriber;

}
