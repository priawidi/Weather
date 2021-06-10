package com.prianugrahw.weather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    TextView tv_hist1_date, tv_hist1_avgtemp, tv_hist1_condition;
    TextView tv_date, tv_avgtemp, tv_condition;
    ImageView tv_icon, history_img, history_img1;
    ArrayList<Forecastday> listForecast = new ArrayList<>();
    ArrayList<Forecastday> listHistory = new ArrayList<>();

    SwipeRefreshLayout swipeLayout;
    private static final String TAG = "ForecastActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //initialize swipe refresh layout
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        //refresh color
        int color1 = getResources().getColor(R.color.Purple);
        int color2 = getResources().getColor(R.color.Paradise);
        swipeLayout.setColorSchemeColors(color1,color2);


        tv_date = findViewById(R.id.tv_forecast_date);
        tv_avgtemp = findViewById(R.id.tv_forecast_avgtemp);
        tv_condition =findViewById(R.id.tv_forecast_condition);
        tv_icon = findViewById(R.id.img_forecast);

        tv_hist_avgtemp = findViewById(R.id.tv_history_avgtemp);
        tv_hist_condition = findViewById(R.id.tv_history_condition);
        history_img = findViewById(R.id.img_history);

        tv_hist1_date = findViewById(R.id.tv_history_date1);
        tv_hist1_avgtemp = findViewById(R.id.tv_history_avgtemp1);
        tv_hist1_condition = findViewById(R.id.tv_history_condition1);
        history_img1 = findViewById(R.id.img_history1);

        recyclerView = findViewById(R.id.list_forecast);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(forecastAdapter);
        forecastActivity = this;

        getForecastDataFromApi();
        getHistoryDataFromApi();


        //Refresh
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshScreen();
                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Berhenti berputar/refreshing
                        if(swipeLayout.isRefreshing()){
                            swipeLayout.setRefreshing(false);
                        }
                    }
                }, 500);
            }
        });
    }



    private void getForecastDataFromApi() {
        ApiService.endpoint().getForecastData().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                listForecast = response.body().getForecast().getForecastday();
                forecastAdapter = new ForecastAdapter(listForecast, forecastActivity);
                recyclerView.setAdapter(forecastAdapter);

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getHistoryDataFromApi() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        final Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -2);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        String history = dateFormat.format(cal.getTime());
        String history1 = dateFormat.format(cal1.getTime());
        Log.d(TAG, "ini history" + history);
        Log.d(TAG, "ini history1" + history1);
        //History -1
        ApiService.endpoint().getHistoryData("history.json?key=32ae3cd02f3e4b1a82741304212705&q=Jakarta&dt=" + history).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                listHistory = response.body().getForecast().getForecastday();

                tv_hist_avgtemp.setText(Double.toString(listHistory.get(0).getDay().getAvgtemp_c())+ " °C");
                tv_hist_condition.setText(listHistory.get(0).getDay().getCondition().getText());
                Glide.with(ForecastActivity.this).load("https:" + listHistory.get(0).getDay().getCondition().getIcon()).into(history_img);

                findViewById(R.id.loadingPanel1).setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<History> call, Throwable t) {

                findViewById(R.id.loadingPanel1).setVisibility(View.VISIBLE);
            }
        });
        ApiService.endpoint().getHistoryData("history.json?key=32ae3cd02f3e4b1a82741304212705&q=Jakarta&dt=" + history1).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                listHistory = response.body().getForecast().getForecastday();

                String hari = listHistory.get(0).getDate();
                SimpleDateFormat fromdate= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat Formatter = new SimpleDateFormat("EEE, dd MMM");
                SimpleDateFormat Formatter1 = new SimpleDateFormat("HH:mm");
                String dateFormat = null;
                String dateFormat1 = null;
                try {
                    dateFormat = Formatter.format(fromdate.parse(hari));
                    dateFormat1 = Formatter1.format(fromdate.parse(hari));
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                tv_hist1_date.setText(dateFormat);
                tv_hist1_avgtemp.setText(Double.toString(listHistory.get(0).getDay().getAvgtemp_c())+ " °C");
                tv_hist1_condition.setText(listHistory.get(0).getDay().getCondition().getText());
                Glide.with(ForecastActivity.this).load("https:" + listHistory.get(0).getDay().getCondition().getIcon()).into(history_img1);

                findViewById(R.id.loadingPanel2).setVisibility(View.GONE);
                findViewById(R.id.loadingPanel3).setVisibility(View.GONE);
                findViewById(R.id.loadingPanel1).setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<History> call, Throwable t) {
                findViewById(R.id.loadingPanel1).setVisibility(View.VISIBLE);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RefreshScreen() {

        findViewById(R.id.loadingPanel1).setVisibility(View.VISIBLE);
        listForecast.clear();
        listHistory.clear();
        getForecastDataFromApi();
        getHistoryDataFromApi();
    }
}