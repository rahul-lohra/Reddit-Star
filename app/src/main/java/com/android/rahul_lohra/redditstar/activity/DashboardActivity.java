package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.cursor.SubredditDrawerAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.DrawerAdapter;
import com.android.rahul_lohra.redditstar.contract.IActivity;
import com.android.rahul_lohra.redditstar.contract.IDashboard;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.dialog.AddAccountDialog;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.HomeFragment;
import com.android.rahul_lohra.redditstar.modal.DrawerItemModal;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.android.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;
import com.android.rahul_lohra.redditstar.utility.CommonOperations;
import com.android.rahul_lohra.redditstar.utility.SpConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        IDashboard,
        LoaderManager.LoaderCallbacks<Cursor>,
        DrawerAdapter.ISubreddit,
        HomeFragment.IHomeFragment,
        ILogin,
        IActivity

{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    //Adapters
    DrawerAdapter drawerAdapter;
    SubredditDrawerAdapter subredditDrawerAdapter;
    List<DrawerItemModal> drawerList;
    DashboardPresenter dashboardPresenter;
    AddAccountDialog addAccountDialog;
    @Bind(R.id.adView)
    AdView adView;
    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.image_view_add)
    ImageView imageViewAdd;
    private boolean mTwoPane;
    private final String TAG = DashboardActivity.class.getSimpleName();

    private final int LOADER_ID = 1;
    private final int LOADER_ID_NAME = 2;

    private static final String INTENT_TAG = "com.android.rahul_lohra.redditstar.activity.DashboardActivity";
    private GcmNetworkManager mGcmNetworkManager;
    private Snackbar snackbar;
    private AdRequest adRequest;
    private Intent startActivityIntent = null;

    @OnClick(R.id.fab)
    public void onClick() {
        Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
//        dashboardPresenter.getMySubredditsAndDeletePreviousOnes();
    }

    @OnClick(R.id.image_view_add)
    public void onClickAddAccount() {
        addAccountDialog.show(getFragmentManager(), AddAccountDialog.class.getSimpleName());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Constants.clearPosts(this, MyProvider.PostsLists.CONTENT_URI);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        init();
//        setAdapter();
        setupDrawer();
        setupPresenter();
        startPeriodicTask();
//        dashboardPresenter.getMySubredditsAndDeletePreviousOnes();


        if (null!=(findViewById(R.id.frame_layout_right))) {
            mTwoPane = true;
            if (savedInstanceState == null) {
//                showDetailSubredditFragment(null, null, null);
                showHomeFragment(R.id.frame_layout_left);
            }
        } else {
            if (savedInstanceState == null) {
                mTwoPane = false;
                showHomeFragment(R.id.frame_layout_left);
            }
        }
        Log.d(TAG, "isTwoPane:" + mTwoPane + "");

    }

    void showDetailSubredditFragment(DetailPostModal modal, String id, Uri uri) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_right, DetailSubredditFragment.newInstance(id), DetailSubredditFragment.class.getSimpleName())
                .commit();
    }

    void showHomeFragment(@IdRes int layoutId) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, HomeFragment.newInstance(), HomeFragment.class.getSimpleName())
                .commit();
    }

    void startPeriodicTask() {
        mGcmNetworkManager = GcmNetworkManager.getInstance(this);
        Task widgetTask = new PeriodicTask.Builder()
                .setService(WidgetTaskService.class)
                .setPeriod(60 * 31)
                .setFlex(10)
                .setTag(WidgetTaskService.TAG_PERIODIC_WIDGET)
                .setPersisted(true)
                .build();

//        Task frontPageTask = new PeriodicTask.Builder()
//                .setService(WidgetTaskService.class)
//                .setPeriod(60*31)
//                .setFlex(10)
//                .setTag(WidgetTaskService.TAG_PERIODIC_WIDGET)
//                .setPersisted(true)
//                .build();

        mGcmNetworkManager.schedule(widgetTask);
//        mGcmNetworkManager.schedule(frontPageTask);

    }


    void setupDrawer() {
        drawerList = new ArrayList<>();
//        drawerList.add(new DrawerItemModal("Search", ContextCompat.getDrawable(this, R.drawable.ic_home)));
        drawerList.add(new DrawerItemModal(getString(R.string.home), ContextCompat.getDrawable(this, R.drawable.ic_home)));
        drawerList.add(new DrawerItemModal(getString(R.string.my_subreddits), ContextCompat.getDrawable(this, R.drawable.ic_list)));
        drawerList.add(new DrawerItemModal(getString(R.string.my_favorites), ContextCompat.getDrawable(this, R.drawable.ic_star)));
//        drawerList.add(new DrawerItemModal(getString(R.string.settings), ContextCompat.getDrawable(this, R.drawable.ic_settings)));
        drawerAdapter = new DrawerAdapter(this, drawerList, this,this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(drawerAdapter);
    }

    void setupPresenter() {
        if (null == dashboardPresenter) {
            dashboardPresenter = new DashboardPresenter(getApplicationContext(), this);
        }
    }


    void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.dashboard));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        addAccountDialog = new AddAccountDialog();

        subredditDrawerAdapter = new SubredditDrawerAdapter(this, null,this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        getSupportLoaderManager().initLoader(LOADER_ID_NAME, null, this);


        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(DashboardActivity.this);
//                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
                    }
                });
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putBoolean(SpConstants.OVER_18,false).apply();
    }

    void setAdapter() {
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(subredditDrawerAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false);
//        layoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(mCursorAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout: {
                performLogout();
            }
            return true;
            case R.id.action_settings: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void performLogout() {
        Uri credentialsUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        Uri delUriSubreddit = MyProvider.SubredditLists.CONTENT_URI;

        getContentResolver().delete(credentialsUri, null, null);
        getContentResolver().delete(delUriSubreddit, null, null);

        Snackbar snackbarLogOut = Snackbar
                .make(coordinatorLayout, getString(R.string.user_logged_out), Snackbar.LENGTH_SHORT);
        snackbarLogOut.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        return true;
    }

    @Override
    public void openActivity(final Intent intent) {
        drawer.closeDrawers();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {}

            @Override
            public void onDrawerClosed(View drawerView) {
                startActivity(intent);
                drawer.removeDrawerListener(this);
            }
            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    @Override
    public void loadMySubreddits() {}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                Uri uri = MyProvider.UserSubredditsWithFav.CONTENT_URI;
                return new CursorLoader(this, uri, MyProvider.UserSubredditsWithFav.mProjection, null, null, null);
            case LOADER_ID_NAME: {
                Uri uri_2 = MyProvider.UserCredentialsLists.CONTENT_URI;
                String proj_2[] = {UserCredentialsColumn.NAME};
                String mSelection_2 = UserCredentialsColumn.ACTIVE_STATE + "=?";
                String mSelectionArgs_2[] = {"1"};
                return new CursorLoader(this, uri_2, proj_2, mSelection_2, mSelectionArgs_2, null);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.subredditDrawerAdapter.swapCursor(data);
                break;
            case LOADER_ID_NAME: {
                updateName(data);
            }
            break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                subredditDrawerAdapter.swapCursor(null);
                break;
            case LOADER_ID_NAME: {
                resetName();
            }
        }
    }

    @Override
    public void getSubredditRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setAdapter(subredditDrawerAdapter);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }

    private void startActivityWithSharedElement(Intent intent, ImageView imageView) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
        ActivityCompat.startActivity(this, intent, bundle);

    }

    private void startActivityNoSharedElement(Intent intent) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
    }

    @Override
    public void sendModalAndImageView(DetailPostModal modal, ImageView imageView, String id) {
        if (mTwoPane) {
            showDetailSubredditFragment(modal, id, MyProvider.PostsLists.CONTENT_URI);
        } else {
            String mProjection[] = {MyPostsColumn.KEY_POST_HINT, MyPostsColumn.KEY_THUMBNAIL};
            String mSelectionArgs[] = {id};
            String mSelection = MyPostsColumn.KEY_ID + "= ? AND " + MyPostsColumn.KEY_POST_HINT + " IS NOT NULL";

            Cursor cursor = getContentResolver().query(MyProvider.PostsLists.CONTENT_URI, mProjection, mSelection, mSelectionArgs, null);
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("modal", modal);
            intent.putExtra("id", id);
            intent.putExtra("uri", MyProvider.PostsLists.CONTENT_URI);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    startActivityWithSharedElement(intent, imageView);
                } else {
                    startActivityNoSharedElement(intent);
                }
                cursor.close();
            }
        }
    }

    @Override
    public void showLoginSnackBar() {
        snackbar.show();
    }

    private void updateName(Cursor cursor) {
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.NAME));
                tv_name.setText(name);
            }
        }
    }

    private void resetName() {
        tv_name.setText(getString(R.string.no_name));
    }

    @Override
    public void pleaseLogin() {
        snackbar.show();
    }
}
