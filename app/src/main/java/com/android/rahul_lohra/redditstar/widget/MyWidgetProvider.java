package com.android.rahul_lohra.redditstar.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.service.widget.ListWidgetService;

/**
 * Created by rkrde on 01-03-2017.
 */

public class MyWidgetProvider extends AppWidgetProvider {



    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views, Intent intent) {
        views.setRemoteAdapter(R.id.list_view,
                intent);
    }
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views,Intent intent) {
        views.setRemoteAdapter(0, R.id.list_view,
                intent);
    }


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
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
}
