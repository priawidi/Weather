package com.prianugrahw.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prianugrahw.weather.adapter.DetailForecastAdapter;
import com.prianugrahw.weather.adapter.ForecastAdapter;
import com.prianugrahw.weather.model.Forecastday;
import com.prianugrahw.weather.model.Hour;

import java.util.ArrayList;


public class DetailForecastActivity extends AppCompatActivity {

    TextView tv_date, tv_avgtemp_c,tv_maxtemp_c ,tv_mintemp_c,tv_maxwind_kph,tv_totalprecip_mm;
    ImageView img;

    DetailForecastAdapter detailForecastAdapter;
    private  RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeLayout;
    LinearLayout linearLayout;

    ArrayList<Hour> listDetailForecast = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forecast);

        //initialize swipe refresh layout
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout2);
        linearLayout = (LinearLayout) findViewById(R.id.swiperefresh2);
        int color1 = getResources().getColor(R.color.Purple);
        int color2 = getResources().getColor(R.color.Paradise);
        swipeLayout.setColorSchemeColors(color1,color2);

        //Parcelable
        Forecastday detailForecast = getIntent().getParcelableExtra("ForecastDay");

        tv_date = findViewById(R.id.tv_detail_forecast_date);
        tv_avgtemp_c= findViewById(R.id.tv_avgtemp);
        tv_maxtemp_c= findViewById(R.id.tv_maxtemp);
        tv_mintemp_c = findViewById(R.id.tv_mintemp);
        tv_maxwind_kph = findViewById(R.id.tv_maxwind);
        tv_totalprecip_mm = findViewById(R.id.tv_totalprecip);
        img = findViewById(R.id.img_detail_weather);

        recyclerView = findViewById(R.id.list_detail_forecast);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailForecastAdapter);



        tv_date.setText(detailForecast.getDate());
        tv_avgtemp_c.setText("Avg " + Double.toString(detailForecast.getDay().getAvgtemp_c()) + " °C");
        tv_maxtemp_c.setText("Max " + Double.toString(detailForecast.getDay().getMaxtemp_c()) + " °C");
        tv_mintemp_c.setText("Min " + Double.toString(detailForecast.getDay().getMintemp_c()) + " °C");
        tv_maxwind_kph.setText(Double.toString(detailForecast.getDay().getMaxwind_kph()) + " kph");
        tv_totalprecip_mm.setText(Double.toString(detailForecast.getDay().getTotalprecip_mm()) + " mm");
        Glide.with(this).load("https:" + detailForecast.getDay().getCondition().getIcon()).into(img);

        listDetailForecast = detailForecast.getHour();
        DetailForecastAdapter detailForecastAdapter = new DetailForecastAdapter(listDetailForecast);
        recyclerView.setAdapter(detailForecastAdapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        swipeLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
    }

}