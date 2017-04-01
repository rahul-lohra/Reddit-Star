package com.rahul_lohra.redditstar.dataModel;

/**
 * Created by rkrde on 01-04-2017.
 */
/*
Used to pass Comments data through java class
 */

public class Comments {
    String sqlId;
    String cId;
    String body;
    String author;
    int upVote;
    int thingId;
    long createdUtc;
    String time;
    int isExpanded;
    int hiddenChildCounts;
    String hiddenBy;
    int depth;

    public Comments(String sqlId,String cId, String body, String author, int upVote, int thingId, long createdUtc, String time, int isExpanded, int hiddenChildCounts, String hiddenBy, int depth) {
        this.sqlId = sqlId;
        this.cId = cId;
        this.body = body;
        this.author = author;
        this.upVote = upVote;
        this.thingId = thingId;
        this.createdUtc = createdUtc;
        this.time = time;
        this.isExpanded = isExpanded;
        this.hiddenChildCounts = hiddenChildCounts;
        this.hiddenBy = hiddenBy;
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public String getSqlId() {
        return sqlId;
    }

    public String getcId() {
        return cId;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public int getUpVote() {
        return upVote;
    }

    public int getThingId() {
        return thingId;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public String getTime() {
        return time;
    }

    public int getIsExpanded() {
        return isExpanded;
    }

    public int getHiddenChildCounts() {
        return hiddenChildCounts;
    }

    public String getHiddenBy() {
        return hiddenBy;
    }
}
