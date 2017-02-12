package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.HomeFragment;
import com.android.rahul_lohra.redditstar.modal.comments.DummyAdapter;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {


    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        if (null == savedInstanceState) {

            showDetailSubredditFragment(
                    intent.getStringExtra("id"),
                    intent.getStringExtra("subreddit")
            );

        }
    }

    void showDetailSubredditFragment(String id, String subreddit) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, DetailSubredditFragment.newInstance(id, subreddit), DetailSubredditFragment.class.getSimpleName())
                .commit();
    }

    public static String string = "[\n" +
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
            "            \"subreddit\": \"about\",\n" +
            "            \"selftext_html\": null,\n" +
            "            \"selftext\": \"\",\n" +
            "            \"likes\": null,\n" +
            "            \"suggested_sort\": null,\n" +
            "            \"user_reports\": [],\n" +
            "            \"secure_media\": null,\n" +
            "            \"saved\": false,\n" +
            "            \"id\": \"39y5zw\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"secure_media_embed\": {},\n" +
            "            \"clicked\": false,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"careless\",\n" +
            "            \"media\": null,\n" +
            "            \"score\": 7,\n" +
            "            \"approved_by\": null,\n" +
            "            \"over_18\": false,\n" +
            "            \"domain\": \"i.imgur.com\",\n" +
            "            \"hidden\": false,\n" +
            "            \"num_comments\": 4,\n" +
            "            \"thumbnail\": \"\",\n" +
            "            \"subreddit_id\": \"t5_2rhde\",\n" +
            "            \"edited\": false,\n" +
            "            \"link_flair_css_class\": null,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"archived\": true,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"stickied\": false,\n" +
            "            \"is_self\": false,\n" +
            "            \"hide_score\": false,\n" +
            "            \"spoiler\": false,\n" +
            "            \"permalink\": \"/r/about/comments/39y5zw/rseattle_global_reddit_meetup_day_2015_group_photo/\",\n" +
            "            \"locked\": false,\n" +
            "            \"name\": \"t3_39y5zw\",\n" +
            "            \"created\": 1434424893,\n" +
            "            \"url\": \"https://i.imgur.com/s6a3GFm.jpg\",\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"quarantine\": false,\n" +
            "            \"title\": \"/r/Seattle Global reddit Meetup Day 2015 Group Photo\",\n" +
            "            \"created_utc\": 1434396093,\n" +
            "            \"link_flair_text\": null,\n" +
            "            \"ups\": 7,\n" +
            "            \"upvote_ratio\": 0.74,\n" +
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
            "            \"subreddit_id\": \"t5_2rhde\",\n" +
            "            \"banned_by\": null,\n" +
            "            \"removal_reason\": null,\n" +
            "            \"link_id\": \"t3_39y5zw\",\n" +
            "            \"likes\": null,\n" +
            "            \"replies\": {\n" +
            "              \"kind\": \"Listing\",\n" +
            "              \"data\": {\n" +
            "                \"modhash\": \"\",\n" +
            "                \"children\": [\n" +
            "                  {\n" +
            "                    \"kind\": \"t1\",\n" +
            "                    \"data\": {\n" +
            "                      \"subreddit_id\": \"t5_2rhde\",\n" +
            "                      \"banned_by\": null,\n" +
            "                      \"removal_reason\": null,\n" +
            "                      \"link_id\": \"t3_39y5zw\",\n" +
            "                      \"likes\": null,\n" +
            "                      \"replies\": {\n" +
            "                        \"kind\": \"Listing\",\n" +
            "                        \"data\": {\n" +
            "                          \"modhash\": \"\",\n" +
            "                          \"children\": [\n" +
            "                            {\n" +
            "                              \"kind\": \"t1\",\n" +
            "                              \"data\": {\n" +
            "                                \"subreddit_id\": \"t5_2rhde\",\n" +
            "                                \"banned_by\": null,\n" +
            "                                \"removal_reason\": null,\n" +
            "                                \"link_id\": \"t3_39y5zw\",\n" +
            "                                \"likes\": null,\n" +
            "                                \"replies\": {\n" +
            "                                  \"kind\": \"Listing\",\n" +
            "                                  \"data\": {\n" +
            "                                    \"modhash\": \"\",\n" +
            "                                    \"children\": [\n" +
            "                                      {\n" +
            "                                        \"kind\": \"t1\",\n" +
            "                                        \"data\": {\n" +
            "                                          \"subreddit_id\": \"t5_2rhde\",\n" +
            "                                          \"banned_by\": null,\n" +
            "                                          \"removal_reason\": null,\n" +
            "                                          \"link_id\": \"t3_39y5zw\",\n" +
            "                                          \"likes\": null,\n" +
            "                                          \"replies\": \"\",\n" +
            "                                          \"user_reports\": [],\n" +
            "                                          \"saved\": false,\n" +
            "                                          \"id\": \"cs7wk71\",\n" +
            "                                          \"gilded\": 0,\n" +
            "                                          \"archived\": true,\n" +
            "                                          \"report_reasons\": null,\n" +
            "                                          \"author\": \"isiramteal\",\n" +
            "                                          \"parent_id\": \"t1_cs7wgt8\",\n" +
            "                                          \"score\": 3,\n" +
            "                                          \"approved_by\": null,\n" +
            "                                          \"controversiality\": 0,\n" +
            "                                          \"body\": \"When did I ever do that?\",\n" +
            "                                          \"edited\": false,\n" +
            "                                          \"author_flair_css_class\": null,\n" +
            "                                          \"downs\": 0,\n" +
            "                                          \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;When did I ever do that?&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                                          \"stickied\": false,\n" +
            "                                          \"subreddit\": \"about\",\n" +
            "                                          \"score_hidden\": false,\n" +
            "                                          \"name\": \"t1_cs7wk71\",\n" +
            "                                          \"created\": 1434448815,\n" +
            "                                          \"author_flair_text\": null,\n" +
            "                                          \"created_utc\": 1434420015,\n" +
            "                                          \"ups\": 3,\n" +
            "                                          \"mod_reports\": [],\n" +
            "                                          \"num_reports\": null,\n" +
            "                                          \"distinguished\": null\n" +
            "                                        }\n" +
            "                                      }\n" +
            "                                    ],\n" +
            "                                    \"after\": null,\n" +
            "                                    \"before\": null\n" +
            "                                  }\n" +
            "                                },\n" +
            "                                \"user_reports\": [],\n" +
            "                                \"saved\": false,\n" +
            "                                \"id\": \"cs7wgt8\",\n" +
            "                                \"gilded\": 0,\n" +
            "                                \"archived\": true,\n" +
            "                                \"report_reasons\": null,\n" +
            "                                \"author\": \"careless\",\n" +
            "                                \"parent_id\": \"t1_cs7vget\",\n" +
            "                                \"score\": -2,\n" +
            "                                \"approved_by\": null,\n" +
            "                                \"controversiality\": 0,\n" +
            "                                \"body\": \"Hey, why not put a big arrow pointing at my face and write my username next to it?\\n\\nBecause that worked out really well for you the last time you did it, didn't it?\",\n" +
            "                                \"edited\": false,\n" +
            "                                \"author_flair_css_class\": null,\n" +
            "                                \"downs\": 0,\n" +
            "                                \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Hey, why not put a big arrow pointing at my face and write my username next to it?&lt;/p&gt;\\n\\n&lt;p&gt;Because that worked out really well for you the last time you did it, didn&amp;#39;t it?&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                                \"stickied\": false,\n" +
            "                                \"subreddit\": \"about\",\n" +
            "                                \"score_hidden\": false,\n" +
            "                                \"name\": \"t1_cs7wgt8\",\n" +
            "                                \"created\": 1434448656,\n" +
            "                                \"author_flair_text\": null,\n" +
            "                                \"created_utc\": 1434419856,\n" +
            "                                \"ups\": -2,\n" +
            "                                \"mod_reports\": [],\n" +
            "                                \"num_reports\": null,\n" +
            "                                \"distinguished\": null\n" +
            "                              }\n" +
            "                            }\n" +
            "                          ],\n" +
            "                          \"after\": null,\n" +
            "                          \"before\": null\n" +
            "                        }\n" +
            "                      },\n" +
            "                      \"user_reports\": [],\n" +
            "                      \"saved\": false,\n" +
            "                      \"id\": \"cs7vget\",\n" +
            "                      \"gilded\": 0,\n" +
            "                      \"archived\": true,\n" +
            "                      \"report_reasons\": null,\n" +
            "                      \"author\": \"[deleted]\",\n" +
            "                      \"parent_id\": \"t1_cs7i84u\",\n" +
            "                      \"score\": 3,\n" +
            "                      \"approved_by\": null,\n" +
            "                      \"controversiality\": 0,\n" +
            "                      \"body\": \"[removed]\",\n" +
            "                      \"edited\": false,\n" +
            "                      \"author_flair_css_class\": null,\n" +
            "                      \"downs\": 0,\n" +
            "                      \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;[removed]&lt;/p&gt;\\n&lt;/div&gt;\",\n" +
            "                      \"stickied\": false,\n" +
            "                      \"subreddit\": \"about\",\n" +
            "                      \"score_hidden\": false,\n" +
            "                      \"name\": \"t1_cs7vget\",\n" +
            "                      \"created\": 1434446975,\n" +
            "                      \"author_flair_text\": null,\n" +
            "                      \"created_utc\": 1434418175,\n" +
            "                      \"ups\": 3,\n" +
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
            "            \"id\": \"cs7i84u\",\n" +
            "            \"gilded\": 0,\n" +
            "            \"archived\": true,\n" +
            "            \"report_reasons\": null,\n" +
            "            \"author\": \"careless\",\n" +
            "            \"parent_id\": \"t3_39y5zw\",\n" +
            "            \"score\": -1,\n" +
            "            \"approved_by\": null,\n" +
            "            \"controversiality\": 0,\n" +
            "            \"body\": \"The photographer, /u/advtorrin, [added a few more pics in this comment on /r/Seattle](https://www.reddit.com/r/Seattle/comments/39wy6d/this_is_reddit_seattle_2015/cs76f0m):\\n\\n&gt; Glade to see everyone for another glorious annual meetup, I'm glad we have such an awesome community!  Here is the [photo of silliness](http://imgur.com/HnWcvuW) and here is a shitty photoshop of the [Seattle skyline version](http://imgur.com/nmamjgb)\\n\",\n" +
            "            \"edited\": false,\n" +
            "            \"author_flair_css_class\": null,\n" +
            "            \"downs\": 0,\n" +
            "            \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;The photographer, &lt;a href=\\\"/u/advtorrin\\\"&gt;/u/advtorrin&lt;/a&gt;, &lt;a href=\\\"https://www.reddit.com/r/Seattle/comments/39wy6d/this_is_reddit_seattle_2015/cs76f0m\\\"&gt;added a few more pics in this comment on /r/Seattle&lt;/a&gt;:&lt;/p&gt;\\n\\n&lt;blockquote&gt;\\n&lt;p&gt;Glade to see everyone for another glorious annual meetup, I&amp;#39;m glad we have such an awesome community!  Here is the &lt;a href=\\\"http://imgur.com/HnWcvuW\\\"&gt;photo of silliness&lt;/a&gt; and here is a shitty photoshop of the &lt;a href=\\\"http://imgur.com/nmamjgb\\\"&gt;Seattle skyline version&lt;/a&gt;&lt;/p&gt;\\n&lt;/blockquote&gt;\\n&lt;/div&gt;\",\n" +
            "            \"stickied\": false,\n" +
            "            \"subreddit\": \"about\",\n" +
            "            \"score_hidden\": false,\n" +
            "            \"name\": \"t1_cs7i84u\",\n" +
            "            \"created\": 1434425133,\n" +
            "            \"author_flair_text\": null,\n" +
            "            \"created_utc\": 1434396333,\n" +
            "            \"ups\": -1,\n" +
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
