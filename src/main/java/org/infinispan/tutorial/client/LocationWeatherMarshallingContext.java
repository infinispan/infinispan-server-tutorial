package org.infinispan.tutorial.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;

import java.io.IOException;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

public class LocationWeatherMarshallingContext {
    private RemoteCacheManager cacheManager;

    public LocationWeatherMarshallingContext(RemoteCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    public void initSerializationContext() throws IOException {
        // Get the serialization context of the client
        SerializationContext ctx = MarshallerUtil.getSerializationContext(cacheManager);

        // Use ProtoSchemaBuilder to define a Protobuf schema on the client
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String fileName = "weather.proto";
        String protoFile = protoSchemaBuilder
                .fileName(fileName)
                .addClass(LocationWeather.class)
                .packageName("tutorial")
                .build(ctx);

        // Retrieve metadata cache
        RemoteCache<String, String> metadataCache =
                cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);

        // Define the new schema on the server too
        metadataCache.put(fileName, protoFile);
    }
}
