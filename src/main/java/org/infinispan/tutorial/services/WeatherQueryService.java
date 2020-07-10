package org.infinispan.tutorial.services;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.tutorial.data.LocationWeather;

import java.util.List;

public class WeatherQueryService {

   private final RemoteCache<String, LocationWeather> weather;

   public WeatherQueryService(RemoteCache<String, LocationWeather> weather) {
      this.weather = weather;
   }

   /**
    * Create a query for the country code
    * @param country
    * @return a Query than can be run in Infinispan
    */
   public Query createFindWeatherByCountryQuery(String country) {
      // Get the query factory
      QueryFactory queryFactory = Search.getQueryFactory(weather);

      // Use Ickle to run the query
      Query query = queryFactory.create("FROM tutorial.LocationWeather w where w.country = :country");

      // Set the parameter value
      query.setParameter("country", country);

      return query;
   }

   public List<LocationWeather> runQuery(Query query) {
      return query.execute().list();
   }
}
