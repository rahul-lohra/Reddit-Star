package com.android.rahul_lohra.redditstar.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.activity.WidgetConfigureActivity;
import com.android.rahul_lohra.redditstar.service.widget.ListWidgetService;
import com.android.rahul_lohra.redditstar.service.widget.WidgetTaskService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by rkrde on 01-03-2017.
 */

public class MyWidgetProvider extends AppWidgetProvider {


    final static String TAG = MyWidgetProvider.class.getSimpleName();
    private final static String REFRESH_INTENT_ACTION = "REFRESH_DATA";
    private final static String CONFIGURE_ACTIVITY_INTENT_ACTION = "CONFIGURE_ACTIVITY";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive");
        super.onReceive(context, intent);
        if (intent != null) {
            Log.d(TAG, "intent Action:" + intent.getAction());
            //execute intent service
            if (intent.getAction().equals(WidgetTaskService.INTENT_TAG)) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                        new ComponentName(context, getClass()));

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
            }else if(intent.getAction().equals(REFRESH_INTENT_ACTION)){
                int mAppWidgetId = intent.getIntExtra(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                    return;
                }
//                WidgetTaskService widgetTaskService = new WidgetTaskService();
//                widgetTaskService.onRunTask(new TaskParams("once"));
                GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(context);
                Task task = new OneoffTask.Builder()
                        .setService(WidgetTaskService.class)
                        .setExecutionWindow(0, 5)
                        .setTag(WidgetTaskService.TAG_ONCE)
                        .setUpdateCurrent(false)
                        .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                        .setRequiresCharging(false)
                        .build();
                mGcmNetworkManager.schedule(task);

            }else if(intent.getAction().equals(CONFIGURE_ACTIVITY_INTENT_ACTION)){
                int mAppWidgetId = intent.getIntExtra(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                    return;
                }

                Intent configureActivityIntent = new Intent(context, WidgetConfigureActivity.class);
                Bundle extras = new Bundle();
                extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(CONFIGURE_ACTIVITY_INTENT_ACTION);
                context.getApplicationContext().startActivity(configureActivityIntent);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.d(TAG,"onEnabled");
        super.onEnabled(context);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views, Intent intent) {
        views.setRemoteAdapter(R.id.list_view,intent);
    }
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views,Intent intent) {
        views.setRemoteAdapter(0, R.id.list_view,
                intent);
    }


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        Log.d(TAG, "updateAppWidget");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);

        //setRefreshIntent
        setRefreshIntent(context,appWidgetId,views);

        setOpenConfigureActivityIntent(context,appWidgetId,views);

        Bitmap bmpRefresh = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_refresh_white);
        Bitmap bmpConfigure = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_menu_white);

        views.setImageViewBitmap(R.id.image_refresh,bmpRefresh);
        views.setImageViewBitmap(R.id.image_configure,bmpConfigure);

        Intent listWidgetIntent = new Intent(context, ListWidgetService.class);
        listWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views,listWidgetIntent);
        } else {
            setRemoteAdapterV11(context, views,listWidgetIntent);
        }
        views.setEmptyView(R.id.list_view, R.id.empty_view);

        //List Item intent
        Intent listItemIntent = new Intent(context, DetailActivity.class);
        listItemIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        PendingIntent pendingIntentListItem = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(listItemIntent)
                .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.list_view,pendingIntentListItem);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void setRefreshIntent(Context context,int appWidgetId,RemoteViews views){
        Intent refreshIntent = new Intent(context,MyWidgetProvider.class);
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        refreshIntent.setAction(REFRESH_INTENT_ACTION);
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context,0,refreshIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_refresh,refreshPendingIntent);
    }

    private static void setOpenConfigureActivityIntent(Context context,int appWidgetId,RemoteViews views){
        Intent intent = new Intent(context,WidgetConfigureActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(CONFIGURE_ACTIVITY_INTENT_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_configure,pendingIntent);

    }


}
