package com.android.rahul_lohra.redditstar.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

/**
 * Created by rkrde on 24-01-2017.
 */

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    private static String TAG = Constants.class.getSimpleName();

    public static String[] getAccessTokenAndRefreshTokenOfActiveUser(Context context){

        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mProjection[] = {UserCredentialsColumn.ACCESS_TOKEN,UserCredentialsColumn.REFRESH_TOKEN};
        String mSelection = UserCredentialsColumn.ACTIVE_STATE +"=?";
        String mSelectionArgs[]={"1"};
        Cursor cursor = context.getContentResolver().query(mUri,mProjection,mSelection,mSelectionArgs,null);
        String accessToken = "";
        String refreshToken = "";
        if(cursor.moveToFirst()){
            do{
                accessToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.ACCESS_TOKEN));
                refreshToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.REFRESH_TOKEN));

            }while (cursor.moveToNext());
        }
        cursor.close();
        String array[] = {accessToken,refreshToken};
        return array;
    }

    public static void updateAccessToken(Context context,String newToken,String refreshToken){

        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACCESS_TOKEN,newToken);
        String mSelection = UserCredentialsColumn.REFRESH_TOKEN +"=?";
        String mSelectionArgs[] ={refreshToken};
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;

        int rowsUpdated = context.getContentResolver().update(mUri,cv,mSelection,mSelectionArgs);
        Log.d(TAG,"rowsUpdated:"+rowsUpdated);

    }

    public static String sampleString = "[\n" +
            "  {\n" +
            "    \"a\":\"1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"b\":\"2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"c\":\"3\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"d\":\"4\"\n" +
            "  }\n" +
            "    \n" +
            "]";

    public static String abc = "[{\"a\":\"1\",\"b\":\"2\"}]";

    public static String comment_2 = "[\n" +
            "  {\n" +
            "    \"kind\": \"Listing\",\n" +
            "    \"data\": {\n" +
            "      \"modhash\": \"\",\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"kind\": \"t3\",\n" +
            "          \"data\": {\n" +
            "            \"contest_mode\": false,\n" +
            "            \"banned_by\": null,\n" +
            "            \"media_embed\": {},\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"selftext_html\": null,\n" +
            "            \"selftext\": \"\",\n" +
            "            \"likes\": null,\n" +
            "            \"suggested_sort\": null,\n" +
            "            \"user_reports\": [],\n" +
            "            \"secure_media\": null,\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"5t02l0\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"secure_media_embed\": {},\n" +
            "            \"clicked\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"tangowhiskey33\",\n" +
            "            \"media\": null,\n" +
            "            \"score\": 25597,\n" +
            "            \"approved_by\": null,\n" +
            "            \"over_18\": false,\n" +
            "            \"domain\": \"i.imgur.com\",\n" +
            "            \"hidden\": false,\n" +
            "            \"preview\": {\n" +
            "              \"images\": [\n" +
            "                {\n" +
            "                  \"source\": {\n" +
            "                    \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?s=618fdf3ddb7354375b373bba2c1b1973\",\n" +
            "                    \"width\": 1080,\n" +
            "                    \"height\": 1080\n" +
            "                  },\n" +
            "                  \"resolutions\": [\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=8332f9009a27737a21a5f9c2e59e798d\",\n" +
            "                      \"width\": 108,\n" +
            "                      \"height\": 108\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=789b409c045c90174c40148176fb4d03\",\n" +
            "                      \"width\": 216,\n" +
            "                      \"height\": 216\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=414b01e76789f3902ffc23742372fa65\",\n" +
            "                      \"width\": 320,\n" +
            "                      \"height\": 320\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;s=4129acfcf72aa58775dc89a1343bf073\",\n" +
            "                      \"width\": 640,\n" +
            "                      \"height\": 640\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=960&amp;s=e0e5f0635b8cfd2ad2f890c9cef2eddf\",\n" +
            "                      \"width\": 960,\n" +
            "                      \"height\": 960\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"url\": \"https://i.redditmedia.com/RyVAQLykabu401fV2RDKulqWywS5oXbmD11ASRig42w.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=1080&amp;s=dfd9cf6b2e025648292a8eccf8fcb397\",\n" +
            "                      \"width\": 1080,\n" +
            "                      \"height\": 1080\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"variants\": {},\n" +
            "                  \"id\": \"_hhP0K7IOYz8vloGBMZrJdM4gbl257mD5rq5Yw2WfcU\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"enabled\": true\n" +
            "            },\n" +
            "            \"num_comments\": 360,\n" +
            "            \"thumbnail\": \"http://a.thumbs.redditmedia.com/YndeVBGmibuPvojk2MxuXuMdYZlRXm-gEXqClDYeut8.jpg\",\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"edited\": false,\n" +
            "            \"link_flair_css_class\": null,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"post_hint\": \"image\",\n" +
            "            \"stickied\": false,\n" +
            "            \"is_self\": false,\n" +
            "            \"hide_score\": false,\n" +
            "            \"spoiler\": false,\n" +
            "            \"permalink\": \"/r/aww/comments/5t02l0/shes_grown_a_lot_in_10_weeks/\",\n" +
            "            \"locked\": false,\n" +
            "            \"name\": \"t3_5t02l0\",\n" +
            "            \"created\": 1486677059,\n" +
            "            \"url\": \"http://i.imgur.com/0oGkZAs.jpg\",\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"quarantine\": false,\n" +
            "            \"title\": \"She's grown a lot in 10 weeks!\",\n" +
            "            \"created_utc\": 1486648259,\n" +
            "            \"link_flair_text\": null,\n" +
            "            \"ups\": 25597,\n" +
            "            \"upvote_ratio\": 0.94,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"visited\": false,\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        }\n" +
            "      ],\n" +
            "      \"after\": null,\n" +
            "      \"before\": null\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"kind\": \"Listing\",\n" +
            "    \"data\": {\n" +
            "      \"modhash\": \"\",\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjega5\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"NapClub\",\n" +
            "                      \"parent_id\": \"t1_ddj3u0x\",\n" +
            "                      \"score\": 175,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"and luna is a good dog, GOOD DOG!\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;and luna is a good dog, GOOD DOG!&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjega5\",\n" +
            "                      \"created\": 1486691439,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486662639,\n" +
            "                      \"ups\": 175,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjgw1h\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"-S-P-E-W-\",\n" +
            "                      \"parent_id\": \"t1_ddj3u0x\",\n" +
            "                      \"score\": 22,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"I hate how cute puppies are. It makes me want another dog until I realize I'm going to have to raise another dog. :\\\\\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;I hate how cute puppies are. It makes me want another dog until I realize I&amp;#39;m going to have to raise another dog. :\\\\&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjgw1h\",\n" +
            "                      \"created\": 1486694161,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486665361,\n" +
            "                      \"ups\": 22,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjg6g5\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"here4dafreefoodnbeer\",\n" +
            "                      \"parent_id\": \"t1_ddj3u0x\",\n" +
            "                      \"score\": 9,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"\\\"Miracle-mutt, we grow bigger brighter more beautiful puppers\\\"\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;&amp;quot;Miracle-mutt, we grow bigger brighter more beautiful puppers&amp;quot;&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjg6g5\",\n" +
            "                      \"created\": 1486693367,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486664567,\n" +
            "                      \"ups\": 9,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddj3u0x\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"stone_age_rebel\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 699,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"She's so beautiful! \",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;She&amp;#39;s so beautiful! &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddj3u0x\",\n" +
            "            \"created\": 1486678538,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486649738,\n" +
            "            \"ups\": 699,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjbsx1\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"lunker13\",\n" +
            "                      \"parent_id\": \"t1_ddjajvy\",\n" +
            "                      \"score\": 352,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"Its only the red-coloured strain that grows that big.  \",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Its only the red-coloured strain that grows that big.  &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjbsx1\",\n" +
            "                      \"created\": 1486688465,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486659665,\n" +
            "                      \"ups\": 352,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjgfhn\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"mechapoitier\",\n" +
            "                      \"parent_id\": \"t1_ddjajvy\",\n" +
            "                      \"score\": 39,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"Reminds me of Louis CK's bit on Clifford the Big Red Dog. \\n\\n\\\"There's 7 books about Narnia, that cover the birth and death of a nation, mice with swords, and a lion who's a god. There are 50 books about Clifford the Big Red Dog, and they all tell the same story: *Look how big this dog is.*\\\" \",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Reminds me of Louis CK&amp;#39;s bit on Clifford the Big Red Dog. &lt;/p&gt;\\n\\n&lt;p&gt;&amp;quot;There&amp;#39;s 7 books about Narnia, that cover the birth and death of a nation, mice with swords, and a lion who&amp;#39;s a god. There are 50 books about Clifford the Big Red Dog, and they all tell the same story: &lt;em&gt;Look how big this dog is.&lt;/em&gt;&amp;quot; &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjgfhn\",\n" +
            "                      \"created\": 1486693649,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486664849,\n" +
            "                      \"ups\": 39,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddje888\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"PietNederwiet\",\n" +
            "                      \"parent_id\": \"t1_ddjajvy\",\n" +
            "                      \"score\": 22,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"The poop would fill a dumpster daily.\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;The poop would fill a dumpster daily.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddje888\",\n" +
            "                      \"created\": 1486691190,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486662390,\n" +
            "                      \"ups\": 22,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjajvy\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"Von_Moistus\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 494,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"Wow. At that rate of growth, by the end of the year she'll be bigger than the house. \",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Wow. At that rate of growth, by the end of the year she&amp;#39;ll be bigger than the house. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjajvy\",\n" +
            "            \"created\": 1486687043,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486658243,\n" +
            "            \"ups\": 494,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddj58mq\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"deeperthansky\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 158,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"They grow up so fast. *sniff*\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;They grow up so fast. &lt;em&gt;sniff&lt;/em&gt;&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddj58mq\",\n" +
            "            \"created\": 1486680453,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486651653,\n" +
            "            \"ups\": 158,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddj4fzo\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"HolyExemplar\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 82,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"That is a gorgeous dog!\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;That is a gorgeous dog!&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddj4fzo\",\n" +
            "            \"created\": 1486679399,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486650599,\n" +
            "            \"ups\": 82,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddje9pp\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"Treees\",\n" +
            "                      \"parent_id\": \"t1_ddj5eug\",\n" +
            "                      \"score\": 42,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"How do we know that the photos aren't reversed?  I think the dog is actually shrinking.\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;How do we know that the photos aren&amp;#39;t reversed?  I think the dog is actually shrinking.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddje9pp\",\n" +
            "                      \"created\": 1486691235,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486662435,\n" +
            "                      \"ups\": 42,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddj5eug\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"mechapoitier\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 56,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"Temporal magic trick: It's *the same dog*. \",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Temporal magic trick: It&amp;#39;s &lt;em&gt;the same dog&lt;/em&gt;. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddj5eug\",\n" +
            "            \"created\": 1486680679,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486651879,\n" +
            "            \"ups\": 56,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjezp4\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"NiggyWiggyWoo\",\n" +
            "                      \"parent_id\": \"t1_ddjd0lr\",\n" +
            "                      \"score\": 27,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"Judging by Luna's breed mix, I'm sure her name is short for Lunatic.\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Judging by Luna&amp;#39;s breed mix, I&amp;#39;m sure her name is short for Lunatic.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjezp4\",\n" +
            "                      \"created\": 1486692042,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486663242,\n" +
            "                      \"ups\": 27,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjhzvl\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"HoldingLimes\",\n" +
            "                      \"parent_id\": \"t1_ddjd0lr\",\n" +
            "                      \"score\": 6,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"If it's male, it's (insert name of Norse god here.)\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;If it&amp;#39;s male, it&amp;#39;s (insert name of Norse god here.)&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjhzvl\",\n" +
            "                      \"created\": 1486695403,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486666603,\n" +
            "                      \"ups\": 6,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjd0lr\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"MaxRayburn\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 44,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"Luna, the official agreed upon name for any female cat or dog on reddit. \",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Luna, the official agreed upon name for any female cat or dog on reddit. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjd0lr\",\n" +
            "            \"created\": 1486689830,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486661030,\n" +
            "            \"ups\": 44,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddj5p4o\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"gamingchicken\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 25,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"The chair also grew substantially (for a chair) in ten weeks.\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;The chair also grew substantially (for a chair) in ten weeks.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddj5p4o\",\n" +
            "            \"created\": 1486681061,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486652261,\n" +
            "            \"ups\": 25,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjdlbt\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"rhiles\",\n" +
            "                      \"parent_id\": \"t1_ddjc1iv\",\n" +
            "                      \"score\": 55,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"It appears to be a \\\"silver lab\\\", which, no matter what anyone tells you, is a Lab/Weim mix, even if the Weim is pretty far back in the pedigree. \\n\\nSilver never appeared in Labradors until suddenly and \\\"randomly\\\" appeared from a kennel that bred both labs and weim. Take from that what you will. \\n\\nhttp://www.woodhavenlabs.com/silverlabs.html\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;It appears to be a &amp;quot;silver lab&amp;quot;, which, no matter what anyone tells you, is a Lab/Weim mix, even if the Weim is pretty far back in the pedigree. &lt;/p&gt;\\n\\n&lt;p&gt;Silver never appeared in Labradors until suddenly and &amp;quot;randomly&amp;quot; appeared from a kennel that bred both labs and weim. Take from that what you will. &lt;/p&gt;\\n\\n&lt;p&gt;&lt;a href=\\\"http://www.woodhavenlabs.com/silverlabs.html\\\"&gt;http://www.woodhavenlabs.com/silverlabs.html&lt;/a&gt;&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjdlbt\",\n" +
            "                      \"created\": 1486690478,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486661678,\n" +
            "                      \"ups\": 55,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjes0q\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"Mulberry_mouse\",\n" +
            "                      \"parent_id\": \"t1_ddjc1iv\",\n" +
            "                      \"score\": 11,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"Please don't ask this. r/aww is a hotspot for breeders marketing their dogs for sale, and this kind of question only encourages it, like the person below with her \\\"silver Lab\\\". The breed registries have no accountability other than shows, and there is no requirement that breeders have a show-qualified animal to breed- so they charge thousands of dollars for puppies that are mixes (like silver Labs or other bs \\\"designer\\\" crosses), unhealthy, or don't conform to breed standards of appearance. Please don't feed these people.\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Please don&amp;#39;t ask this. &lt;a href=\\\"/r/aww\\\"&gt;r/aww&lt;/a&gt; is a hotspot for breeders marketing their dogs for sale, and this kind of question only encourages it, like the person below with her &amp;quot;silver Lab&amp;quot;. The breed registries have no accountability other than shows, and there is no requirement that breeders have a show-qualified animal to breed- so they charge thousands of dollars for puppies that are mixes (like silver Labs or other bs &amp;quot;designer&amp;quot; crosses), unhealthy, or don&amp;#39;t conform to breed standards of appearance. Please don&amp;#39;t feed these people.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjes0q\",\n" +
            "                      \"created\": 1486691807,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486663007,\n" +
            "                      \"ups\": 11,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjdskw\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"hawgdrummer7\",\n" +
            "                      \"parent_id\": \"t1_ddjc1iv\",\n" +
            "                      \"score\": 5,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"I was thinking the same thing. The before picture looks dead-on for a weim, but her eyes sure changed quick, and her coat looks just a tad off. That being said, all the weims I've ever had (4) have very light coats even for a weim, so maybe that's clouding my memory. I know there's a decent range. I used to work for the operators of Weimaraner Rescue in Northwest Arkansas when I was in High School. \",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;I was thinking the same thing. The before picture looks dead-on for a weim, but her eyes sure changed quick, and her coat looks just a tad off. That being said, all the weims I&amp;#39;ve ever had (4) have very light coats even for a weim, so maybe that&amp;#39;s clouding my memory. I know there&amp;#39;s a decent range. I used to work for the operators of Weimaraner Rescue in Northwest Arkansas when I was in High School. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjdskw\",\n" +
            "                      \"created\": 1486690708,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486661908,\n" +
            "                      \"ups\": 5,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjc1iv\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"dondraperscurtains\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 20,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"So pretty!  What breed is she?  Looks kind of like a Lab-Weim mix, but I don't have a great eye for this.\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;So pretty!  What breed is she?  Looks kind of like a Lab-Weim mix, but I don&amp;#39;t have a great eye for this.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjc1iv\",\n" +
            "            \"created\": 1486688736,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486659936,\n" +
            "            \"ups\": 20,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjdrwd\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"andrewsj1\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 10,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"I love how you can tell when big dogs are still puppers\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;I love how you can tell when big dogs are still puppers&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjdrwd\",\n" +
            "            \"created\": 1486690685,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486661885,\n" +
            "            \"ups\": 10,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjfgoi\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"b0nk3rs1337\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 7,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"Well you can tell she's got Weimaraner in her due to the way she sits lol\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Well you can tell she&amp;#39;s got Weimaraner in her due to the way she sits lol&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjfgoi\",\n" +
            "            \"created\": 1486692578,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486663778,\n" +
            "            \"ups\": 7,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjen0v\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"mishko27\",\n" +
            "                      \"parent_id\": \"t1_ddjb55n\",\n" +
            "                      \"score\": 10,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"That's why we go with mutts. Our neighbors got a rotti-lab-some terrier mix who looked like a mid size lab with rotti markings and face. Absolutely adorable, 40-50 pound ball of joy. We have a 30 pounds of muscle in a blue heeler, am staff mix, who is amazing and mid sized. \",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;That&amp;#39;s why we go with mutts. Our neighbors got a rotti-lab-some terrier mix who looked like a mid size lab with rotti markings and face. Absolutely adorable, 40-50 pound ball of joy. We have a 30 pounds of muscle in a blue heeler, am staff mix, who is amazing and mid sized. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjen0v\",\n" +
            "                      \"created\": 1486691649,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486662849,\n" +
            "                      \"ups\": 10,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjb55n\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"Demsatanworshiper\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 32,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"As a kid whose dad bred German Shepards, I never really had a whole \\\"I want a puppy\\\" phase. I watched all of them grow into big sized dogs really quick. My boy who is a lab-rotti mix I got when he was like four months old and he turned into a hundred pound beast of muscle and fur in under six months. My wife talked about getting a Great Dane puppy because they're so cute and I reminded her taht the whole puppy size only lasts a handful of weeks and then they are small horses. \\n\\nMy boy is a touch too big for my tastes, and I want more like a mid-sized dog like a Pit Bull (won't get the breed in all likelihood till my kids are in their teens). In all likelihood we're getting a cat, then a dog that tolerates the cat and isn't aggressive towards the cat.\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;As a kid whose dad bred German Shepards, I never really had a whole &amp;quot;I want a puppy&amp;quot; phase. I watched all of them grow into big sized dogs really quick. My boy who is a lab-rotti mix I got when he was like four months old and he turned into a hundred pound beast of muscle and fur in under six months. My wife talked about getting a Great Dane puppy because they&amp;#39;re so cute and I reminded her taht the whole puppy size only lasts a handful of weeks and then they are small horses. &lt;/p&gt;\\n\\n&lt;p&gt;My boy is a touch too big for my tastes, and I want more like a mid-sized dog like a Pit Bull (won&amp;#39;t get the breed in all likelihood till my kids are in their teens). In all likelihood we&amp;#39;re getting a cat, then a dog that tolerates the cat and isn&amp;#39;t aggressive towards the cat.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjb55n\",\n" +
            "            \"created\": 1486687718,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486658918,\n" +
            "            \"ups\": 32,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddjeu61\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"DinksMalone\",\n" +
            "                      \"parent_id\": \"t1_ddjdjj0\",\n" +
            "                      \"score\": 7,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"It's called panosteitis and does happen. Great Danes for instance when they have too much protein in their diet, usually above 24%, can grow too fast and is painful. \\n\\nSource: had a limping pupper.\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;It&amp;#39;s called panosteitis and does happen. Great Danes for instance when they have too much protein in their diet, usually above 24%, can grow too fast and is painful. &lt;/p&gt;\\n\\n&lt;p&gt;Source: had a limping pupper.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddjeu61\",\n" +
            "                      \"created\": 1486691870,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486663070,\n" +
            "                      \"ups\": 7,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2qh1o\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_5t02l0\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": \"\",\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"ddje90v\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": false,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"Motoflou\",\n" +
            "                      \"parent_id\": \"t1_ddjdjj0\",\n" +
            "                      \"score\": 5,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"Right?  That was my first thought.  To grow at such a rapid rate must be painful.  PAINFUL BUT CUTE\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Right?  That was my first thought.  To grow at such a rapid rate must be painful.  PAINFUL BUT CUTE&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"aww\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_ddje90v\",\n" +
            "                      \"created\": 1486691214,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1486662414,\n" +
            "                      \"ups\": 5,\n" +
            "                      \"mod_reports\": [],\n" +
            "                      \"num_reports\": null,\n" +
            "                      \"distinguished\": null\n" +
            "                    }\n" +
            "                  }\n" +
            "                ],\n" +
            "                \"after\": null,\n" +
            "                \"before\": null\n" +
            "              }\n" +
            "            },\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjdjj0\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"24grant24\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 6,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"I wonder if dogs get growing pains\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;I wonder if dogs get growing pains&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjdjj0\",\n" +
            "            \"created\": 1486690425,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486661625,\n" +
            "            \"ups\": 6,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddje9xb\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"Vahlir\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 6,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"should be massively upvoted for people who get a dog because they fall in love with the idea of the puppy phase, and the reality of how short it actually is. \",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;should be massively upvoted for people who get a dog because they fall in love with the idea of the puppy phase, and the reality of how short it actually is. &lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddje9xb\",\n" +
            "            \"created\": 1486691242,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486662442,\n" +
            "            \"ups\": 6,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjdg8k\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"loJohn\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 5,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"Don't puppy tempt me! My Cats would slit my throat in my sleep tonight if they knew i was looking at puppy pictures.\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Don&amp;#39;t puppy tempt me! My Cats would slit my throat in my sleep tonight if they knew i was looking at puppy pictures.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjdg8k\",\n" +
            "            \"created\": 1486690320,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486661520,\n" +
            "            \"ups\": 5,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"kind\": \"t1\",\n" +
            "          \"data\": {\n" +
            "            \"subreddit_id\": \"t5_2qh1o\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_5t02l0\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": \"\",\n" +
            "            \"user_reports\": [],\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"ddjebhl\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"thiefmann\",\n" +
            "            \"parent_id\": \"t3_5t02l0\",\n" +
            "            \"score\": 4,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"You're gonna need a bigger chair.\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;You&amp;#39;re gonna need a bigger chair.&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"aww\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_ddjebhl\",\n" +
            "            \"created\": 1486691289,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1486662489,\n" +
            "            \"ups\": 4,\n" +
            "            \"mod_reports\": [],\n" +
            "            \"num_reports\": null,\n" +
            "            \"distinguished\": null\n" +
            "          }\n" +
            "        }\n" +
            "      ],\n" +
            "      \"after\": null,\n" +
            "      \"before\": null\n" +
            "    }\n" +
            "  }\n" +
            "]";

}
