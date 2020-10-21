package org.infinispan.tutorial.services.weather;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.tutorial.data.LocationWeather;
import org.infinispan.tutorial.data.WeatherCondition;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.WeatherLoader;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Loads the Weather for location and puts the data into a Queryable Cache
 */
public class FullWeatherLoader implements WeatherLoader<LocationWeather> {
   private final RemoteCache<String, LocationWeather> cache;
   private final Random random;

   public FullWeatherLoader(DataSourceConnector dataSourceConnector) {
      cache = dataSourceConnector.getWeatherCache();
      verifyWeatherCache();
      cache.clear();
      random = new Random();
   }

   @Override
   public LocationWeather getForLocation(String location) {
      verifyWeatherCache();
      LocationWeather weather = cache.get(location);

      if (weather == null) {
         weather = fetchWeather(location);
         cache.put(location, weather);
      } else {
         weather.setCondition(getCondition());
         cache.put(location, weather);
      }
      return weather;
   }

   private void verifyWeatherCache() {
      Objects.requireNonNull(cache,
            "'weather' cache is not correctly initialized. " + "Check DataSourceConnector - getWeatherCache method");
   }

   private LocationWeather fetchWeather(String location) {
      try {
         TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
      String[] cityCountry = location.split(", ");

      return new LocationWeather(random.nextFloat() * 20f + 5f, getCondition(), cityCountry[0], cityCountry[1]);
   }

   private String getCondition() {
      return WeatherCondition.values()[random.nextInt(10)].name();
   }
}
