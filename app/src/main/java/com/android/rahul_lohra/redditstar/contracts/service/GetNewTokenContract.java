package com.android.rahul_lohra.redditstar.contracts.service;

/**
 * Created by rkrde on 25-01-2017.
 */

public interface GetNewTokenContract {
    boolean getToken(String code);
    void saveInDatabase(String accessToken, String refreshToken,int activeState);
    void disableActiveState(String accessToken,int activeState);
    void showLoginSuccess();
    void showLoginFailure();
    void fetchUsersCredentials(String accessToken);
}
