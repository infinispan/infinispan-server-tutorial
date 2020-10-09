package org.infinispan.tutorial.services.temperature;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.tutorial.db.DataSourceConnector;

public class TemperatureMonitor {
   private final RemoteCache<String, Float> cache;

   public TemperatureMonitor(DataSourceConnector dataSourceConnector) {
      cache = dataSourceConnector.getTemperatureCache();
   }

   public Float getTemperatureForLocation(String location) {
      return cache.get(location);
   }

   @ClientListener
   public class TemperatureChangesListener {
      private final String location;

      TemperatureChangesListener(String location) {
         this.location = location;
      }

      @ClientCacheEntryCreated
      public void created(ClientCacheEntryCreatedEvent<?> event) {
         if(event.getKey().equals(location)) {
            cache.getAsync(location)
                  .whenComplete((temperature, ex) ->
                  System.out.printf(">> Location %s Temperature %s", location, temperature));
         }
      }
   }

   /**
    * Monitor the given location
    *
    * @param location
    */
   public void monitorLocation(String location) {
      System.out.println("---- Start monitoring temperature changes for " + location + " ----\n");
      TemperatureChangesListener temperatureChangesListener = new TemperatureChangesListener(location);
      cache.addClientListener(temperatureChangesListener);
   }

}
