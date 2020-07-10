package org.infinispan.tutorial.services;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryExpired;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryExpiredEvent;

@ClientListener
public class WeatherChangesListener {

   @ClientCacheEntryExpired
   public void entryExpired(ClientCacheEntryExpiredEvent<String> event) {
      System.out.printf("Temperature has expired %s%n", event.getKey());
   }
}
