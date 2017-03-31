package com.rahul_lohra.redditstar.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActivityOptions;
import android.content.ContentValues;
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
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.MyUrl;
import com.rahul_lohra.redditstar.adapter.cursor.AccountsAdapter;
import com.rahul_lohra.redditstar.adapter.cursor.SubredditDrawerAdapter;
import com.rahul_lohra.redditstar.adapter.normal.DrawerAdapter;
import com.rahul_lohra.redditstar.contract.IActivity;
import com.rahul_lohra.redditstar.contract.IDashboard;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.dialog.AddAccountDialog;
import com.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.rahul_lohra.redditstar.fragments.HomeFragment;
import com.rahul_lohra.redditstar.modal.DrawerItemModal;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;
import com.rahul_lohra.redditstar.Utility.CommonOperations;
import com.rahul_lohra.redditstar.Utility.SpConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.rahul_lohra.redditstar.viewHolder.AccountsViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rahul_lohra.redditstar.Utility.MyUrl.AUTH_URL;
import static com.rahul_lohra.redditstar.Utility.MyUrl.CLIENT_ID;
import static com.rahul_lohra.redditstar.Utility.MyUrl.REDIRECT_URI;
import static com.rahul_lohra.redditstar.Utility.MyUrl.STATE;

@SuppressWarnings("HardCodedStringLiteral")
public class DashboardActivity extends BaseActivity implements
        IDashboard,
        LoaderManager.LoaderCallbacks<Cursor>,
        DrawerAdapter.ISubreddit,
        HomeFragment.IHomeFragment,
        ILogin,
        IActivity,
        AccountsAdapter.IAccountsAdapter,
        AccountsViewHolder.IAccountsViewHolder

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
    AccountsAdapter accountsAdapter;
    List<DrawerItemModal> drawerList;
    DashboardPresenter dashboardPresenter;
    AddAccountDialog addAccountDialog;
    @Bind(R.id.adView)
    AdView adView;
    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.image_view_drop_down)
    ImageView imageViewDropDown;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.add_new_account)
    LinearLayout layoutAddNewAccount;
    @Bind(R.id.sign_out)
    LinearLayout layoutSignOut;
    @Bind(R.id.anonymous_user)
    LinearLayout layoutAnonymous;


    private boolean mTwoPane;
    private final String TAG = DashboardActivity.class.getSimpleName();

    private final int LOADER_ID = 1;
    private final int LOADER_ID_NAME = 2;
    private final int LOADER_ID_ACCOUNTS = 3;


    private static final String INTENT_TAG = "DashboardActivity";
    private GcmNetworkManager mGcmNetworkManager;
    private Snackbar snackbar;
    private AdRequest adRequest;
    private Animator rotateDown,rotateUp;


    @OnClick(R.id.image_view_drop_down)
    public void onClickDropDown() {
        perfornAnimation();
        setAccountsAdapter();
    }
    @OnClick(R.id.add_new_account)
    public void onClickAddNewAccount(){
//        addAccountDialog.show(getFragmentManager(), AddAccountDialog.class.getSimpleName());
        addNewAccount();
    }

    @OnClick(R.id.sign_out)
    public void onClickSignOut(){
        performLogout();
    }

    @OnClick(R.id.anonymous_user)
    public void onClickAnonymousUser(){
       enableAnonymousUser();
    }



    private void setAccountsAdapter() {
        RecyclerView.Adapter adapter = rv.getAdapter();
        if (adapter != null) {
            if (adapter instanceof AccountsAdapter) {
                rv.setAdapter(drawerAdapter);
                hideSignOut();
                hideAddAccount();
            } else {
                rv.setAdapter(accountsAdapter);
                showAddAccount();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        init();
        setupDrawer();
        setupPresenter();
        startPeriodicTask();

        if ((findViewById(R.id.frame_layout_right)) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
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

        mGcmNetworkManager.schedule(widgetTask);

    }


    void setupDrawer() {
        drawerList = new ArrayList<>();
        drawerList.add(new DrawerItemModal(getString(R.string.home), ContextCompat.getDrawable(this, R.drawable.ic_home)));
        drawerList.add(new DrawerItemModal(getString(R.string.my_subreddits), ContextCompat.getDrawable(this, R.drawable.ic_list)));
        drawerList.add(new DrawerItemModal(getString(R.string.my_favorites), ContextCompat.getDrawable(this, R.drawable.ic_star)));
        drawerAdapter = new DrawerAdapter(this, drawerList, this, this);
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
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.dashboard));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        addAccountDialog = new AddAccountDialog();

        subredditDrawerAdapter = new SubredditDrawerAdapter(this, null, this);
        accountsAdapter = new AccountsAdapter(this, null, this,this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        getSupportLoaderManager().initLoader(LOADER_ID_NAME, null, this);
        getSupportLoaderManager().initLoader(LOADER_ID_ACCOUNTS, null, this);

        ((TextView) layoutSignOut.findViewById(R.id.tv)).setText(getString(R.string.logout));
        ((AppCompatImageView) layoutSignOut.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_log_out);

        ((TextView) layoutAddNewAccount.findViewById(R.id.tv)).setText(getString(R.string.add_new_account));
        ((AppCompatImageView) layoutAddNewAccount.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_add_3);

        ((TextView) layoutAnonymous.findViewById(R.id.tv)).setText(getString(R.string.anonymous_user));
        ((AppCompatImageView) layoutAnonymous.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_person_inactive);

        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(DashboardActivity.this);
                    }
                });
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

         rotateDown= AnimatorInflater.loadAnimator(this,
                R.animator.rotate_down);
        rotateDown.setTarget(imageViewDropDown);
        rotateUp= AnimatorInflater.loadAnimator(this,
                R.animator.rotate_up);
        rotateUp.setTarget(imageViewDropDown);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putBoolean(SpConstants.OVER_18, false).apply();
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


    private void performLogout() {
        Uri credentialsUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        Uri delUriSubreddit = MyProvider.SubredditLists.CONTENT_URI;

        getContentResolver().delete(credentialsUri, null, null);
        getContentResolver().delete(delUriSubreddit, null, null);

        Snackbar snackbarLogOut = Snackbar
                .make(coordinatorLayout, getString(R.string.user_logged_out), Snackbar.LENGTH_SHORT);
        snackbarLogOut.show();
    }


    @Override
    public void openActivity(final Intent intent) {
        drawer.closeDrawers();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                startActivity(intent);
                drawer.removeDrawerListener(this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void loadMySubreddits() {
    }

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
            case LOADER_ID_ACCOUNTS:
                Uri uriAccounts = MyProvider.UserCredentialsLists.CONTENT_URI;
                return new CursorLoader(this, uriAccounts, null, null, null, null);
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
                break;
            }
            case LOADER_ID_ACCOUNTS: {
                accountsAdapter.swapCursor(data);
                break;
            }

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
                break;
            }
            case LOADER_ID_ACCOUNTS: {
                accountsAdapter.swapCursor(null);
                break;
            }
        }
    }

    @Override
    public void getSubredditRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setAdapter(subredditDrawerAdapter);
        }
    }

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

    @Override
    public void setSubTitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    private void updateName(Cursor cursor) {
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.NAME));
                tv_name.setText(name);
            }else {
                resetName();
            }
        }
    }

    private void resetName() {
        tv_name.setText(getString(R.string.anonymous_user));
        ((AppCompatImageView) layoutAnonymous.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_person_active);
    }

    void perfornAnimation(){
        if(rv.getAdapter()!=null)
        {
            if (rv.getAdapter() instanceof AccountsAdapter) {
                rotateDown.start();
            }else {
                rotateUp.start();
            }
        }

    }

    void enableAnonymousUser(){
        ContentValues cvActive = new ContentValues();
        cvActive.put(UserCredentialsColumn.ACTIVE_STATE,0);
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        getContentResolver().update(mUri,cvActive,null,null);
        ((AppCompatImageView) layoutAnonymous.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_person_active);
    }

    @Override
    public void disableAnonymousUser(){
        ((AppCompatImageView) layoutAnonymous.findViewById(R.id.image_view)).setImageResource(R.drawable.ic_person_inactive);
    }

    @Override
    public void pleaseLogin() {
        snackbar.show();
    }

    @Override
    public void showSignOut() {
        layoutSignOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSignOut() {
        layoutSignOut.setVisibility(View.GONE);
    }

    public void showAddAccount() {
        layoutAddNewAccount.setVisibility(View.VISIBLE);
        layoutAnonymous.setVisibility(View.VISIBLE);
    }

    public void hideAddAccount() {
        layoutAddNewAccount.setVisibility(View.GONE);
        layoutAnonymous.setVisibility(View.GONE);
    }

    private void  addNewAccount(){
    String scopeArray[] = getResources().getStringArray(R.array.scope);
    String scope = MyUrl.getProperScope(scopeArray);
    String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
    Intent intent = new Intent(this, WebViewActivity.class);
    intent.setData(Uri.parse(url));
    startActivity(intent);
}
}
