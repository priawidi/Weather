package com.prianugrahw.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {
   private Current current;
   private Condition condition;
   private AirQuality air_quality;

   @SerializedName("forecastday")
   private ArrayList<Forecastday> forecastday;


   public Current getCurrent() {
      return current;
   }

   public void setCurrent(Current current) {
      this.current = current;
   }

   public Condition getCondition() {
      return condition;
   }

   public void setCondition(Condition condition) {
      this.condition = condition;
   }

   public ArrayList<Forecastday> getForecastday() {
      return forecastday;
   }

   public void setForecastday(ArrayList<Forecastday> forecastday) {
      this.forecastday = forecastday;
   }

   public AirQuality getAir_quality() {
      return air_quality;
   }

   public void setAir_quality(AirQuality air_quality) {
      this.air_quality = air_quality;
   }
}
