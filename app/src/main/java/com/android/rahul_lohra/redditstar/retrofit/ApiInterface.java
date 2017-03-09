package com.android.rahul_lohra.redditstar.retrofit;

import com.android.rahul_lohra.redditstar.modal.AboutMe;
import com.android.rahul_lohra.redditstar.modal.RefreshTokenResponse;
import com.android.rahul_lohra.redditstar.modal.SubredditResponse;
import com.android.rahul_lohra.redditstar.modal.SubscribeSubreddit;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.modal.reply.ReplyModal;
import com.android.rahul_lohra.redditstar.modal.reply.ReplyResponse;
import com.android.rahul_lohra.redditstar.modal.search.T3_SearchResponse;
import com.android.rahul_lohra.redditstar.modal.search.T5_SearchResponse;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.t5_Response;
import com.android.rahul_lohra.redditstar.utility.Constants;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by rkrde on 24-01-2017.
 */

public interface ApiInterface {

    @GET("/subreddits/mine/subscriber")
    Call<SubredditResponse> getMySubscribedSubreddits(@Header(Constants.AUTHORIZATION) String authorization,
                                                      @QueryMap Map<String, String> options);

    @GET("/api/v1/me")
    Call<AboutMe> getAboutMe(@Header(Constants.AUTHORIZATION) String authorization);

    @FormUrlEncoded
    @POST("/api/v1/access_token")
    Call<RefreshTokenResponse> refreshToken(@Header(Constants.AUTHORIZATION) String authorization,
                                    @Field("grant_type") String grant_type,
                                    @Field("refresh_token") String refresh_token);

    @GET("/.json")
    Call<FrontPageResponse> getFrontPage(@Header(Constants.AUTHORIZATION) String authorization,@QueryMap Map<String, String> options);

    @GET("/r/{subbreddit_name}/.json")
    Call<FrontPageResponse> getSubredditList(@Header(Constants.AUTHORIZATION) String authorization,
                                             @Path(value = "subbreddit_name", encoded = true) String subbreddit_name,
                                             @QueryMap Map<String, String> map);

    @GET("/r/{subbreddit_name}/about/.json")
    Call<t5_Response> getAboutSubreddit(@Header(Constants.AUTHORIZATION) String authorization,
                                        @Path(value = "subbreddit_name", encoded = true) String subbreddit_name
    );

    @GET("/r/{subbreddit_name}/comments/{postId}.json")
    Call<ResponseBody> getComments(@Path(value = "postId", encoded = true) String postId,
                                   @Path(value = "subbreddit_name", encoded = true) String subbreddit_name,
                                   @QueryMap Map<String, String> map);

    @POST("/api/vote")
    Call<ResponseBody> postVote(@Header(Constants.AUTHORIZATION) String authorization,
                                @QueryMap Map<String, String> map);

    @GET("/subreddits/search.json")
    Call<T5_SearchResponse> searchSubreddits(@Header(Constants.AUTHORIZATION) String authorization,
                                             @QueryMap Map<String, Object> map);

    @GET("search.json")
    Call<FrontPageResponse> searchLinks(@Header(Constants.AUTHORIZATION) String authorization,
                                        @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/api/comment")
    Call<ReplyResponse> postComment(@Header(Constants.AUTHORIZATION) String authorization,
                                    @Field("api_type")String api_type,
                                    @Field("text")String text,
                                    @Field("thing_id")String thing_id);

    @FormUrlEncoded
    @POST("/api/subscribe")
    Call<ResponseBody> subscribeSubreddit_new(@Header(Constants.AUTHORIZATION) String authorization,
                                              @Field("action") String action,
                                              @Field("skip_initial_defaults") boolean skip_initial_defaults,
                                              @Field("sr") String fullName
                                              );
//    https://www.reddit.com/r/all/controversial/?sort=controversial&t=month
    @GET("/r/{subbreddit_name}/{sort}/.json")
    Call<FrontPageResponse> getSubredditPosts(@Header(Constants.AUTHORIZATION) String authorization,
                                              @Path(value = "subbreddit_name", encoded = true) String subreddit_name,
                                              @Path(value = "sort", encoded = true) String sort,
                                              @QueryMap Map<String, String> options);


}
