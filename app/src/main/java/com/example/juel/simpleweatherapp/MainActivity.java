package com.example.juel.simpleweatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String url = "https://goo.gl/vSXwJI";
    TextView cityNameTv;
    TextView lastUpdateTV;
    TextView weatherConditionTv;
    ImageView weatherIcon;
    TextView upTempTv;
    TextView downTempTv;
    TextView currentTempTv;
    TextView sunriseTv;
    TextView sunsetTv;
    TextView windSpeedTv;
    TextView humidityTv;
    TextView visibilityTv;
    LinearLayout mainBackgroundImage;
    ArrayList<WeatherForecast> weatherList = new ArrayList<>();
    WeatherForecastAdapter weatherForecastAdapter;
    WeatherForecast weatherForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_view);
        cityNameTv = (TextView) findViewById(R.id.cityNameTv);
        lastUpdateTV = (TextView) findViewById(R.id.lastUpadateTextView);
        weatherConditionTv = (TextView) findViewById(R.id.weatherConditionTextView);
        weatherIcon = (ImageView) findViewById(R.id.weatherIconView);
        upTempTv = (TextView) findViewById(R.id.upTepmTv);
        downTempTv = (TextView) findViewById(R.id.downTempTv);
        currentTempTv = (TextView) findViewById(R.id.currentTempTv);
        sunriseTv = (TextView) findViewById(R.id.sunriseTextView);
        sunsetTv = (TextView) findViewById(R.id.sunsetTextView);
        windSpeedTv = (TextView) findViewById(R.id.windSpeedTextView);
        humidityTv = (TextView) findViewById(R.id.humidityTextView);
        visibilityTv = (TextView) findViewById(R.id.visibilityTextView);
        mainBackgroundImage = (LinearLayout) findViewById(R.id.mainBackgroundImage);
        weatherList = new ArrayList<>();
        getDataFromWeb();

    }


    private void getDataFromWeb() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject channel = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel");

                    int weatherCode = Integer.parseInt(channel.getJSONObject("item").getJSONObject("condition").getString("code").toString());
                    String cityName = channel.getJSONObject("location").getString("city").toString();
                    String countryName = channel.getJSONObject("location").getString("country").toString();
                    cityNameTv.setText(cityName.concat(", ").concat(countryName));

                    lastUpdateTV.setText(channel.getString("lastBuildDate").toString());
                    weatherConditionTv.setText(channel.getJSONObject("item").getJSONObject("condition").getString("text").toString());
                    upTempTv.setText(channel.getJSONObject("item").getJSONArray("forecast").getJSONObject(0).getString("high").toString() + (char) 0x00B0);
                    downTempTv.setText(channel.getJSONObject("item").getJSONArray("forecast").getJSONObject(0).getString("low").toString() + (char) 0x00B0);
                    currentTempTv.setText(channel.getJSONObject("item").getJSONObject("condition").getString("temp").toString() + (char) 0x00B0 + "F");
                    sunriseTv.setText(channel.getJSONObject("astronomy").getString("sunrise").toString());
                    sunsetTv.setText(channel.getJSONObject("astronomy").getString("sunset").toString());
                    windSpeedTv.setText(channel.getJSONObject("wind").getString("speed").toString() + " mph");
                    humidityTv.setText(channel.getJSONObject("atmosphere").getString("humidity").toString() + " %");
                    visibilityTv.setText(channel.getJSONObject("atmosphere").getString("visibility").toString() + " mi");

                    JSONArray forecast = channel.getJSONObject("item").getJSONArray("forecast");
                    for (int i = 0; i < forecast.length(); i++) {
                        String day = forecast.getJSONObject(i).getString("day");
                        String condition = forecast.getJSONObject(i).getString("text");
                        String high = forecast.getJSONObject(i).getString("high");
                        String low = forecast.getJSONObject(i).getString("low");

                        weatherForecast = new WeatherForecast(day, condition, high, low);
                        weatherList.add(weatherForecast);
                    }
                    //mainBackgroundImage.getDividerDrawable(R.drawable.acti

                    //mainBackgroundImage.setBackgroundResource(R.drawable.thunderstorm);

                    weatherConditionImageChoice(weatherCode);

                    weatherForecastAdapter = new WeatherForecastAdapter(getApplicationContext(), weatherList);
                    listView.setAdapter(weatherForecastAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "No internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }


        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getDataFromWeb();
           weatherList = new ArrayList<>();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void weatherConditionImageChoice(int weatherCode) {
        switch (weatherCode) {
            case 0:
                weatherIcon.setImageResource(R.drawable.weather0);
                break;
            case 1:
                weatherIcon.setImageResource(R.drawable.weather1);
                break;
            case 2:
                weatherIcon.setImageResource(R.drawable.weather2);
                break;
            case 3:
                weatherIcon.setImageResource(R.drawable.weather3);
                break;
            case 4:
                weatherIcon.setImageResource(R.drawable.weather4);
                // mainBackgroundImage.setBackgroundResource(R.drawable.thunderstrom);
                break;
            case 5:
                weatherIcon.setImageResource(R.drawable.weather5);
                break;
            case 6:
                weatherIcon.setImageResource(R.drawable.weather6);
                break;
            case 7:
                weatherIcon.setImageResource(R.drawable.weather7);
                break;
            case 8:
                weatherIcon.setImageResource(R.drawable.weather8);
                break;
            case 9:
                weatherIcon.setImageResource(R.drawable.weather9);
                break;
            case 10:
                weatherIcon.setImageResource(R.drawable.weather10);
                break;

            case 11:
                weatherIcon.setImageResource(R.drawable.weather11);
                break;
            case 12:
                weatherIcon.setImageResource(R.drawable.weather12);
                break;
            case 13:
                weatherIcon.setImageResource(R.drawable.weather13);
                break;
            case 14:
                weatherIcon.setImageResource(R.drawable.weather14);
                break;
            case 15:
                weatherIcon.setImageResource(R.drawable.weather15);
                break;
            case 16:
                weatherIcon.setImageResource(R.drawable.weather16);
                break;
            case 17:
                weatherIcon.setImageResource(R.drawable.weather17);
                break;
            case 18:
                weatherIcon.setImageResource(R.drawable.weather18);
                break;
            case 19:
                weatherIcon.setImageResource(R.drawable.weather19);
                break;
            case 20:
                weatherIcon.setImageResource(R.drawable.weather20);
                break;

            case 21:
                weatherIcon.setImageResource(R.drawable.weather21);
                break;
            case 22:
                weatherIcon.setImageResource(R.drawable.weather22);
                break;
            case 23:
                weatherIcon.setImageResource(R.drawable.weather23);
                break;
            case 24:
                weatherIcon.setImageResource(R.drawable.weather24);
                break;
            case 25:
                weatherIcon.setImageResource(R.drawable.weather25);
                break;
            case 26:
                weatherIcon.setImageResource(R.drawable.weather26);
                break;
            case 27:
                weatherIcon.setImageResource(R.drawable.weather27);
                break;
            case 28:
                weatherIcon.setImageResource(R.drawable.weather28);
                break;
            case 29:
                weatherIcon.setImageResource(R.drawable.weather29);
                break;
            case 30:
                weatherIcon.setImageResource(R.drawable.weather30);
                break;

            case 31:
                weatherIcon.setImageResource(R.drawable.weather31);
                break;
            case 32:
                weatherIcon.setImageResource(R.drawable.weather32);
                break;
            case 33:
                weatherIcon.setImageResource(R.drawable.weather33);
                break;
            case 34:
                weatherIcon.setImageResource(R.drawable.weather34);
                break;
            case 35:
                weatherIcon.setImageResource(R.drawable.weather35);
                break;
            case 36:
                weatherIcon.setImageResource(R.drawable.weather36);
                break;
            case 37:
                weatherIcon.setImageResource(R.drawable.weather37);
                break;
            case 38:
                weatherIcon.setImageResource(R.drawable.weather38);
                break;
            case 39:
                weatherIcon.setImageResource(R.drawable.weather39);
                break;
            case 40:
                weatherIcon.setImageResource(R.drawable.weather40);
                break;
            case 41:
                weatherIcon.setImageResource(R.drawable.weather41);
                break;
            case 42:
                weatherIcon.setImageResource(R.drawable.weather42);
                break;
            case 43:
                weatherIcon.setImageResource(R.drawable.weather43);
                break;
            case 44:
                weatherIcon.setImageResource(R.drawable.weather44);
                break;
            case 45:
                weatherIcon.setImageResource(R.drawable.weather45);
                break;
            case 46:
                weatherIcon.setImageResource(R.drawable.weather46);
                break;
            case 47:
                weatherIcon.setImageResource(R.drawable.weather47);
                break;
            default:
                weatherIcon.setImageResource(R.drawable.na);
                break;


        }


    }

}
