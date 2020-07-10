package org.infinispan.tutorial.client;

import org.infinispan.tutorial.services.InfinispanAdmin;
import org.infinispan.tutorial.services.TemperatureService;
import org.infinispan.tutorial.services.WeatherService;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TemperatureApp {

    private static void fetchTemperatures(TemperatureService temperatureService) {
        System.out.println("---- Fetching weather information ----");
        long start = System.currentTimeMillis();
        for (String location : WeatherService.LOCATIONS) {
            Float temperature = temperatureService.getWeatherForLocation(location);
            System.out.printf("%s - %s\n", location, temperature);
        }
        System.out.printf("---- Fetched in %dms ----\n", System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Exception {
        InfinispanAdmin admin = null;

        try {
            admin = new InfinispanAdmin();
            admin.connect();
            TemperatureService temperatureService = new TemperatureService(admin);
            while(System.in.available() == 0) {
                fetchTemperatures(temperatureService);
                TimeUnit.SECONDS.sleep(5);
            }
        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
