package org.infinispan.tutorial.client.weather;

import org.infinispan.tutorial.data.LocationWeather;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.weather.FullWeatherLoader;
import org.infinispan.tutorial.services.WeatherLoader;

import java.util.concurrent.TimeUnit;

public class WeatherFetcherApp {

    private static void fetchLocationWeather(WeatherLoader<LocationWeather> weatherService) {
        System.out.println("---- Fetching weather information ----");
        long start = System.currentTimeMillis();
        for (String location : WeatherLoader.LOCATIONS) {
            LocationWeather weather = weatherService.getForLocation(location);
            System.out.printf("%s - %s\n", location, weather);
        }
        System.out.printf("---- Fetched in %dms ----\n", System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Exception {

        DataSourceConnector admin = null;

        try {
            admin = new DataSourceConnector();
            admin.connect();
            WeatherLoader weatherService = new FullWeatherLoader(admin);
            while(System.in.available() == 0) {
                fetchLocationWeather(weatherService);
                TimeUnit.SECONDS.sleep(5);
            }
        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
