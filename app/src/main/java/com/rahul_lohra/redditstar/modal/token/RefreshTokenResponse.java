package com.rahul_lohra.redditstar.modal.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rkrde on 28-01-2017.
 */
public class RefreshTokenResponse {
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("device_id")
    @Expose
    public String deviceId;
    @SerializedName("expires_in")
    @Expose
    public Integer expiresIn;
    @SerializedName("scope")
    @Expose
    public String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
