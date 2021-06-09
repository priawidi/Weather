package com.prianugrahw.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Forecastday implements Parcelable {


    private String date;
    private ArrayList<Hour> hour;
    private Day day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Hour> getHour() {
        return hour;
    }

    public void setHour(ArrayList<Hour> hour) {
        this.hour = hour;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeTypedList(this.hour);
        dest.writeParcelable(this.day, flags);
    }

    public void readFromParcel(Parcel source) {
        this.date = source.readString();
        this.hour = source.createTypedArrayList(Hour.CREATOR);
        this.day = source.readParcelable(Day.class.getClassLoader());
    }

    public Forecastday() {
    }

    protected Forecastday(Parcel in) {
        this.date = in.readString();
        this.hour = in.createTypedArrayList(Hour.CREATOR);
        this.day = in.readParcelable(Day.class.getClassLoader());
    }

    public static final Creator<Forecastday> CREATOR = new Creator<Forecastday>() {
        @Override
        public Forecastday createFromParcel(Parcel source) {
            return new Forecastday(source);
        }

        @Override
        public Forecastday[] newArray(int size) {
            return new Forecastday[size];
        }
    };
}
