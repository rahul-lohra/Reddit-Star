package com.rahul_lohra.redditstar.factory;

import com.rahul_lohra.redditstar.contract.IMedia;
import com.rahul_lohra.redditstar.modal.GifyCat.GifyCatResponse;
import com.rahul_lohra.redditstar.modal.eroshare.EroShareResponse;
import com.rahul_lohra.redditstar.modal.imgur.ImgurResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.util.List;

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
        }else if(domain.equals(DOMAIN_IMGUR_1)||domain.equals(DOMAIN_IMGUR_2)||domain.equals(DOMAIN_IMGUR_3)){
            return ImgurResponse.class;
        }else if(domain.equals(DOMAIN_EROSHARE)){
            return EroShareResponse.class;
        }
        return null;
    }

    public static List<String> provideUrl(IMedia media){
        return media.getMediaUrlList();
    }

    public static String getBaseUrl(String domain,String url)
    {

        String resultUrl = null;
        if(domain.equals(DOMAIN_GFYCAT))
        {
            resultUrl = GFYCAT_BASE_URL.concat(url.split("/")[url.split("/").length-1]);
        }else if (domain.equals(DOMAIN_IMGUR_1)||domain.equals(DOMAIN_IMGUR_2)||domain.equals(DOMAIN_IMGUR_3))
        {
            resultUrl =  getFullUrlFromImgur(url);
        }else if(domain.equals(DOMAIN_EROSHARE)){
            resultUrl = EROSHARE_BASE_URL.concat(url.split("/")[url.split("/").length-1]);
        }
        return resultUrl;
    }

    public static Call<ResponseBody> getCall(ApiInterface apiInterface,String domain,String url)
    {
        if(domain.equals(DOMAIN_GFYCAT)){
            return apiInterface.getDataFromGfyCat(getBaseUrl(domain,url));
        }else if(domain.equals(DOMAIN_IMGUR_1)||domain.equals(DOMAIN_IMGUR_2)||domain.equals(DOMAIN_IMGUR_3)){
            String auth = "Client-ID eaa7dd863d4ffe9";
            return apiInterface.getDataFromImgur(auth,getBaseUrl(domain,url));
        }else if(domain.equals(DOMAIN_EROSHARE)){
            return apiInterface.getDataFromGfyCat(getBaseUrl(domain,url));
        }
        return null;
    }


    public static String DOMAIN_EROSHARE = "eroshare.com";
    public static String DOMAIN_GFYCAT = "gfycat.com";
    public static String DOMAIN_IMGUR_1 = "i.imgur.com";
    public static String DOMAIN_IMGUR_2 = "imgur.com";
    public static String DOMAIN_IMGUR_3 = "m.imgur.com";

    private static String GFYCAT_BASE_URL = "https://api.gfycat.com/v1test/gfycats/";
    private static String EROSHARE_BASE_URL = "https://api.eroshare.com/api/v1/albums/";
    private static String IMGUR_BASE_URL = "https://api.imgur.com/3/";
    private static String IMGUR_URL_SUFFIX_GALLERY = "gallery/";
    private static String IMGUR_URL_SUFFIX_IMAGE = "image/";
    private static String IMGUR_URL_SUFFIX_ALBUM = "album/";

    private static String getFullUrlFromImgur(String url){
        String resultUrl = null;
        String str = url.split("/")[url.split("/").length-1];
        if(url.contains("/a/")){
            resultUrl = IMGUR_BASE_URL.concat(IMGUR_URL_SUFFIX_ALBUM).concat(str.split("\\.")[0]);

        }else if(url.contains("/gallery/")){
            resultUrl = IMGUR_BASE_URL.concat(IMGUR_URL_SUFFIX_GALLERY).concat(str.split("\\.")[0]);

        }else {
            resultUrl = IMGUR_BASE_URL.concat(IMGUR_URL_SUFFIX_IMAGE).concat(str.split("\\.")[0]);
        }
        return resultUrl;
    }


}
