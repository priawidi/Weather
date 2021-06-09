package com.prianugrahw.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {

    private Condition condition;

    private Double avgtemp_c;
    private Double maxtemp_c;
    private Double mintemp_c;
    private Double maxwind_kph;
    private Double totalprecip_mm;
    private String daily_will_it_rain;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(Double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public Double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(Double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public Double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(Double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public Double getMaxwind_kph() {
        return maxwind_kph;
    }

    public void setMaxwind_kph(Double maxwind_kph) {
        this.maxwind_kph = maxwind_kph;
    }

    public Double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public void setTotalprecip_mm(Double totalprecip_mm) {
        this.totalprecip_mm = totalprecip_mm;
    }

    public String getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    public void setDaily_will_it_rain(String daily_will_it_rain) {
        this.daily_will_it_rain = daily_will_it_rain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.condition, flags);
        dest.writeValue(this.avgtemp_c);
        dest.writeValue(this.maxtemp_c);
        dest.writeValue(this.mintemp_c);
        dest.writeValue(this.maxwind_kph);
        dest.writeValue(this.totalprecip_mm);
        dest.writeString(this.daily_will_it_rain);
    }

    public void readFromParcel(Parcel source) {
        this.condition = source.readParcelable(Condition.class.getClassLoader());
        this.avgtemp_c = (Double) source.readValue(Double.class.getClassLoader());
        this.maxtemp_c = (Double) source.readValue(Double.class.getClassLoader());
        this.mintemp_c = (Double) source.readValue(Double.class.getClassLoader());
        this.maxwind_kph = (Double) source.readValue(Double.class.getClassLoader());
        this.totalprecip_mm = (Double) source.readValue(Double.class.getClassLoader());
        this.daily_will_it_rain = source.readString();
    }

    public Day() {
    }

    protected Day(Parcel in) {
        this.condition = in.readParcelable(Condition.class.getClassLoader());
        this.avgtemp_c = (Double) in.readValue(Double.class.getClassLoader());
        this.maxtemp_c = (Double) in.readValue(Double.class.getClassLoader());
        this.mintemp_c = (Double) in.readValue(Double.class.getClassLoader());
        this.maxwind_kph = (Double) in.readValue(Double.class.getClassLoader());
        this.totalprecip_mm = (Double) in.readValue(Double.class.getClassLoader());
        this.daily_will_it_rain = in.readString();
    }

    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
