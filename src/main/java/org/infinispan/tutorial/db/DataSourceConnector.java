package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.tutorial.data.LocationWeather;

import java.net.URI;
import java.net.URISyntaxException;
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
        builder.uri("hotrod://admin:secret@localhost:11222");

        // For Docker For Mac. Not recommended for production. Default is HASH_DISTRIBUTION_AWARE
        builder.clientIntelligence(ClientIntelligence.BASIC);

        URI temperatureCacheConfig;
        URI weatherCacheConfig;
        try {
            temperatureCacheConfig = getClass().getClassLoader().getResource("temperatureCacheConfig.xml").toURI();
            weatherCacheConfig = getClass().getClassLoader().getResource("weatherCacheConfig.xml").toURI();
        } catch (URISyntaxException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }

        // Define two caches that should be created in the server if they not exist
        builder.remoteCache("temperature").configurationURI(temperatureCacheConfig);
        builder.remoteCache("weather").configurationURI(weatherCacheConfig);

        // Define the schema on the client
        builder.addContextInitializer(new LocationWeatherSchemaImpl());

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
        System.out.println("---- Get the 'temperature' cache ----");

        // Get the cache
       return remoteCacheManager.getCache("temperature");
    }
    // Step - Get or create a Queryable Cache

    public RemoteCache<String, LocationWeather> getWeatherCache() {
        Objects.requireNonNull(remoteCacheManager);

        // Initialize the Marshalling context
        LocationWeatherMarshallingContext.initSerializationContext(remoteCacheManager);

        System.out.println("---- Get the 'weather' cache ----");

        // Get the cache
       return remoteCacheManager.getCache("weather");
    }

    public void shutdown() {
        Objects.requireNonNull(remoteCacheManager);
        System.out.println("---- Shutdown the client ----");
        remoteCacheManager.stop();
    }
}
