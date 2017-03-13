package com.rahul_lohra.redditstar.modal;

/**
 * Created by rkrde on 25-02-2017.
 */

public class FavoritesModal {
    private String displayName;
    private String fullName;
    private String subredditId;

    public FavoritesModal(String displayName, String fullName, String subredditId) {
        this.displayName = displayName;
        this.fullName = fullName;
        this.subredditId = subredditId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }
}
