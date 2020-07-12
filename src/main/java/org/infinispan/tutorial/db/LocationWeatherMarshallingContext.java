package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.tutorial.data.LocationWeather;

import java.io.IOException;
import java.util.Objects;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

public final class LocationWeatherMarshallingContext {

    public static void initSerializationContext(RemoteCacheManager cacheManager) {
        System.out.println("LocationWeatherMarshallingContext - initialize the serialization context for LocationWeather class");
        Objects.requireNonNull(cacheManager);

        // Get the serialization context of the client
        SerializationContext ctx = MarshallerUtil.getSerializationContext(cacheManager);

        // Use ProtoSchemaBuilder to define a Protobuf schema on the client
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String fileName = "weather.proto";
        String protoFile = null;
        try {
            protoFile = protoSchemaBuilder
                    .fileName(fileName)
                    .addClass(LocationWeather.class)
                    .packageName("org.infinispan.tutorial.data")
                    .build(ctx);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Retrieve metadata cache
        RemoteCache<String, String> metadataCache =
                cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);

        // Define the new schema on the server too
        metadataCache.put(fileName, protoFile);
    }
}
