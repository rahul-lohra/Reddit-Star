package com.android.rahul_lohra.redditstar.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.reply.ReplyModal;
import com.android.rahul_lohra.redditstar.modal.reply.ReplyResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReplyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = ReplyFragment.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et)
    EditText et;

    @Inject
    @Named("withToken")
    Retrofit retrofit;
    ApiInterface apiInterface;

    private String thingId;
    String token;

    public ReplyFragment() {
        // Required empty public constructor
    }

    public static ReplyFragment newInstance(String param1) {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);

        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mProjection[]={UserCredentialsColumn.ACCESS_TOKEN};
        String mSelection=UserCredentialsColumn.ACTIVE_STATE+"=?";
        String mSelectionArgs[]={"1"};
        Cursor cursor = getContext().getContentResolver().query(mUri,mProjection,mSelection,mSelectionArgs,null);

        if(cursor.moveToFirst())
        {
            do{
                token = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.ACCESS_TOKEN));
            }while (cursor.moveToNext());
        }
        cursor.close();



        if (getArguments() != null) {
            thingId = getArguments().getString(ARG_PARAM1);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reply, container, false);
        ButterKnife.bind(this, v);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_reply, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_send:
                makeReply();
                break;
            default:
        }
        return true;
    }

    private void makeReply() {
        //1.get text data
        //2. send it

        String message = et.getText().toString();
        if(message.isEmpty()){
            Toast.makeText(getContext(),getString(R.string.write_something),Toast.LENGTH_SHORT).show();
            return;
        }
        String auth = "bearer "+token;
        ReplyModal modal  = new ReplyModal("json",message, thingId);
        apiInterface.postComment(auth,"json",message, thingId).enqueue(new Callback<ReplyResponse>() {
            @Override
            public void onResponse(Call<ReplyResponse> call, Response<ReplyResponse> response) {
                Log.d(TAG,"onResponse");
                if(response.code()==200){
                    List<String> mErrorList = response.body().getJsonData().getMError();
                    if(mErrorList.size()>0){
                        showToast((mErrorList.get(1)!=null?mErrorList.get(1):mErrorList.get(0)));
                    }else {
                        showToast(getString(R.string.success));
                    }
                }
            }

            @Override
            public void onFailure(Call<ReplyResponse> call, Throwable t) {
                Log.d(TAG,"onFail");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
