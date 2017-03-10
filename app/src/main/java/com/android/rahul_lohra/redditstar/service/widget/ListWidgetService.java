package com.android.rahul_lohra.redditstar.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

/**
 * Created by rkrde on 01-03-2017.
 */

public class ListWidgetService extends RemoteViewsService {

    final String TAG = ListWidgetService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        private Context mContext;
        private int mAppWidgetId;
        String[] mProjection = null;
        String mSelectionClause = MyPostsColumn.TYPE_WIDGET+"=?";
        String mSelectionArgs[] = {"1"};
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        private Cursor data = null;
        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
            final long identityToken = Binder.clearCallingIdentity();
            data = getContentResolver().query(mUri, mProjection, mSelectionClause, mSelectionArgs, null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            if(data!=null)
                Log.wtf(TAG,"getCount:"+data.getCount());
            return data == null ? 0 : data.getCount();

        }

        @Override
        public RemoteViews getViewAt(int position) {
//            Log.d(TAG, "RemoteViews getViewAt");
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
            final String postHint = data.getString(data.getColumnIndex(MyPostsColumn.KEY_POST_HINT));

            DetailPostModal modal = new DetailPostModal(id,
                    subreddit,ups,title,commentsCount,thumbnail,time,author,bigImageUrl,likes,name,postHint);

            rv.setTextViewText(R.id.tv_detail,title);
            rv.setTextViewText(R.id.tv_vote,ups);
            rv.setTextViewText(R.id.tv_comments,commentsCount);
            rv.setTextViewText(R.id.tv_title, subreddit+" - "+time);


//            final AppWidgetTarget appWidgetTarget = new AppWidgetTarget( mContext, rv, R.id.image_view,mAppWidgetId );
//            BitmapTypeRequest bitmapTypeRequest = Glide.with(mContext.getApplicationContext() ) // safer!
//                    .load(thumbnail)
//                    .asBitmap();

//            FutureTarget<Bitmap> ft = bitmapTypeRequest.into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("id", id);
            fillInIntent.putExtra("uri",MyProvider.PostsLists.CONTENT_URI);

            rv.setOnClickFillInIntent(R.id.parent, fillInIntent);

            try {
                Bitmap bmp = Glide.with(mContext)
                        .load(thumbnail)
                        .asBitmap()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(100,100)
                        .get();

                rv.setImageViewBitmap(R.id.image_view,bmp);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            } catch (ExecutionException e) {
//                e.printStackTrace();
            }


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
            return false;
        }
    }
}
