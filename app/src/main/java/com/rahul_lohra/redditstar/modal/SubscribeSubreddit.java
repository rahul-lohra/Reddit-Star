package com.rahul_lohra.redditstar.modal;

/**
 * Created by rkrde on 25-02-2017.
 */

public class SubscribeSubreddit {
    private String sr;
    private String action;
    private boolean skip_initial_defaults;

    public SubscribeSubreddit(String sr, String action, boolean skip_initial_defaults) {
        this.sr = sr;
        this.action = action;
        this.skip_initial_defaults = skip_initial_defaults;
    }
}
