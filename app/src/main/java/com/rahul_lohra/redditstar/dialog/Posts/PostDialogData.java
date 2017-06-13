package com.rahul_lohra.redditstar.dialog.Posts;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

/**
 * Created by rkrde on 15-04-2017.
 */

class PostDialogData implements Parcelable {
    String name;
    @DrawableRes
    int drawableId;

     PostDialogData(String name, @DrawableRes int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.drawableId);
    }

    private PostDialogData(Parcel in) {
        this.name = in.readString();
        this.drawableId = in.readInt();
    }

    public static final Parcelable.Creator<PostDialogData> CREATOR = new Parcelable.Creator<PostDialogData>() {
        @Override
        public PostDialogData createFromParcel(Parcel source) {
            return new PostDialogData(source);
        }

        @Override
        public PostDialogData[] newArray(int size) {
            return new PostDialogData[size];
        }
    };
}

