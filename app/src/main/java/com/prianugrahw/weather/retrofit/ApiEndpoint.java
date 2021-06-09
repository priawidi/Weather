package com.prianugrahw.weather.retrofit;

import com.prianugrahw.weather.model.History;
import com.prianugrahw.weather.model.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiEndpoint {

    @GET("current.json?key=32ae3cd02f3e4b1a82741304212705&q=jakarta&aqi=yes")
    Call<MainModel> getCurrentData();
    @GET("forecast.json?key=32ae3cd02f3e4b1a82741304212705&q=Jakarta&days=4&aqi=no&alerts=no")
    Call<MainModel> getForecastData();
    @GET Call<History> getHistoryData(@Url String url);
}
