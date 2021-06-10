package com.prianugrahw.weather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prianugrahw.weather.adapter.DetailForecastAdapter;
import com.prianugrahw.weather.adapter.ForecastAdapter;
import com.prianugrahw.weather.model.Forecastday;
import com.prianugrahw.weather.model.Hour;

import java.text.ParseException;
import java.util.ArrayList;


public class DetailForecastActivity extends AppCompatActivity {

    TextView tv_date, tv_avgtemp_c,tv_maxtemp_c ,tv_mintemp_c,tv_maxwind_kph,tv_totalprecip_mm, tv_humidity;
    ImageView img;

    DetailForecastAdapter detailForecastAdapter;
    private  RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeLayout;
    LinearLayout linearLayout;

    ArrayList<Hour> listDetailForecast = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        tv_date = findViewById(R.id.tv_detail_forecast_date);
        tv_avgtemp_c= findViewById(R.id.tv_avgtemp);
        tv_maxtemp_c= findViewById(R.id.tv_maxtemp);
        tv_mintemp_c = findViewById(R.id.tv_mintemp);
        tv_maxwind_kph = findViewById(R.id.tv_maxwind);
        tv_totalprecip_mm = findViewById(R.id.tv_totalprecip);
        tv_humidity = findViewById(R.id.tv_humidity);
        img = findViewById(R.id.img_detail_weather);

        recyclerView = findViewById(R.id.list_detail_forecast);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailForecastAdapter);

        getData();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {

        //Parcelable
        Forecastday detailForecast = getIntent().getParcelableExtra("ForecastDay");

        // SET TEXT
        String hari = detailForecast.getDate();
        SimpleDateFormat fromdate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat Formatter = new SimpleDateFormat("EEEE, dd MMMM");
        SimpleDateFormat Formatter1 = new SimpleDateFormat("HH:mm");
        String dateFormat = null;
        String dateFormat1 = null;
        try {
            dateFormat = Formatter.format(fromdate.parse(hari));
            dateFormat1 = Formatter1.format(fromdate.parse(hari));
        } catch (ParseException e) {
            e.printStackTrace();

        }
        tv_date.setText(dateFormat);
        tv_avgtemp_c.setText( Double.toString(detailForecast.getDay().getAvgtemp_c()) + "°C");
        tv_maxtemp_c.setText( Double.toString(detailForecast.getDay().getMaxtemp_c()) + "°C");
        tv_mintemp_c.setText( Double.toString(detailForecast.getDay().getMintemp_c()) + "°C");
        tv_maxwind_kph.setText(Double.toString(detailForecast.getDay().getMaxwind_kph()) + "km/h");
        tv_totalprecip_mm.setText(Double.toString(detailForecast.getDay().getTotalprecip_mm()) + "mm");
        tv_humidity.setText(Double.toString(detailForecast.getDay().getAvghumidity()) + "%");
        Glide.with(this).load("https:" + detailForecast.getDay().getCondition().getIcon()).into(img);

        findViewById(R.id.loadingPanel3).setVisibility(View.GONE);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        listDetailForecast = detailForecast.getHour();
        DetailForecastAdapter detailForecastAdapter = new DetailForecastAdapter(listDetailForecast);
        recyclerView.setAdapter(detailForecastAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RefreshScreen() {
        findViewById(R.id.loadingPanel3).setVisibility(View.VISIBLE);
        getData();
    }

}