package org.infinispan.tutorial.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.tutorial.services.InfinispanAdmin;
import org.infinispan.tutorial.services.TemperatureService;
import org.infinispan.tutorial.services.WeatherChangesListener;
import org.infinispan.tutorial.services.WeatherService;

import java.util.concurrent.TimeUnit;

public class TemperatureAlertApp {

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
            System.out.println("Portugal : " + temperatureService.getWeatherForLocation("Porto, Portugal"));
            temperatureService.monitorExpiredLocations();

            while(System.in.available() == 0) {
            }

        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
