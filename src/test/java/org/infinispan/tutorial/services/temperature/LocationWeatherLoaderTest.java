package org.infinispan.tutorial.services.temperature;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.server.test.junit5.InfinispanServerExtension;
import org.infinispan.server.test.junit5.InfinispanServerExtensionBuilder;
import org.infinispan.tutorial.data.LocationWeather;
import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.db.LocationWeatherSchema;
import org.infinispan.tutorial.db.LocationWeatherSchemaImpl;
import org.infinispan.tutorial.services.WeatherLoader;
import org.infinispan.tutorial.services.weather.FullWeatherLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocationWeatherLoaderTest {

   @RegisterExtension
   static InfinispanServerExtension infinispanServerExtension = InfinispanServerExtensionBuilder.server();

   @Test
   public void loadLocationWeather() {
      DataSourceConnector dataSourceConnector = new DataSourceConnector(createRemoteCacheManager());
      WeatherLoader<LocationWeather> fullWeatherLoader = new FullWeatherLoader(dataSourceConnector);
      LocationWeather locationWeather = fullWeatherLoader.getForLocation(WeatherLoader.LOCATIONS[0]);
      assertNotNull(locationWeather);
   }

   private RemoteCacheManager createRemoteCacheManager() {
      RemoteCacheManager remoteCacheManager = infinispanServerExtension.hotrod().createRemoteCacheManager();
      SerializationContext serCtx = MarshallerUtil.getSerializationContext(remoteCacheManager);
      LocationWeatherSchema schema = new LocationWeatherSchemaImpl();
      schema.registerSchema(serCtx);
      schema.registerMarshallers(serCtx);
      return remoteCacheManager;
   }
}
