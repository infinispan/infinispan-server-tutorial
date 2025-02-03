package org.infinispan.tutorial.services.temperature;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.server.test.junit5.InfinispanServerExtension;
import org.infinispan.server.test.junit5.InfinispanServerExtensionBuilder;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.db.LocationWeatherSchema;
import org.infinispan.tutorial.db.LocationWeatherSchemaImpl;
import org.infinispan.tutorial.services.WeatherLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TemperatureLoaderTest {

   @RegisterExtension
   static InfinispanServerExtension infinispanServerExtension = InfinispanServerExtensionBuilder.server();

   @Test
   public void loadLocationTemperature() {
      DataSourceConnector dataSourceConnector = new DataSourceConnector(createRemoteCacheManager());
      TemperatureLoader temperatureLoader = new TemperatureLoader(dataSourceConnector);
      Float temperatureLoaderForLocation = temperatureLoader.getForLocation(WeatherLoader.LOCATIONS[0]);
      assertNotNull(temperatureLoaderForLocation);
   }

   private RemoteCacheManager createRemoteCacheManager() {
      ConfigurationBuilder clientConfiguration = new ConfigurationBuilder();
      RemoteCacheManager remoteCacheManager = infinispanServerExtension.hotrod().withClientConfiguration(clientConfiguration).createRemoteCacheManager();
      SerializationContext serCtx = MarshallerUtil.getSerializationContext(remoteCacheManager);
      LocationWeatherSchema schema = new LocationWeatherSchemaImpl();
      schema.registerSchema(serCtx);
      schema.registerMarshallers(serCtx);
      return remoteCacheManager;
   }
}
