package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.tutorial.data.LocationWeather;

import java.util.Objects;

/**
 * This class connects to Infinispan and gets or creates two caches in the server
 * - Simple cache (String-Float)
 * - Queryable Cache (String, {@link LocationWeather}
 */
public class DataSourceConnector {
    private RemoteCacheManager remoteCacheManager;

    public DataSourceConnector() {

    }

    public DataSourceConnector(RemoteCacheManager remoteCacheManager) {
        this.remoteCacheManager = remoteCacheManager;
    }

    // Step 1 - Connect to Infinispan
    public void connect() {
        System.out.println("---- Connect to Infinispan ----");
        ConfigurationBuilder builder = new ConfigurationBuilder();

        // Hot Rod URI
        builder.uri("hotrod://admin:pass@localhost:11222");

        // For Docker For Mac
        builder.clientIntelligence(ClientIntelligence.BASIC);

        // Connect to the server
        remoteCacheManager = new RemoteCacheManager(builder.build());
    }

    public void health() {
        Objects.requireNonNull(remoteCacheManager);
        System.out.println("---- Connection count: " + remoteCacheManager.getConnectionCount() + " ----");
    }

    // Step 2 - Get or create a simple cache
    public RemoteCache<String, Float> getTemperatureCache() {
        Objects.requireNonNull(remoteCacheManager);
        System.out.println("---- Get or create the 'temperature' cache ----");

        // Get the cache, create it if needed with an existing template name
       return remoteCacheManager.administration()
              .getOrCreateCache("temperature", "example.PROTOBUF_DIST");
    }
    // Step - Get or create a Queryable Cache

    public RemoteCache<String, LocationWeather> getWeatherCache() {
        Objects.requireNonNull(remoteCacheManager);

        // Initialize the Marshalling context
        LocationWeatherMarshallingContext.initSerializationContext(remoteCacheManager);

        System.out.println("---- Get or create the 'weather' cache ----");

        // Get the cache, create it if needed with an existing template name
       return remoteCacheManager.administration()
              .getOrCreateCache("weather", "example.PROTOBUF_DIST");
    }

    public void shutdown() {
        Objects.requireNonNull(remoteCacheManager);
        System.out.println("---- Shutdown the client ----");
        remoteCacheManager.stop();
    }
}
