package org.infinispan.tutorial.client;

import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
      includeClasses = LocationWeather.class,
      schemaFileName = "weather.proto",
      schemaFilePath = "proto",
      schemaPackageName = "org.infinispan.tutorial.client"
)
public interface SerializationContextInitializer extends org.infinispan.protostream.SerializationContextInitializer {
}
