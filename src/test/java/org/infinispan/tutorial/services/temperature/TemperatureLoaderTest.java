package org.infinispan.tutorial.services.temperature;

import org.infinispan.server.test.junit5.InfinispanServerExtension;
import org.infinispan.server.test.junit5.InfinispanServerExtensionBuilder;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.WeatherLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TemperatureLoaderTest {

   @RegisterExtension
   static InfinispanServerExtension infinispanServerExtension = InfinispanServerExtensionBuilder.server();

   @Test
   public void loadLocationTemperature() {
      DataSourceConnector dataSourceConnector = new DataSourceConnector(infinispanServerExtension.hotrod().createRemoteCacheManager());
      TemperatureLoader temperatureLoader = new TemperatureLoader(dataSourceConnector);
      Float temperatureLoaderForLocation = temperatureLoader.getForLocation(WeatherLoader.LOCATIONS[0]);
      assertNotNull(temperatureLoaderForLocation);
   }
}