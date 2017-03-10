package com.android.rahul_lohra.redditstar.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;

/**
 * Created by rkrde on 18-02-2017.
 */

public class AwesomeSearchView extends SearchView {

    public AwesomeSearchView(Context context) {
        super(context);
    }

    public AwesomeSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AwesomeSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void onActionViewCollapsed() {
        super.onActionViewCollapsed();
        Toast.makeText(getContext(),getContext().getString(R.string.close),Toast.LENGTH_SHORT).show();
//        exitReveal();
    }

    @Override
    public void onActionViewExpanded() {
        super.onActionViewExpanded();
        Toast.makeText(getContext(),getContext().getString(R.string.open),Toast.LENGTH_SHORT).show();
//        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
//        enterReveal();
    }

    void enterReveal() {
        // previously invisible view
        final View myView = this;

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;
        cx = myView.getRight();
        cy = myView.getBottom();

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.setDuration(2000).start();
    }

    void exitReveal() {
        // previously visible view
        final View myView = this;

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }
}
