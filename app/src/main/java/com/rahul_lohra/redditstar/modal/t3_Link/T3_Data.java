
package com.rahul_lohra.redditstar.modal.t3_Link;


import com.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3_Data {

    @SerializedName("created_utc")
    @Expose
    public Integer createdUtc;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("preview")
    @Expose
    public Preview preview;
    @SerializedName("subreddit")
    @Expose
    public String subreddit;
    @SerializedName("likes")
    @Expose
    public Boolean likes;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("clicked")
    @Expose
    public Boolean clicked;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("media")
    @Expose
    public Object media;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("over_18")
    @Expose
    public Boolean over18;
    @SerializedName("domain")
    @Expose
    public String domain;
    @SerializedName("num_comments")
    @Expose
    public Integer numComments;
    @SerializedName("subreddit_id")
    @Expose
    public String subredditId;
    @SerializedName("downs")
    @Expose
    public Integer downs;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("ups")
    @Expose
    public Integer ups;
    @SerializedName("upvote_ratio")
    @Expose
    public Double upvoteRatio;
    @SerializedName("visited")
    @Expose
    public Boolean visited;
    @SerializedName("num_reports")
    @Expose
    public Object numReports;
    @SerializedName("permalink")
    @Expose
    public String permalink;

    public T3_Data(String subreddit, Boolean likes, String id, Boolean clicked, String author, Object media, Integer score, Boolean over18, String domain, Integer numComments, String subredditId, Integer downs, String name, String url, String title, Integer ups, Double upvoteRatio, Boolean visited, Object numReports, String permalink) {
        this.subreddit = subreddit;
        this.likes = likes;
        this.id = id;
        this.clicked = clicked;
        this.author = author;
        this.media = media;
        this.score = score;
        this.over18 = over18;
        this.domain = domain;
        this.numComments = numComments;
        this.subredditId = subredditId;
        this.downs = downs;
        this.name = name;
        this.url = url;
        this.title = title;
        this.ups = ups;
        this.upvoteRatio = upvoteRatio;
        this.visited = visited;
        this.numReports = numReports;
        this.permalink = permalink;
    }
}
