package com.rahul_lohra.redditstar.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;

/**
 * Created by rkrde on 24-03-2017.
 */

public class CardViewZoom extends CardView implements Animator.AnimatorListener {
    ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(this, "scaleX", 0.95f);
    ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(this, "scaleY", 0.95f);
    ObjectAnimator scaleInX = ObjectAnimator.ofFloat(this, "scaleX", 1f);
    ObjectAnimator scaleInY = ObjectAnimator.ofFloat(this, "scaleY", 1f);
    AnimatorSet zoomOutAnimator;
    AnimatorSet zoomInAnimator;
    IFrontPageAdapter mListener;
    DetailPostModal modal;
    ImageView imageView;
    String id;


    public CardViewZoom(Context context) {
        super(context);
    }

    public CardViewZoom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardViewZoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCardData(DetailPostModal modal, ImageView imageView, String id, IFrontPageAdapter mListener) {
        this.modal = modal;
        this.imageView = imageView;
        this.id = id;
        this.mListener = mListener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("Touch:"+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                zoomInAnimator = new AnimatorSet();
                zoomInAnimator.play(scaleOutX).with(scaleOutY);
                zoomInAnimator.setDuration(200);
                zoomInAnimator.start();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                zoomOutAnimator = new AnimatorSet();
                zoomOutAnimator.play(scaleInX).with(scaleInY);
                zoomOutAnimator.setDuration(200);
                zoomOutAnimator.start();
                zoomOutAnimator.addListener(this);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                zoomOutAnimator = new AnimatorSet();
                zoomOutAnimator.play(scaleInX).with(scaleInY);
                zoomOutAnimator.setDuration(200);
                zoomOutAnimator.start();
        }
        return true;
    }

    @Override
    public void onAnimationStart(Animator animator) {
        System.out.println("start");
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        mListener.sendData(modal,imageView,id);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        System.out.println("cancel");
    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
