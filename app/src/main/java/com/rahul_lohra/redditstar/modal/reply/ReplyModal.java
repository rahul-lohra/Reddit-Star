package com.rahul_lohra.redditstar.modal.reply;

/**
 * Created by rkrde on 13-02-2017.
 */

public class ReplyModal {
    String api_type;
    String text;
    String thing_id;

    public ReplyModal(String api_type, String text, String thing_id) {
        this.api_type = api_type;
        this.text = text;
        this.thing_id = thing_id;
    }
}
