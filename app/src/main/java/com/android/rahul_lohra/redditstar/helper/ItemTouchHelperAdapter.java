package com.android.rahul_lohra.redditstar.helper;

/**
 * Created by rkrde on 17-02-2017.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
