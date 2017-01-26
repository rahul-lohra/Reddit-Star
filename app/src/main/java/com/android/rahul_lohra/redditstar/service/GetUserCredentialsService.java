package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.AboutMe;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetUserCredentialsService extends IntentService {

    @Inject
    @Named("fun")
    Retrofit retrofit;

    ApiInterface apiInterface;
    private static final String TAG = GetUserCredentialsService.class.getSimpleName();
    public GetUserCredentialsService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer)getApplication()).getNetComponent().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String token = intent.getStringExtra("accessToken");
        apiInterface.getAboutMe("bearer "+token)
        .enqueue(new Callback<AboutMe>() {
            @Override
            public void onResponse(Call<AboutMe> call, Response<AboutMe> response) {
                if(response.code()== 200){
                    saveCredentials(response.body(),token);
                    Log.d(TAG,"success");
                }else {
                    Log.d(TAG,"fail:"+response.code());
                }
            }
            @Override
            public void onFailure(Call<AboutMe> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"getting name failed",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"getting name failed:"+t.getMessage());
            }
        });

    }

    void saveCredentials(AboutMe aboutMe,String token){

        String name = aboutMe.getName();
        String redditId = aboutMe.getId();
        boolean over_18 = aboutMe.getOver18();

        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.NAME,name);
        cv.put(UserCredentialsColumn.REDDIT_ID,redditId);
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mWhere = UserCredentialsColumn.ACCESS_TOKEN +"=?";
        String mSelectionArgs[] = {token};
        getContentResolver().update(mUri,cv,mWhere,mSelectionArgs);
        Toast.makeText(getApplicationContext(),"Credentials updated",Toast.LENGTH_SHORT).show();

    }
}
