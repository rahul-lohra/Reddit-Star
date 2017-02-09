
package com.android.rahul_lohra.redditstar.modal.comments;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T1data {

    @SerializedName("subreddit_id")
    @Expose
    public String subredditId;
    @SerializedName("likes")
    @Expose
    public Object likes;
    @SerializedName("replies")
    @Expose
    public Example replies;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("parent_id")
    @Expose
    public String parentId;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("downs")
    @Expose
    public Integer downs;
    @SerializedName("subreddit")
    @Expose
    public String subreddit;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("ups")
    @Expose
    public Integer ups;
    @SerializedName("link_id")
    @Expose
    public String link_id;

    public T1data(String subredditId, Object likes, Example replies, String id, String author, String parentId, Integer score, String body, Integer downs, String subreddit, String name, Integer ups, String link_id) {
        this.subredditId = subredditId;
        this.likes = likes;
        this.replies = replies;
        this.id = id;
        this.author = author;
        this.parentId = parentId;
        this.score = score;
        this.body = body;
        this.downs = downs;
        this.subreddit = subreddit;
        this.name = name;
        this.ups = ups;
        this.link_id = link_id;
    }
}
