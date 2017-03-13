package com.rahul_lohra.redditstar.modal;

import android.graphics.drawable.Drawable;

import lombok.Data;

/**
 * Created by rkrde on 23-01-2017.
 */
@Data
public class DrawerItemModal {
    private String name;
    private Drawable drawable;

    public DrawerItemModal(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }
}
