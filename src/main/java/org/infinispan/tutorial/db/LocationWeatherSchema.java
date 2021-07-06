package org.infinispan.tutorial.db;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.tutorial.data.LocationWeather;

@AutoProtoSchemaBuilder(
      includeClasses = {
            LocationWeather.class
      },
      schemaFileName = "weather.proto",
      schemaFilePath = "proto/",
      schemaPackageName = "org.infinispan.tutorial.data")
public interface LocationWeatherSchema extends GeneratedSchema {
}
