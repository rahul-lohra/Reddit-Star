package com.rahul_lohra.redditstar.modal;

import android.graphics.drawable.Drawable;

import lombok.Data;

/**
 * Created by rkrde on 23-01-2017.
 */

public class DrawerItemModal {
    private String name;
    private Drawable drawable;

    public DrawerItemModal(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
