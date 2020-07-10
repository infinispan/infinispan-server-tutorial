package org.infinispan.tutorial.services;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.tutorial.data.LocationWeather;

public class InfinispanAdmin {
    private RemoteCacheManager remoteCacheManager;
    private LocationWeatherMarshallingContext locationWeatherMarshallingContext;

    // Step 1 - Connect to Infinispan
    public void connect() {
        System.out.println("Connect to Infinispan");
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clientIntelligence(ClientIntelligence.BASIC);
        builder.addServer().host("127.0.0.1")
              .port(ConfigurationProperties.DEFAULT_HOTROD_PORT);
        builder.security().authentication().username("admin").password("pass");

        // Connect to the server
        remoteCacheManager = new RemoteCacheManager(builder.build());

        // Create Serialization Context
        locationWeatherMarshallingContext = new LocationWeatherMarshallingContext(remoteCacheManager);
    }

    // Step 2 - Create a cache and clean up
    public RemoteCache<String, Float> getSimpleCache() {
        if(remoteCacheManager == null) {
            throw new IllegalStateException("You need to initialize the connection first");
        }

        System.out.println("Get or Create a weather cache");
        // Get the cache, create it if needed with an existing template name
       RemoteCache simpleCache = remoteCacheManager.administration()
              .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
              .getOrCreateCache("weather-simple", DefaultTemplate.DIST_SYNC);

        // Clear all
        System.out.println("Clear the cache on init");
        simpleCache.clear();
        return simpleCache;
    }

    public RemoteCache<String, LocationWeather> getQueryCache() {
        if(remoteCacheManager == null) {
            throw new IllegalStateException("You need to initialize the connection first");
        }

        if(locationWeatherMarshallingContext == null) {
            throw new IllegalStateException("You need to initialize the serialization context for LocationWeather");
        }

        System.out.println("Get or Create a queryable weather cache");
        // Get the cache, create it if needed with an existing template name
       RemoteCache queryCache = remoteCacheManager.administration()
              .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
              .getOrCreateCache("weather-query", DefaultTemplate.DIST_SYNC);

        // Clear all
        System.out.println("Clear the cache on init");
        queryCache.clear();
        return queryCache;
    }

    public void shutdown() {
        if (remoteCacheManager == null) {
            return;
        }

        System.out.println("Shutdown");
        remoteCacheManager.stop();
    }
}
