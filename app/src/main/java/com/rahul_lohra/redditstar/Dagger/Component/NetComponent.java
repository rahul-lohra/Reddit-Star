package com.rahul_lohra.redditstar.Dagger.Component;

import com.rahul_lohra.redditstar.activity.DetailActivity;
import com.rahul_lohra.redditstar.activity.DetailActivityNew;
import com.rahul_lohra.redditstar.activity.SearchActivity;
import com.rahul_lohra.redditstar.Dagger.Module.AppModule;
import com.rahul_lohra.redditstar.Dagger.Module.NetModule;
import com.rahul_lohra.redditstar.activity.MainActivity;
import com.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.rahul_lohra.redditstar.fragments.HomeFragment;
import com.rahul_lohra.redditstar.fragments.ReplyFragment;
import com.rahul_lohra.redditstar.fragments.SearchFragment;
import com.rahul_lohra.redditstar.fragments.subreddit.SubredditFragment;
import com.rahul_lohra.redditstar.loader.CommentsLoader;
import com.rahul_lohra.redditstar.loader.SubredditLoader;
import com.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.rahul_lohra.redditstar.service.CommentsService;
import com.rahul_lohra.redditstar.service.GetFrontPageService;
import com.rahul_lohra.redditstar.service.GetSubredditListService;
import com.rahul_lohra.redditstar.service.GetSubscribedSubredditsService;
import com.rahul_lohra.redditstar.service.GetUserCredentialsService;
import com.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.rahul_lohra.redditstar.service.search.SearchSubredditsService;
import com.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.rahul_lohra.redditstar.viewHolder.PostView;
import com.rahul_lohra.redditstar.viewHolder.PostViewDetail;

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
    void inject(DetailActivity activity);
    void inject(DetailActivityNew activity);
    void inject(DashboardPresenter presenter);
    void inject(CommentsLoader loader);
    void inject(ReplyFragment fragment);
    void inject(HomeFragment fragment);
    void inject(SubredditFragment fragment);
    void inject(SearchFragment fragment);
    void inject(DetailSubredditFragment fragment);

    //Service
    void inject(GetSubredditListService service);
    void inject(SearchSubredditsService service);
    void inject(SearchLinksService service);
    void inject(GetUserCredentialsService service);
    void inject(GetSubscribedSubredditsService service);
    void inject(GetFrontPageService service);
    void inject(WidgetTaskService service);
    void inject(CommentsService service);

    //Loader
    void inject(SubredditLoader loader);
    //View
    void inject(PostView view);
    void inject(PostViewDetail view);


}
