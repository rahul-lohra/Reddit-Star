package com.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;

import com.rahul_lohra.redditstar.presenter.service.GetNewTokenPresenter;

public class GetNewTokenService extends IntentService {

    private static final String TAG = GetNewTokenService.class.getSimpleName();
    public GetNewTokenService() {
        super(TAG);
    }
    GetNewTokenPresenter presenter ;

    @Override
    public void onCreate() {
        super.onCreate();
        if(null == presenter)
            presenter = new GetNewTokenPresenter(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /*
        1.Get the token and save in database!!
        2.Fetch User's credentials
         */
        String code = intent.getStringExtra("code");
        boolean gotTheToken = presenter.getToken(code);

        if(gotTheToken){
            presenter.showLoginSuccess();
        }else {
            presenter.showLoginFailure();
        }
    }
}
