package com.android.rahul_lohra.redditstar.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.Constants;

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
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION ||
                    data == null || !data.moveToPosition(position)) {
                return null;
            }
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

            final String sqlId = data.getString(data.getColumnIndex(MyPostsColumn.KEY_SQL_ID));
            final String id = data.getString(data.getColumnIndex(MyPostsColumn.KEY_ID));
            final String subreddit = data.getString(data.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
            final String subredditId = data.getString(data.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT_ID));
            final String name = data.getString(data.getColumnIndex(MyPostsColumn.KEY_NAME));
            final String author = data.getString(data.getColumnIndex(MyPostsColumn.KEY_AUTHOR));
            final long createdUtc = data.getLong(data.getColumnIndex(MyPostsColumn.KEY_CREATED_UTC));
            final String time = Constants.getTimeDiff(createdUtc);
            final String ups = String.valueOf(data.getString(data.getColumnIndex(MyPostsColumn.KEY_UPS)));
            final String title = String.valueOf(data.getString(data.getColumnIndex(MyPostsColumn.KEY_TITLE)));
            final String commentsCount = String.valueOf(data.getString(data.getColumnIndex(MyPostsColumn.KEY_COMMENTS_COUNT)));
            final String thumbnail = data.getString(data.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
            final String url = data.getString(data.getColumnIndex(MyPostsColumn.KEY_URL));
            final Integer likes = data.getInt(data.getColumnIndex(MyPostsColumn.KEY_LIKES));
            final String bigImageUrl = data.getString(data.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));

            DetailPostModal modal = new DetailPostModal(id,
                    subreddit,ups,title,commentsCount,thumbnail,time,author,bigImageUrl,likes,name);

            rv.setTextViewText(R.id.tv_detail,title);
            rv.setTextViewText(R.id.tv_vote,ups);
            rv.setTextViewText(R.id.tv_title, subreddit+" - "+time);

            final Intent fillInIntent = new Intent();
            fillInIntent.putExtra("modal", modal);
            rv.setOnClickFillInIntent(R.id.coordinator_layout, fillInIntent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}