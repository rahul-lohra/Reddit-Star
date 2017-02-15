package com.android.rahul_lohra.redditstar.modal.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 15-02-2017.
 */

public class RefreshToken {
    @SerializedName("grant_type")
    @Expose
    String grant_type;
    @SerializedName("refresh_token")
    @Expose
    String refresh_token;


    public RefreshToken(String grant_type, String refresh_token) {
        this.grant_type = grant_type;
        this.refresh_token = refresh_token;
    }
}
