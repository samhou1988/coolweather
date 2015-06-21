package net.fenotes.sam.coolweather.util;

import android.text.TextUtils;

import net.fenotes.sam.coolweather.db.CoolWeatherDB;
import net.fenotes.sam.coolweather.model.City;
import net.fenotes.sam.coolweather.model.Country;
import net.fenotes.sam.coolweather.model.Province;

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
}
