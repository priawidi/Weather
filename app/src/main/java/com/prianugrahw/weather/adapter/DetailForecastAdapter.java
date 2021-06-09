package com.prianugrahw.weather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prianugrahw.weather.R;
import com.prianugrahw.weather.model.Hour;

import java.util.ArrayList;

public class DetailForecastAdapter extends RecyclerView.Adapter<DetailForecastAdapter.ListViewHolder>{

    ArrayList<Hour> listHourForecast = new ArrayList<>();

    public DetailForecastAdapter(ArrayList<Hour> listHourForecast) {
        this.listHourForecast = listHourForecast;
    }

    @NonNull
    @Override
    public DetailForecastAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast_item, parent,false);
        DetailForecastAdapter.ListViewHolder listViewHolder = new DetailForecastAdapter.ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailForecastAdapter.ListViewHolder holder, int position) {
        String split_date = listHourForecast.get(position).getTime();
        String[] date_splited = split_date.split(" ");
        holder.tv_time.setText(date_splited[1]);
        holder.tv_temp.setText(Double.toString(listHourForecast.get(position).getTemp_c()) + " °C");
        holder.tv_condition.setText(listHourForecast.get(position).getCondition().getText());
        Glide.with(holder.itemView.getContext()).load("https:" + listHourForecast.get(position).getCondition().getIcon()).into(holder.tv_icon);

    }

    @Override
    public int getItemCount() {
        return listHourForecast.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_time, tv_temp, tv_condition, tv_wind, tv_gust, tv_pressure, tv_precip;
        public ImageView tv_icon;

        public ListViewHolder(@NonNull View itemView){
            super(itemView);

            tv_time = itemView.findViewById(R.id.tv_hour_forecast_date);
            tv_temp = itemView.findViewById(R.id.tv_hour_forecast_avgtemp);
            tv_condition = itemView.findViewById(R.id.tv_hour_forecast_condition);
            tv_icon = itemView.findViewById(R.id.img_hour_forecast);


        }
    }
}
