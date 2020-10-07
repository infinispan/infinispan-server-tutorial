package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import java.util.Objects;

public final class LocationWeatherMarshallingContext {

   public static void initSerializationContext(RemoteCacheManager cacheManager) {
      System.out.println("---- LocationWeatherMarshallingContext - initialize the serialization context for LocationWeather class ----");
      Objects.requireNonNull(cacheManager);

      // Retrieve metadata cache
      RemoteCache<String, String> metadataCache =
            cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);

      GeneratedSchema schema = new LocationWeatherSchemaImpl();
      // Define the new schema on the server too
      metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
   }
}
