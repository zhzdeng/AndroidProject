package com.example.deng.experimentfour;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Adapter;

/**
 * Created by deng on 2016/10/19.
 */
public class Fruit implements Parcelable {
    private String name;
    private int pictureId;

    public Fruit(String name, int pictureId) {
        this.name = name;
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public int getPictureId() {
        return pictureId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.pictureId);
    }

    protected Fruit(Parcel in) {
        this.name = in.readString();
        this.pictureId = in.readInt();
    }

    public static final Parcelable.Creator<Fruit> CREATOR = new Parcelable.Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel source) {
            return new Fruit(source);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };
}

