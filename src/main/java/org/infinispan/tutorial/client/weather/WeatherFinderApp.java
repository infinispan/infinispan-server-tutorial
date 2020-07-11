package org.infinispan.tutorial.client.weather;

import org.infinispan.tutorial.data.WeatherCondition;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.weather.WeatherSearch;

public class WeatherFinderApp {

    public static void main(String[] args) throws Exception {
        DataSourceConnector admin = null;

        try {
            admin = new DataSourceConnector();
            admin.connect();

            WeatherSearch weatherQueryService = new WeatherSearch(admin);

            for(WeatherCondition condition: WeatherCondition.values()) {
                System.out.printf("%s: %s \n", condition.name(), weatherQueryService.findByCondition(condition));
            }

            weatherQueryService.findWeatherByConditionContinuously(WeatherCondition.SUNNY);

            while(System.in.available() == 0) {
            }

        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
