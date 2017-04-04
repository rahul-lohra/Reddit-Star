package com.rahul_lohra.redditstar.factory;

import com.rahul_lohra.redditstar.contract.IMedia;
import com.rahul_lohra.redditstar.modal.GifyCat.GifyCatResponse;
import com.rahul_lohra.redditstar.modal.imgur.ImgurResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by rkrde on 04-04-2017.
 */

public class DomainFactory {

    private DomainFactory(){}
    public static Class getDomainResponse(String domain){

        if(domain.equals(DOMAIN_GFYCAT))
        {
            return GifyCatResponse.class;
        }else if(domain.equals(DOMAIN_IMGUR)){
            return ImgurResponse.class;
        }
        return null;
    }

    public static String provideUrl(IMedia media){
        return media.getMediaUrl();
    }

    public static String getBaseUrl(String domain,String url)
    {
        String GFYCAT_BASE_URL = "https://api.gfycat.com/v1test/gfycats/";
        String IMGUR_BASE_URL = "https://api.imgur.com/3/image/";

        String resultUrl = null;
        if(domain.equals(DOMAIN_GFYCAT))
        {
            resultUrl = GFYCAT_BASE_URL.concat(url.split("/")[url.split("/").length-1]);
        }else if (domain.equals(DOMAIN_IMGUR))
        {
            String str = url.split("/")[url.split("/").length-1];
            resultUrl = IMGUR_BASE_URL.concat(str.split("\\.")[0]);
        }
        return resultUrl;
    }

    public static Call<ResponseBody> getCall(ApiInterface apiInterface,String domain,String url)
    {
        if(domain.equals(DOMAIN_GFYCAT)){
            return apiInterface.getDataFromGfyCat(getBaseUrl(domain,url));
        }else if(domain.equals(DOMAIN_IMGUR)){
            String auth = "Client-ID eaa7dd863d4ffe9";
            return apiInterface.getDataFromImgur(auth,getBaseUrl(domain,url));
        }
        return null;
    }

    public static String DOMAIN_GFYCAT = "gfycat.com";
    public static String DOMAIN_IMGUR = "i.imgur.com";


}
