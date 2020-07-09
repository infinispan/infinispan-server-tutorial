package org.infinispan.tutorial.client;

import org.infinispan.client.hotrod.RemoteCache;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomWeatherService extends CachingWeatherService {
   final Random random;

   public RandomWeatherService(RemoteCache<String, LocationWeather> cache) {
      super(cache);
      random = new Random();
   }

   @Override
   protected LocationWeather fetchWeather(String location) {
      try {
         TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {}
      String[] split = location.split(",");
      return new LocationWeather(random.nextFloat() * 20f + 5f, "sunny", split[1]);
   }

}
