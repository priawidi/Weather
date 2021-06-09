package com.prianugrahw.weather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prianugrahw.weather.adapter.DetailCurrentAdapter;
import com.prianugrahw.weather.adapter.ForecastAdapter;
import com.prianugrahw.weather.model.Forecastday;
import com.prianugrahw.weather.model.History;
import com.prianugrahw.weather.model.MainModel;
import com.prianugrahw.weather.retrofit.ApiService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity implements ForecastAdapter.OnForecastListener{

    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ForecastActivity forecastActivity;

    TextView tv_hist_date, tv_hist_avgtemp, tv_hist_condition;
    TextView tv_date, tv_avgtemp, tv_condition;
    ImageView tv_icon, history_img;
    ArrayList<Forecastday> listForecast = new ArrayList<>();
    ArrayList<Forecastday> listHistory = new ArrayList<>();

    private static final String TAG = "ForecastActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        tv_date = findViewById(R.id.tv_forecast_date);
        tv_avgtemp = findViewById(R.id.tv_forecast_avgtemp);
        tv_condition =findViewById(R.id.tv_forecast_condition);
        tv_icon = findViewById(R.id.img_forecast);

        tv_hist_avgtemp = findViewById(R.id.tv_history_avgtemp);
        tv_hist_condition = findViewById(R.id.tv_history_condition);
        history_img = findViewById(R.id.img_history);


        recyclerView = findViewById(R.id.list_forecast);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(forecastAdapter);
        forecastActivity = this;

        getForecastDataFromApi();
        getHistoryDataFromApi();

    }




    private void getForecastDataFromApi() {
        ApiService.endpoint().getForecastData().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                listForecast = response.body().getForecast().getForecastday();
                forecastAdapter = new ForecastAdapter(listForecast, forecastActivity);
                recyclerView.setAdapter(forecastAdapter);
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getHistoryDataFromApi() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        String history = dateFormat.format(cal.getTime());
        //History -1
        ApiService.endpoint().getHistoryData("history.json?key=32ae3cd02f3e4b1a82741304212705&q=Jakarta&dt=" + history).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                listHistory = response.body().getForecast().getForecastday();

                tv_hist_avgtemp.setText(Double.toString(listHistory.get(0).getDay().getAvgtemp_c())+ " °C");
                tv_hist_condition.setText(listHistory.get(0).getDay().getCondition().getText());
                Glide.with(ForecastActivity.this).load("https:" + listHistory.get(0).getDay().getCondition().getIcon()).into(history_img);

            }
            @Override
            public void onFailure(Call<History> call, Throwable t) {

            }
        });
    }

    @Override
    public void onForecastClick(int position) {
        Intent intent = new Intent(this, DetailForecastActivity.class);
        intent.putExtra("ForecastDay", listForecast.get(position));
        startActivity(intent);
        Log.d(TAG, "onForecastClick: click");
    }

}