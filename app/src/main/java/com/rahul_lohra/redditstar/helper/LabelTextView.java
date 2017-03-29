package com.rahul_lohra.redditstar.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.rahul_lohra.redditstar.R;

/**
 * Created by rkrde on 24-03-2017.
 */

public class LabelTextView extends AppCompatTextView {
    public LabelTextView(Context context) {
        super(context);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setFinalText(String text, @ColorRes int backgroundColor, @ColorRes int fontColor)
    {
        /*
        Determine if the text is domain or media type (eg: album,link)
         */
        setTextColor(ContextCompat.getColor(getContext(),fontColor));
        setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.rect_filled));
        setSupportBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),backgroundColor)));
        setText(text);
        setPaddingRelative(4,2,4,2);
        setVisibility(VISIBLE);

    }



    public void writeLabel(String domain,String postHint,String link)
    {
        setVisibility(GONE);
        if(postHint!=null)
        {
            if(postHint.equals("image")||postHint.equals("link"))
            {
                if(link!=null && (link.endsWith(".gif")||link.endsWith(".gifv")))
                {
                    String text = (link.endsWith(".gif")?"GIF":"GIFV");
                    setFinalText(text,R.color.light_blue_500,R.color.white);
                }

            }else {
                if(postHint.equals("rich:video"))
                {
                    if(domain.equals("youtube.com")){
                        String text = "YOUTUBE";
                        setFinalText(text,R.color.red_youtube,R.color.white);
                    }else {
                        if(link.endsWith(".gif")||link.endsWith(".gifv"))
                        {
                            String text = (link.endsWith(".gif")?"GIF":"GIFV");
                            setFinalText(text,R.color.light_blue_500,R.color.white);
                        }
                    }
                }
            }
        }

    }

//    @Retention(SOURCE)
//    @StringDef({DIRECTION_UP, DIRECTION_DOWN, DIRECTION_NULL})
//    private  @interface MediaType {}
//    public static final int DIRECTION_DOWN = -1;
//    public static final int DIRECTION_NULL = 0;
//    public static final int DIRECTION_UP = 1;


}
