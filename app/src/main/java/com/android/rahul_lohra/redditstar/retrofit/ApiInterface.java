package com.android.rahul_lohra.redditstar.retrofit;

import com.android.rahul_lohra.redditstar.modal.AboutMe;
import com.android.rahul_lohra.redditstar.modal.RefreshTokenResponse;
import com.android.rahul_lohra.redditstar.modal.SubredditResponse;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.modal.reply.ReplyModal;
import com.android.rahul_lohra.redditstar.modal.t5.t5_Response;
import com.android.rahul_lohra.redditstar.modal.token.RefreshToken;
import com.android.rahul_lohra.redditstar.modal.vote.VoteModal;
import com.android.rahul_lohra.redditstar.utility.Constants;

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
    Call<FrontPageResponse> getFrontPage(@QueryMap Map<String, String> options);

    @GET("/r/{subbreddit_name}/.json")
    Call<FrontPageResponse> getSubredditList(@Header(Constants.AUTHORIZATION) String authorization);

    @GET("/r/{subbreddit_name}/.json")
    Call<t5_Response> getAboutSubreddit(@Header(Constants.AUTHORIZATION) String authorization,
                                        @Path(value = "subbreddit_name", encoded = true) String subbreddit_name
    );

    @GET("/r/{subbreddit_name}/comments/{commentsId}.json")
    Call<ResponseBody> getComments(@Path(value = "commentsId", encoded = true) String commentsId,
                                   @Path(value = "subbreddit_name", encoded = true) String subbreddit_name,
                                   @QueryMap Map<String, String> map);

    @POST("/api/vote")
    Call<ResponseBody> postVote(@Header(Constants.AUTHORIZATION) String authorization,
                                @QueryMap Map<String, String> map);

    @POST("/api/comment")
    Call<ResponseBody> postComment(@Header(Constants.AUTHORIZATION) String authorization,
                                   @Body ReplyModal replyModal);
}
