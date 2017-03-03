package com.android.rahul_lohra.redditstar.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.android.rahul_lohra.redditstar.utility.SpConstants;
import com.android.rahul_lohra.redditstar.widget.MyWidgetProvider;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import java.lang.annotation.Retention;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.rahul_lohra.redditstar.utility.SpConstants.FAV;
import static com.android.rahul_lohra.redditstar.utility.SpConstants.FRONT_PAGE;
import static com.android.rahul_lohra.redditstar.utility.SpConstants.POPULAR;
import static com.android.rahul_lohra.redditstar.utility.SpConstants.SUBREDDIT;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class WidgetConfigureActivity extends AppCompatActivity {

    @Bind(R.id.rb_popular)
    RadioButton rbPopular;
    @Bind(R.id.rb_front_page)
    RadioButton rbFrontPage;
    @Bind(R.id.rb_subreddit)
    RadioButton rbSubreddit;
    @Bind(R.id.rb_fav)
    RadioButton rbFav;
    @Bind(R.id.rb_group)
    RadioGroup rbGroup;
    @Bind(R.id.et_subreddit)
    EditText etSubreddit;
    @Bind(R.id.tl_subreddit)
    TextInputLayout tlSubreddit;
    @Bind(R.id.btn_apply)
    Button btnApply;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        ButterKnife.bind(this);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        setResult(RESULT_CANCELED);

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    @OnClick({R.id.rb_popular, R.id.rb_front_page, R.id.rb_subreddit, R.id.rb_fav, R.id.et_subreddit, R.id.btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_popular:
                hideAndClearEditText();
                break;
            case R.id.rb_front_page:
                hideAndClearEditText();
                break;
            case R.id.rb_subreddit:
                tlSubreddit.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_fav:
                hideAndClearEditText();
                break;
            case R.id.et_subreddit:
                break;
            case R.id.btn_apply:
                submit();
                break;
        }
    }

    private void submit(){
        int checkedRbId = rbGroup.getCheckedRadioButtonId();
        if(checkedRbId==-1){
            return;
        }

        int subscribe = -1;
        switch (checkedRbId){
            case R.id.rb_popular:
                subscribe = POPULAR;
                break;
            case R.id.rb_front_page:
                subscribe = FRONT_PAGE;
                break;
            case R.id.rb_subreddit:
                subscribe = SUBREDDIT;
                break;
            case R.id.rb_fav:
                subscribe = FAV;
                break;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt(SpConstants.WIDGET_SUBSCRIBE,subscribe).apply();

        makeApiCall();
    }

    private void makeApiCall(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
//        RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),
//                R.layout.my_widget);
        MyWidgetProvider.updateAppWidget(getApplicationContext(),appWidgetManager,mAppWidgetId);

//        appWidgetManager.updateAppWidget(mAppWidgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
/*
        Start sync Adapter or Job Scheduler or Gcm Network Manager
         */
//        WidgetTaskService widgetTaskService = new WidgetTaskService();
//        widgetTaskService.onRunTask(new TaskParams("once"));
        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(this);
        Task task = new OneoffTask.Builder()
                .setService(WidgetTaskService.class)
                .setExecutionWindow(0, 2)
                .setTag(WidgetTaskService.TAG_ONCE)
                .setUpdateCurrent(false)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setRequiresCharging(false)
                .build();
        mGcmNetworkManager.schedule(task);
    }

    private void hideAndClearEditText(){
        etSubreddit.setText("");
        etSubreddit.clearComposingText();
        tlSubreddit.setVisibility(View.GONE);
    }



}
