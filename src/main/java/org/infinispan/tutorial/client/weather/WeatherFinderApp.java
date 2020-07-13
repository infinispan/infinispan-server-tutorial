package org.infinispan.tutorial.client.weather;

import org.infinispan.tutorial.client.App;
import org.infinispan.tutorial.data.WeatherCondition;
import org.infinispan.tutorial.services.weather.WeatherSearch;

/**
 * Use Search capabilities
 * {@link WeatherLoaderApp} needs to be running
 */
public class WeatherFinderApp extends App {

    @Override
    protected void execute() throws Exception {
        WeatherSearch weatherQueryService = new WeatherSearch(admin);

        // Step Query 1: Find by country
        System.out.printf("%s: %s \n", "Spain", weatherQueryService.findByCountry("Spain"));

        // Step Query 2:  Find by condition
        for(WeatherCondition condition: WeatherCondition.values()) {
            System.out.printf("%s: %s \n", condition.name(), weatherQueryService.findByCondition(condition));
        }

        // Step Query 3: Find by condition continuously
        weatherQueryService.findWeatherByConditionContinuously(WeatherCondition.SUNNY);

        // Wait until quit
        System.out.println("---- Press any key to quit ----");
        while(System.in.available() == 0) {
            // Do nothing
        }
    }

    public static void main(String[] args) {
        new WeatherFinderApp().run();
    }
}
