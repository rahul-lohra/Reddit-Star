package com.rahul_lohra.redditstar.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.rahul_lohra.redditstar.modal.search.T5_SearchResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 13-04-2017.
 */

public class ApiCalls {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    @Inject
    Context context;
    @Inject
    SharedPreferences sp;

    ApiInterface apiInterface;


    public ApiCalls(Context context) {
        ((Initializer) context.getApplicationContext()).getNetComponent().inject(this);

    }

    public Observable<FrontPageResponse> searchLinksRx(String subredditName, String mAfter) {
        apiInterface = MyUrl.getApiInterface(context,retrofitWithToken,retrofitWithoutToken);
        String token = MyUrl.getToken(context);
        Map<String, Object> map = new HashMap<>();
        map.put("after", mAfter);
        map.put("limit", "7");
        map.put("q", subredditName);
        map.put(SpConstants.OVER_18,sp.getBoolean(SpConstants.OVER_18,false));
        return  apiInterface.searchLinksRx(token,map);
    }

    public Observable<T5_SearchResponse> searchSubredditsRx(String subredditName, String mAfter) {
        apiInterface = MyUrl.getApiInterface(context,retrofitWithToken,retrofitWithoutToken);
        String token = MyUrl.getToken(context);
        Map<String, Object> map = new HashMap<>();
        map.put("after", mAfter);
        map.put("limit", "7");
        map.put("q", subredditName);
        map.put(SpConstants.OVER_18,sp.getBoolean(SpConstants.OVER_18,false));
        return  apiInterface.searchSubredditsRx(token,map);
    }

    public Call<FrontPageResponse> getCallFrontPageResponse(String after,String filterParam_1,String filterParam_2){
        apiInterface = MyUrl.getApiInterface(context,retrofitWithToken,retrofitWithoutToken);
        if(null == after){
            return null;
        }
        String token = MyUrl.getToken(context);
        Map<String,String> map = new HashMap<>();
        map.put("limit","20");
        map.put("after",after);
        map.put("t",filterParam_2);
        map.put(SpConstants.OVER_18,String.valueOf(sp.getBoolean(SpConstants.OVER_18,false)));
        return apiInterface.getFrontPage(token,filterParam_1,map);

    }
}
