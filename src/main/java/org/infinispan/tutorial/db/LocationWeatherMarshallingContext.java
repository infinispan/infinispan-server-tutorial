package org.infinispan.tutorial.db;

import org.infinispan.client.hotrod.RemoteCacheManager;

import java.util.Objects;

public final class LocationWeatherMarshallingContext {

    public static void initSerializationContext(RemoteCacheManager remoteCacheManager) {
        System.out.println("---- LocationWeatherMarshallingContext - initialize the serialization context for LocationWeather class ----");
        Objects.requireNonNull(remoteCacheManager);

        // STEP Configure the marshalling context
    }
}
