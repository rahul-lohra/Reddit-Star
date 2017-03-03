package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import com.android.rahul_lohra.redditstar.contract.IDashboard;
import com.android.rahul_lohra.redditstar.dialog.AddAccountDialog;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.HomeFragment;
import com.android.rahul_lohra.redditstar.modal.DrawerItemModal;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.android.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.utility.CommonOperations;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        IDashboard,
        LoaderManager.LoaderCallbacks<Cursor>,
        DrawerAdapter.ISubreddit,
        HomeFragment.IHomeFragment

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
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.image_view_add)
    ImageView imageViewAdd;
    private boolean mTwoPane;
    private final String TAG = DashboardActivity.class.getSimpleName();

    private final int LOADER_ID = 1;
    private static final String INTENT_TAG = "com.android.rahul_lohra.redditstar.activity.DashboardActivity";
    private GcmNetworkManager mGcmNetworkManager;
    private Snackbar snackbar;
    private AdRequest adRequest;
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
//        Constants.clearTable(this, MyProvider.PostsLists.CONTENT_URI);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        init();
//        setAdapter();
        setupDrawer();
        setupPresenter();
        startPeriodicTask();
//        dashboardPresenter.getMySubredditsAndDeletePreviousOnes();


        if ((findViewById(R.id.frame_layout_right) != null)) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                showDetailSubredditFragment(null,null,null);
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

    void showDetailSubredditFragment(DetailPostModal modal,String id, Uri uri) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_right, DetailSubredditFragment.newInstance(modal,id,uri), DetailSubredditFragment.class.getSimpleName())
                .commit();
    }

    void showHomeFragment(@IdRes int layoutId) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, HomeFragment.newInstance(), HomeFragment.class.getSimpleName())
                .commit();
    }

    void startPeriodicTask(){
        mGcmNetworkManager = GcmNetworkManager.getInstance(this);
        Task task = new PeriodicTask.Builder()
                .setService(WidgetTaskService.class)
                .setPeriod(60*31)
                .setFlex(10)
                .setTag(WidgetTaskService.TAG_PERIODIC)
                .setPersisted(true)
                .build();

        mGcmNetworkManager.schedule(task);
    }


    void setupDrawer() {
        drawerList = new ArrayList<>();
        drawerList.add(new DrawerItemModal("Search", ContextCompat.getDrawable(this, R.drawable.ic_home)));
        drawerList.add(new DrawerItemModal("Home", ContextCompat.getDrawable(this, R.drawable.ic_home)));
        drawerList.add(new DrawerItemModal("My Subreddits", ContextCompat.getDrawable(this, R.drawable.ic_list)));
        drawerList.add(new DrawerItemModal(getString(R.string.my_favorites), ContextCompat.getDrawable(this, R.drawable.ic_star)));
        drawerList.add(new DrawerItemModal("Settings", ContextCompat.getDrawable(this, R.drawable.ic_settings)));
        drawerAdapter = new DrawerAdapter(this, drawerList, this);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        addAccountDialog = new AddAccountDialog();

        subredditDrawerAdapter = new SubredditDrawerAdapter(this, null);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void loadMySubreddits() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LOADER_ID:
                Uri uri = MyProvider.SubredditLists.CONTENT_URI;
                String mProjection[] = {
                        MySubredditColumn.KEY_SQL_ID,
                        MySubredditColumn.KEY_NAME,
                        MySubredditColumn.KEY_DISPLAY_NAME,
                        MySubredditColumn.KEY_ID,
                        MySubredditColumn.KEY_TITLE,
                        MySubredditColumn.KEY_URL
                };
                return new CursorLoader(this, uri, mProjection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.subredditDrawerAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                subredditDrawerAdapter.swapCursor(null);
                break;
        }
    }


    @Override
    public void getSubredditRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setAdapter(subredditDrawerAdapter);
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(DetailPostModal modal) {
//        if(mTwoPane){
//            showDetailSubredditFragment(modal);
//        }else {
//            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,imageView,imageView.getTransitionName()).toBundle();
//            Intent intent = new Intent(this, DetailActivity.class);
//            intent.putExtra("modal",modal);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent,bundle);
//
////            getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.frame_layout_left, DetailSubredditFragment.newInstance(modal), DetailSubredditFragment.class.getSimpleName())
////                    .addToBackStack(DetailSubredditFragment.class.getSimpleName())
////                    .commit();
//        }
//    }

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

    @Override
    public void sendModalAndImageView(DetailPostModal modal, ImageView imageView,String id) {
        if (mTwoPane) {
            showDetailSubredditFragment(modal,id,MyProvider.PostsLists.CONTENT_URI);
        } else {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("modal", modal);
            intent.putExtra("id",id);
            intent.putExtra("uri", MyProvider.PostsLists.CONTENT_URI);
            startActivity(intent, bundle);

        }
    }

    @Override
    public void showLoginSnackBar() {
        snackbar.show();
    }


}
