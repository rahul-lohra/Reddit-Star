
package com.rahul_lohra.redditstar.modal.frontPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FrontPageChildData {

    @SerializedName("locked")
    @Expose
    private Boolean locked;
//    @SerializedName("is_self")
//    @Expose
//    private Boolean is_self;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("likes")
    @Expose
    private Boolean likes;
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
    private Media media;
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
    @SerializedName("thumbnail_height")
    @Expose
    private int thumbnailHeight;
    @SerializedName("thumbnail_width")
    @Expose
    private int thumbnailWidth;

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public Boolean getLocked() {
        return locked;
    }

    public String getDomain() {
        return domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

    public Object getSecureMedia() {
        return secureMedia;
    }

    public Boolean getSaved() {
        return saved;
    }

    public String getId() {
        return id;
    }

    public SecureMediaEmbed getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public Boolean getClicked() {
        return clicked;
    }

    public String getAuthor() {
        return author;
    }

    public Media getMedia() {
        return media;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Object getApprovedBy() {
        return approvedBy;
    }

    public Boolean getOver18() {
        return over18;
    }

    public Object getRemovalReason() {
        return removalReason;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public Preview getPreview() {
        return preview;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public Integer getDowns() {
        return downs;
    }

    public List<Object> getModReports() {
        return modReports;
    }

    public Boolean getArchived() {
        return archived;
    }

    public MediaEmbed getMediaEmbed() {
        return mediaEmbed;
    }

    public String getPostHint() {
        return postHint;
    }

    public Boolean getSelf() {
        return isSelf;
    }

    public Boolean getHideScore() {
        return hideScore;
    }

    public String getPermalink() {
        return permalink;
    }

    public Integer getCreated() {
        return created;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCreatedUtc() {
        return createdUtc;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public Boolean getVisited() {
        return visited;
    }

    public Object getNumReports() {
        return numReports;
    }

    public Integer getUps() {
        return ups;
    }
}
