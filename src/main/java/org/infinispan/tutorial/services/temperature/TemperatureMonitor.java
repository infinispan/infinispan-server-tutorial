package org.infinispan.tutorial.services.temperature;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.tutorial.db.DataSourceConnector;

import java.util.Objects;

public class TemperatureMonitor {
   private final RemoteCache<String, Float> cache;

   public TemperatureMonitor(DataSourceConnector dataSourceConnector) {
      cache = dataSourceConnector.getTemperatureCache();
   }

   public Float getTemperatureForLocation(String location) {
      Objects.requireNonNull(cache, "'temperature' cache is not correctly initialized. "
            + "Check DataSourceConnector - getTemperatureCache method");
      return cache.get(location);
   }

   public class TemperatureChangesListener {
      private String location;

      TemperatureChangesListener(String location) {
         this.location = location;
      }

      // STEP Implement a Client Listener sub-steps 1-2-3
   }

   /**
    * Monitor the given location
    *
    * @param location
    */
   public void monitorLocation(String location) {
      System.out.println("---- Start monitoring temperature changes for " + location + " ----\n");
      TemperatureChangesListener temperatureChangesListener = new TemperatureChangesListener(location);

      // STEP Implement a Client Listener - sub-step 4
   }

}
