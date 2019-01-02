package com.example.juel.simpleweatherapp;

/**
 * Created by software0 on 4/4/2016.
 */
public class WeatherForecast {
    private String day;
    private String tempCondition;
    private String tempHigh;
    private String tempLow;

    public WeatherForecast(String day, String tempCondition, String tempHigh, String tempLow) {
        setDay(day);
        setTempCondition(tempCondition);
        setTempHigh(tempHigh);
        setTempLow(tempLow);
    }
    public WeatherForecast() {

    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTempCondition() {
        return tempCondition;
    }

    public void setTempCondition(String tempCondition) {
        this.tempCondition = tempCondition;
    }

    public String getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(String tempHigh) {
        this.tempHigh = tempHigh;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }
}