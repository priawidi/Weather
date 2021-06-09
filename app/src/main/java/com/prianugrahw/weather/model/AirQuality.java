package com.prianugrahw.weather.model;

import com.google.gson.annotations.SerializedName;

public class AirQuality {
    private float co;
    private float no2;
    private float o3;
    private float so2;
    private float pm2_5;
    private float pm10;
    @SerializedName("us-epa-index")
    private int usepaindex;
    @SerializedName("gb-defra-index")
    private int gbdefraindex;

    public float getCo() {
        return co;
    }

    public void setCo(float co) {
        this.co = co;
    }

    public float getNo2() {
        return no2;
    }

    public void setNo2(float no2) {
        this.no2 = no2;
    }

    public float getO3() {
        return o3;
    }

    public void setO3(float o3) {
        this.o3 = o3;
    }

    public float getSo2() {
        return so2;
    }

    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public float getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(float pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public float getPm10() {
        return pm10;
    }

    public void setPm10(float pm10) {
        this.pm10 = pm10;
    }

    public int getUsepaindex() {

        return usepaindex;
    }

    public String getUsepaindexString(){
        if (usepaindex == 1){
            String result = "Good";
            return result;
        }
        if (usepaindex == 2){
            String result = "Moderate";
            return result;
        }
        if (usepaindex == 3){
            String result = "Unhealthy for sensitive group";
            return result;
        }
        if (usepaindex == 4){
            String result = "Unhealthy";
            return result;
        }
        if (usepaindex == 5){
            String result = "Very Unhealthy";
            return result;
        }
        else {
            String result = "Hazardous";
            return result;
        }
    }

    public void setUsepaindex(int usepaindex) {
        this.usepaindex = usepaindex;
    }

    public int getGbdefraindex() {
        return gbdefraindex;
    }

    public void setGbdefraindex(int gbdefraindex) {
        this.gbdefraindex = gbdefraindex;
    }
}
