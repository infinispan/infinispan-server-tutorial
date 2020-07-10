package org.infinispan.tutorial.services;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.tutorial.data.LocationWeather;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LocationWeatherService implements WeatherService<LocationWeather> {
   private final RemoteCache<String, LocationWeather> cache;
   private final Random random;

   private final String[] DESCRIPTIONS = new String[] {"sunny", "cloudy", "rainy", "partially covered"};

   public LocationWeatherService(InfinispanAdmin infinispanAdmin) {
      cache = infinispanAdmin.getQueryCache();
      random = new Random();
   }

   @Override
   public LocationWeather getWeatherForLocation(String location) {
      LocationWeather weather = cache.get(location);
      if (weather == null) {
         weather = fetchWeather(location);
         cache.put(location, weather, 10L, TimeUnit.SECONDS);
      }
      return weather;
   }

   private LocationWeather fetchWeather(String location) {
      try {
         TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {}
      String[] split = location.split(",");
      return new LocationWeather(random.nextFloat() * 20f + 5f, DESCRIPTIONS[random.nextInt(4)], split[1]);
   }
}
