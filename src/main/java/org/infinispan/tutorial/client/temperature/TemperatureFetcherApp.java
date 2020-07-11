package org.infinispan.tutorial.client.temperature;

import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.temperature.TemperatureLoader;
import org.infinispan.tutorial.services.WeatherLoader;

import java.util.concurrent.TimeUnit;

public class TemperatureFetcherApp {

    private static void fetchTemperatures(TemperatureLoader temperatureLoader) {
        System.out.println("---- Fetching weather information ----");
        long start = System.currentTimeMillis();
        for (String location : WeatherLoader.LOCATIONS) {
            Float temperature = temperatureLoader.getForLocation(location);
            System.out.printf("%s - %s\n", location, temperature);
        }
        System.out.printf("---- Fetched in %dms ----\n", System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Exception {
        DataSourceConnector admin = null;

        try {
            admin = new DataSourceConnector();
            admin.connect();
            TemperatureLoader temperatureLoader = new TemperatureLoader(admin);
            while(System.in.available() == 0) {
                fetchTemperatures(temperatureLoader);
                TimeUnit.SECONDS.sleep(5);
            }
        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
