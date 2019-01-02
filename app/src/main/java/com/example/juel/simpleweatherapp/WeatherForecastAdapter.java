package com.example.juel.simpleweatherapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by software0 on 4/3/2016.
 */
public class WeatherForecastAdapter extends ArrayAdapter<WeatherForecast> {

    Context context;
    TextView dayNameTv;
    TextView tempConditionTv;
    TextView tempHighTv;
    TextView tempLowTv;

    ArrayList<WeatherForecast> weatherList;


    public WeatherForecastAdapter(Context context, ArrayList<WeatherForecast> weatherList) {
        super(context, R.layout.weather_row_view, weatherList);
        this.context = context;
        this.weatherList = weatherList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.weather_row_view, null);
        dayNameTv = (TextView) convertView.findViewById(R.id.dayNameTextView);
        tempConditionTv = (TextView) convertView.findViewById(R.id.tempConditionTextView);
        tempHighTv = (TextView) convertView.findViewById(R.id.tempHighTextView);
        tempLowTv = (TextView) convertView.findViewById(R.id.tempLowTextView);


        dayNameTv.setText(weatherList.get(position).getDay().toString());
        tempConditionTv.setText(weatherList.get(position).getTempCondition().toString());
        tempHighTv.setText(weatherList.get(position).getTempHigh().toString()+ (char) 0x00B0);
        tempLowTv.setText(weatherList.get(position).getTempLow().toString()+ (char) 0x00B0);

        return convertView;
    }

}
