package com.prianugrahw.weather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.prianugrahw.weather.model.MainModel;
import com.prianugrahw.weather.retrofit.ApiService;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirQualityActivity extends AppCompatActivity {

    TextView tv_co, tv_no2, tv_so2, tv_o3, tv_pm2_5, tv_pm10, tv_epa, tv_epa_index;
    TextView tv_city, tv_lastup;

    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality);

        //initialize swipe refresh layout
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        //refresh color
        int color1 = getResources().getColor(R.color.Purple);
        int color2 = getResources().getColor(R.color.Paradise);
        swipeLayout.setColorSchemeColors(color1,color2);



        tv_epa_index = findViewById(R.id.tv_epa_index);
        tv_epa = findViewById(R.id.tv_epa);
        tv_co = findViewById(R.id.co);
        tv_no2 = findViewById(R.id.no2);
        tv_o3 = findViewById(R.id.o3);
        tv_so2 = findViewById(R.id.so2);
        tv_pm2_5 = findViewById(R.id.pm2_5);
        tv_pm10 = findViewById(R.id.pm10);
        tv_city = findViewById(R.id.aqi_city);
        tv_lastup = findViewById(R.id.aqi_lastup);

        getCurrentDataFromApi();

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

    private void RefreshScreen() {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        getCurrentDataFromApi();
    }

    private void getCurrentDataFromApi(){
        ApiService.endpoint().getCurrentData().enqueue(new Callback<MainModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                tv_epa.setText(response.body().getCurrent().getAir_quality().getUsepaindexString());
                tv_epa_index.setText(Integer.toString(response.body().getCurrent().getAir_quality().getUsepaindex()));
                tv_co.setText(Float.toString(response.body().getCurrent().getAir_quality().getCo()) );
                tv_o3.setText(Float.toString(response.body().getCurrent().getAir_quality().getO3()) );
                tv_no2.setText(Float.toString(response.body().getCurrent().getAir_quality().getNo2()) );
                tv_so2.setText(Float.toString(response.body().getCurrent().getAir_quality().getSo2()) );
                tv_pm2_5.setText(Float.toString(response.body().getCurrent().getAir_quality().getPm2_5()) );
                tv_pm10.setText(Float.toString(response.body().getCurrent().getAir_quality().getPm10()) );

                tv_city.setText(response.body().getLocation().getName());

                String hari = response.body().getCurrent().getLast_updated();
                SimpleDateFormat fromlastup = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat Formatter = new SimpleDateFormat("EEEE");
                SimpleDateFormat Formatter1 = new SimpleDateFormat("HH:mm");
                String dateFormat = null;
                String dateFormat1 = null;
                try {
                    dateFormat = Formatter.format(fromlastup.parse(hari));
                    dateFormat1 = Formatter1.format(fromlastup.parse(hari));
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                tv_lastup.setText("Last Updated at " + dateFormat1);

                findViewById(R.id.loadingPanel1).setVisibility(View.GONE);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }

}