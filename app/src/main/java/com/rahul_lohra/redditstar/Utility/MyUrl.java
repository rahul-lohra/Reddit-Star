package com.rahul_lohra.redditstar.Utility;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by rkrde on 23-12-2016.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class MyUrl {

    public static String abc = "https://www.reddit.com/api/v1/authorize?client_id=CLIENT_ID&response_type=TYPE&\n" +
            "    state=RANDOM_STRING&redirect_uri=URI&duration=DURATION&scope=SCOPE_STRING";
    public static String LOGIN_AUTHORITY = "https://www.reddit.com";
    public static String CLIENT_ID = "gQx9XYxNhF7hsA";
    public static String REDIRECT_URI = "http://www.example.co.in/my_redirect";
    public static String SCOPE = "identity edit flair";
    public static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";

    public static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope=%s";
    public static final String STATE = "AnyState";

    public static final String BASE_URL = "https://oauth.reddit.com";






    public static String getProperScope(String array[]){
        String scope = "";
        for(int i=0;i<array.length;++i){
            if(i==array.length-1){
                scope = scope+array[i];
            }else {
                scope = scope+array[i]+",";
            }

        }
        return scope;
    }

    public static class Filter_Param_1{

        @Retention(SOURCE)
        @StringDef({HOT,NEW, RISING,CONTROVERSIAL,TOP})
        public @interface FilterType {}
        public static final String HOT = "hot";
        public static final String NEW = "new";
        public static final String RISING = "rising";
        public static final String CONTROVERSIAL = "controversial";
        public static final String TOP = "top";

    }
    public static class Filter_Param_2{

        @Retention(SOURCE)
        @StringDef({HOUR,DAY,WEEK,MONTH,YEAR,ALL_TIME,DEFAULT})
        public @interface FilterMore {}
        public static final String HOUR = "hour";
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String ALL_TIME = "all";
        public static final String DEFAULT = " ";


    }


}
