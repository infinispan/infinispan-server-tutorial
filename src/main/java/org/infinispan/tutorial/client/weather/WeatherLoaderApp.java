package org.infinispan.tutorial.client.weather;

import org.infinispan.tutorial.client.App;
import org.infinispan.tutorial.services.weather.FullWeatherLoader;
import org.infinispan.tutorial.services.WeatherLoader;

import java.util.concurrent.TimeUnit;

/**
 * Loads weather
 * Refresh every 5 seconds
 */
public class WeatherLoaderApp extends App {

    @Override
    protected void execute() throws Exception {
        WeatherLoader weatherLoader = new FullWeatherLoader(admin);

        System.out.println("---- Press any key to quit ----");
        while(System.in.available() == 0) {
            weatherLoader.loadAll();
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public static void main(String[] args) {
        new WeatherLoaderApp().run();
    }
}
