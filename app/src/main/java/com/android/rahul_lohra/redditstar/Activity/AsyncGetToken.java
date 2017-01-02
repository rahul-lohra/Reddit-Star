package com.android.rahul_lohra.redditstar.Activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.rahul_lohra.redditstar.Activity.Utility.MyUrl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rkrde on 24-12-2016.
 */

public class AsyncGetToken extends AsyncTask<String,Void,Void>{

    public static String PARSING_URL = "";
    String TAG = AsyncGetToken.class.getSimpleName();
    public AsyncGetToken(String url)
    {

    }
    @Override
    protected Void doInBackground(String... params) {
        makeRequest();
        return null;
    }

    public void makeRequest()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(MyUrl.LOGIN_AUTHORITY)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("access_token")
//                .appendQueryParameter("client_id", MyUrl.CLIENT_ID)
//                .appendQueryParameter("response_type", "code")
//                .appendQueryParameter("state","AnyState")
//                .appendQueryParameter("redirect_uri", MyUrl.REDIRECT_URI)
//                .appendQueryParameter("duration","permanent")
//                .appendQueryParameter("scope",MyUrl.SCOPE)
                 ;

        String myUrl = builder.build().toString();
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            String key_val=getPostPatams();
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(key_val);
            wr.flush();
            wr.close();


            Log.d(TAG,"RequestCode:"+connection.getResponseCode());
            BufferedReader in;
            if(connection.getResponseCode()!=200)
            {
                Log.d("OnBackground1234", "ErrorStream=" + connection.getErrorStream().toString());

                in= new BufferedReader(
                        new InputStreamReader(connection.getErrorStream()));
            }else{
                in= new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String finalJson = response.toString();
            Log.d(TAG,"finalJson:"+finalJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPostPatams()
    {
        String str = "";
        try {
            URL  url  = new URL(PARSING_URL);
            //name=abc&lastname=123
            String query = url.getQuery();

            String key_1 = query.substring(0,query.indexOf("="));
            String val_1 = query.substring(key_1.length(),query.indexOf("&"));
            String key_2 = query.substring(query.indexOf("&")+1,query.indexOf("=",query.indexOf("&")));
            String val_2 = query.substring(query.lastIndexOf("=")+1,query.length());
            Map<String,String> map = new HashMap<>();
            map.put(key_1,val_1);
            map.put(key_2,val_2);

            str = "grant_type=authorization_code" +
                    "&code="+map.get("code")+
                    "&redirect_uri="+MyUrl.REDIRECT_URI;
//            query = "grant_type=authorization_code&code=CODE&redirect_uri=URI+              //////////////======> Change params
//                    "&code=" + params[1]
//                    +"&requestId="+" "
//                    +"&pushToken="+params[3]
//                    +"&platform="+params[4]
            ;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return str;
    }
}
