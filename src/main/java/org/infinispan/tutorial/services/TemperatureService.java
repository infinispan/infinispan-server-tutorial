package org.infinispan.tutorial.services;

import org.infinispan.client.hotrod.RemoteCache;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TemperatureService implements WeatherService<Float> {
   private final RemoteCache<String, Float> cache;
   private final Random random;

   public TemperatureService(InfinispanAdmin infinispanAdmin) {
      cache = infinispanAdmin.getSimpleCache();
      random = new Random();
   }

   public Float getTemperatureForLocation(String location) {
      return cache.get(location);
   }

   @Override
   public Float getWeatherForLocation(String location) {
      Float temperature = cache.get(location);
      if (temperature == null) {
         temperature = fetchTemperature(location);
         cache.put(location, temperature, random.nextInt(10), TimeUnit.SECONDS);
      }
      return temperature;
   }

   public void monitorExpiredLocations() {
      cache.addClientListener(new WeatherChangesListener());
   }

   private Float fetchTemperature(String location) {
      try {
         TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {}

      return random.nextFloat() * 20f + 5f;
   }
}
