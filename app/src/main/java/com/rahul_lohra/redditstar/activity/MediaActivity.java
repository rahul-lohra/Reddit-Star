package com.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.contract.IMedia;
import com.rahul_lohra.redditstar.factory.DomainFactory;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MediaActivity extends AppCompatActivity {

    @Inject
    @Named("withoutToken")
    Retrofit retrofit;
    ApiInterface apiInterface;
    String domain;
    String TAG  = MediaActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
        ((Initializer) getApplication()).getNetComponent().inject(this);
        if(savedInstanceState==null)
        {
            init();
            Intent intent = getIntent();
            String url = intent.getStringExtra("url");
            domain = intent.getStringExtra("domain");

            getMediaUrl(url);
        }
    }
    private void init(){
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void getMediaUrl(String url){

        Call<ResponseBody> call = DomainFactory.getCall(apiInterface,domain,url);
        if(null == call)
            return;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                {
                    Class domainResponse = DomainFactory.getDomainResponse(domain);
                    if(null ==domainResponse)
                        return;
                    //Do this is back ground thread!!
                    try {
                        Object obj = new Gson().fromJson(response.body().string(),domainResponse);
                        List<String> mediaUrl = DomainFactory.provideUrl((IMedia) obj);
                        for (String str: mediaUrl){
                            System.out.println("Media url:"+str);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"onFail:"+t.getMessage());

            }
        });


    }


}
