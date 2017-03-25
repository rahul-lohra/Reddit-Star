package com.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.helper.CardViewZoom;
import com.rahul_lohra.redditstar.helper.LabelTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 24-03-2017.
 */

public class GalleryView extends PostView {

    @Bind(R.id.label_tv)
    public LabelTextView labelTextView;
    @Nullable
    @Bind(R.id.card_view_zoom)
    public CardViewZoom cardViewZoom;

    public GalleryView(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);

    }

}
