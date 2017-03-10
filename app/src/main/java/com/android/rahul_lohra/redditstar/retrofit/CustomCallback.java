package com.android.rahul_lohra.redditstar.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rkrde on 27-01-2017.
 */

public  class CustomCallback implements Callback {

    @Override
    public void onResponse(Call call, Response response) {

        if(response.code()==401)
        {
            refreshToken();
        }

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    private void refreshToken(){

    }

}
