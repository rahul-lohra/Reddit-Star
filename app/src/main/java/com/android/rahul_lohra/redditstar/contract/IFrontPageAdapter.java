package com.android.rahul_lohra.redditstar.contract;

import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;

/**
 * Created by rkrde on 28-02-2017.
 */

public interface IFrontPageAdapter {
    void sendData(DetailPostModal modal, ImageView imageView,String id);
    void pleaseLogin();

}
