package org.infinispan.tutorial.client;

import org.infinispan.client.hotrod.RemoteCache;

public abstract class CachingWeatherService implements WeatherService {
   final private RemoteCache<String, LocationWeather> cache;

   public CachingWeatherService(RemoteCache<String, LocationWeather> cache) {
      this.cache = cache;
   }

   @Override
   final public LocationWeather getWeatherForLocation(String location) {
      LocationWeather weather = cache.get(location);
      if (weather == null) {
         weather = fetchWeather(location);
         cache.put(location, weather);
      }
      return weather;
   }

   protected abstract LocationWeather fetchWeather(String location);
}
