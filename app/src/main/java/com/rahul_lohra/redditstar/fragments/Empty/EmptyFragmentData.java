package com.rahul_lohra.redditstar.fragments.Empty;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by rkrde on 13-04-2017.
 */

public class EmptyFragmentData implements Parcelable {
    String title;
    String subtitle;
    @DrawableRes int drawableId;

    public EmptyFragmentData(@NonNull String title,@NonNull String subtitle,@Nullable int drawableId) {
        this.title = title;
        this.subtitle = subtitle;
        this.drawableId = drawableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeInt(this.drawableId);
    }

    protected EmptyFragmentData(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.drawableId = in.readInt();
    }

    public static final Parcelable.Creator<EmptyFragmentData> CREATOR = new Parcelable.Creator<EmptyFragmentData>() {
        @Override
        public EmptyFragmentData createFromParcel(Parcel source) {
            return new EmptyFragmentData(source);
        }

        @Override
        public EmptyFragmentData[] newArray(int size) {
            return new EmptyFragmentData[size];
        }
    };
}
