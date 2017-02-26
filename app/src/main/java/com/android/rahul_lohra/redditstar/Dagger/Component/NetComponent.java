package com.android.rahul_lohra.redditstar.dagger.Component;

import com.android.rahul_lohra.redditstar.activity.SearchActivity;
import com.android.rahul_lohra.redditstar.dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.dagger.Module.NetModule;
import com.android.rahul_lohra.redditstar.activity.MainActivity;
import com.android.rahul_lohra.redditstar.dagger.extras.TokenAuthenticator;
import com.android.rahul_lohra.redditstar.fragments.HomeFragment;
import com.android.rahul_lohra.redditstar.fragments.ReplyFragment;
import com.android.rahul_lohra.redditstar.fragments.SearchFragment;
import com.android.rahul_lohra.redditstar.fragments.subreddit.SubredditFragment;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.loader.SubredditLoader;
import com.android.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.android.rahul_lohra.redditstar.service.GetDashboardSubredditsService;
import com.android.rahul_lohra.redditstar.service.GetFrontPageService;
import com.android.rahul_lohra.redditstar.service.GetSubredditListService;
import com.android.rahul_lohra.redditstar.service.GetSubscribedSubredditsService;
import com.android.rahul_lohra.redditstar.service.GetUserCredentialsService;
import com.android.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.android.rahul_lohra.redditstar.service.search.SearchSubredditsService;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rkrde on 24-12-2016.
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    //Activity
    void inject(MainActivity activity);
    void inject(SearchActivity activity);
    void inject(DashboardPresenter presenter);
    void inject(CommentsLoader loader);
    void inject(ReplyFragment fragment);
    void inject(HomeFragment fragment);
    void inject(SubredditFragment fragment);
    void inject(SearchFragment fragment);
    //Service
    void inject(GetSubredditListService service);
    void inject(SearchSubredditsService service);
    void inject(SearchLinksService service);
    void inject(GetUserCredentialsService service);
    void inject(GetSubscribedSubredditsService service);
    void inject(GetDashboardSubredditsService service);
    void inject(GetFrontPageService service);
    //Loader
    void inject(SubredditLoader loader);
    //View
    void inject(PostView view);

}
