package com.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.rahul_lohra.redditstar.BuildConfig;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.fragments.SearchFragment;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.SuggestionColumn;

import static com.rahul_lohra.redditstar.storage.MyDatabase.SUGGESTION_TABLE;

public class SearchActivity extends BaseActivity implements
        SearchFragment.ISearchFragment,
        ILogin

{
    private Uri uri = MyProvider.PostsLists.CONTENT_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState==null){
            showSearchFragment();
        }


//        if(BuildConfig.DEBUG){
//            if(BuildConfig.VERSION_CODE ==2){
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
////                        Database db = new SQLiteDatabase();
//
//                        db.beginTransaction();
//                        try {
//                            String execSql = "CREATE TABLE IF NOT EXISTS"+SUGGESTION_TABLE+" ( "
//                                    + SuggestionColumn.KEY_SUGGESTION+" TEXT NOT NULL UNIQUE "
//                                    + SuggestionColumn.KEY_SQL_ID+" INTEGER PRIMARY KEY "
//                                    +" )";
//                            db.execSQL(execSql);
//                            db.setTransactionSuccessful();
//                        }catch (Exception e){}
//                        finally {
//                            db.endTransaction();
//                        }
//                        return;
//                    }
//                }).start();
//            }
//        }
    }

    private void showSearchFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SearchFragment.newInstance(), SearchFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openDetailScreen(DetailPostModal modal, ImageView imageView,String id) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("uri",uri);
        intent.putExtra("modal",modal);

        startActivity(intent, bundle);
    }

    @Override
    public void pleaseLogin() {

    }
}
