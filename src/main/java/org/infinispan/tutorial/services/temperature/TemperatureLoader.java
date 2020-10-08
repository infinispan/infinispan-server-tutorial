package org.infinispan.tutorial.services.temperature;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.WeatherLoader;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Loads the temperature for a given location.
 * Puts the data in a key/value cache containing primitive values
 */
public class TemperatureLoader implements WeatherLoader<Float> {
   private final RemoteCache<String, Float> cache;
   private final Random random;

   public TemperatureLoader(DataSourceConnector dataSourceConnector) {
      cache = dataSourceConnector.getTemperatureCache();
      cache.clear();
      random = new Random();
   }

   @Override
   public Float getForLocation(String location) {
      Float temperature = cache.get(location);
      if (temperature == null) {
         temperature = fetchTemperature(location);
         cache.put(location, temperature, 20, TimeUnit.SECONDS);
      }
      return temperature;
   }

   private Float fetchTemperature(String location) {
      try {
         TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }

      return random.nextFloat() * 20f + 5f;
   }
}
