package com.rahul_lohra.redditstar.adapter.cursor;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.CursorRecyclerViewAdapter;
import com.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;
import com.rahul_lohra.redditstar.viewHolder.AccountsViewHolder;
import com.varunest.sparkbutton.SparkButton;


import butterknife.ButterKnife;

/**
 * Created by rkrde on 30-03-2017.
 */

public class AccountsAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public interface IAccountsAdapter{
        void showSignOut();
        void hideSignOut();
    }
    private IAccountsAdapter mListener;
    private AccountsViewHolder.IAccountsViewHolder accountsViewHolder;
    public AccountsAdapter(Context context, Cursor cursor, IAccountsAdapter listener, AccountsViewHolder.IAccountsViewHolder accountsViewHolder) {
        super(context, cursor);
        this.mListener = listener;
        this.mContext = context;
        this.accountsViewHolder = accountsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor,int position) {
        AccountsViewHolder accountsViewHolder = (AccountsViewHolder)viewHolder;
        String userName = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.NAME));
        int isActive = cursor.getInt(cursor.getColumnIndex(UserCredentialsColumn.ACTIVE_STATE));
        int sqlId = cursor.getInt(cursor.getColumnIndex(UserCredentialsColumn._ID));

        accountsViewHolder.init(userName,isActive,sqlId);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountsViewHolder(mContext,LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accounts, parent, false),accountsViewHolder);
    }
    @Override
    public int getItemCount() {
        int c = super.getItemCount();
        if(mListener!=null)
        {
            if(c>0)
            {
                mListener.showSignOut();
            }else {
                mListener.hideSignOut();
            }
        }
        return c;
    }


}
