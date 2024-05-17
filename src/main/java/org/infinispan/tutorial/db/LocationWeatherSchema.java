package org.infinispan.tutorial.db;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;
import org.infinispan.tutorial.data.LocationWeather;

@ProtoSchema(
      includeClasses = {
            LocationWeather.class
      },
      schemaFileName = "weather.proto",
      schemaFilePath = "proto/",
      schemaPackageName = "org.infinispan.tutorial.data")
public interface LocationWeatherSchema extends GeneratedSchema {
}
