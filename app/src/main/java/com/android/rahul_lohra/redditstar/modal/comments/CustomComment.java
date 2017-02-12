package com.android.rahul_lohra.redditstar.modal.comments;

import lombok.Data;

/**
 * Created by rkrde on 11-02-2017.
 */

@Data
public class CustomComment {
    private int depth;
    private Child child;

    public CustomComment(int depth, Child child) {
        this.depth = depth;
        this.child = child;
    }
}
