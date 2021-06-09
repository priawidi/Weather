package com.prianugrahw.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Condition implements Parcelable {

    private String text;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.icon);
    }

    public void readFromParcel(Parcel source) {
        this.text = source.readString();
        this.icon = source.readString();
    }

    public Condition() {
    }

    protected Condition(Parcel in) {
        this.text = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<Condition> CREATOR = new Parcelable.Creator<Condition>() {
        @Override
        public Condition createFromParcel(Parcel source) {
            return new Condition(source);
        }

        @Override
        public Condition[] newArray(int size) {
            return new Condition[size];
        }
    };
}
