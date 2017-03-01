package com.android.rahul_lohra.redditstar.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;

/**
 * Created by rkrde on 01-03-2017.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        private Context mContext;
        private int mAppWidgetId;
        String[] mProjection = null;
        String mSelectionClause = null;
        String mSelectionArgs[] = null;
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        private Cursor data = null;
        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            final long identityToken = Binder.clearCallingIdentity();
            data = getContentResolver().query(mUri, mProjection, mSelectionClause, mSelectionArgs, null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
