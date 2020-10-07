package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.impl.query.RemoteQuery;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class LocationWeatherMarshallingContext {

    public static void initSerializationContext(RemoteCacheManager cacheManager) {
        System.out.println("---- LocationWeatherMarshallingContext - initialize the serialization context for LocationWeather class ----");
        Objects.requireNonNull(cacheManager);

        try {
            // Retrieve the generated Protobuf schema on the client
            Path proto = Paths.get(RemoteQuery.class.getClassLoader()
                  .getResource("proto/weather.proto").toURI());
            // Retrieve metadata cache
            RemoteCache<String, String> metadataCache =
                  cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
            // Define the new schema on the server too
            metadataCache.put("weather.proto", Files.readString(proto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
