package com.android.rahul_lohra.redditstar.utility;

/**
 * Created by rkrde on 23-12-2016.
 */

public class MyUrl {

    public static String abc = "https://www.reddit.com/api/v1/authorize?client_id=CLIENT_ID&response_type=TYPE&\n" +
            "    state=RANDOM_STRING&redirect_uri=URI&duration=DURATION&scope=SCOPE_STRING";
    public static String LOGIN_AUTHORITY = "www.reddit.com";
    public static String CLIENT_ID = "gQx9XYxNhF7hsA";
    public static String REDIRECT_URI = "http://www.example.co.in/my_redirect";
    public static String SCOPE = "identity edit flair";
    public static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";

    public static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope="+SCOPE;
    public static final String STATE = "AnyState";

}
