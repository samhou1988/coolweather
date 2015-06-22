package net.fenotes.sam.coolweather.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import net.fenotes.sam.coolweather.db.CoolWeatherDB;
import net.fenotes.sam.coolweather.model.City;
import net.fenotes.sam.coolweather.model.Country;
import net.fenotes.sam.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sam on 15/6/22.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);

                    // save the parse data into db
                    coolWeatherDB.saveProvince(province);
                }

                return true;
            }
        }

        return  false;
    }

    /**
     * parse the cities
     */
    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);

                    coolWeatherDB.saveCity(city);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * parse the coutries
     */
    public synchronized static boolean handleCoutriesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCoutries = response.split(",");
            if (allCoutries != null && allCoutries.length > 0) {
                for (String c : allCoutries) {
                    String[] array = c.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    country.setCityId(cityId);

                    coolWeatherDB.saveCountry(country);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * parse weather json data
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * save weather data into SharedPreferences
     */
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }
}
