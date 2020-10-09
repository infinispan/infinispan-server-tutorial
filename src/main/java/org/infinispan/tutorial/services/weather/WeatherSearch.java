package org.infinispan.tutorial.services.weather;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.api.continuous.ContinuousQuery;
import org.infinispan.query.api.continuous.ContinuousQueryListener;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.tutorial.data.LocationWeather;
import org.infinispan.tutorial.data.WeatherCondition;
import org.infinispan.tutorial.db.DataSourceConnector;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains all the methods to perform Query
 * - Simple Query
 * - Continuous query
 */
public class WeatherSearch {

   private final RemoteCache<String, LocationWeather> weather;

   public WeatherSearch(DataSourceConnector admin) {
      this.weather = admin.getWeatherCache();
   }

   /**
    * Search all the tracked locations in a country
    *
    * @param country
    * @return {@link LocationWeather} for country
    */
   public List<LocationWeather> findByCountry(String country) {
      // Get the query factory
      QueryFactory queryFactory = Search.getQueryFactory(weather);

      // Use Ickle to run the query
      Query<LocationWeather> query = queryFactory.create("FROM org.infinispan.tutorial.data.LocationWeather WHERE country = :country");

      // Set the parameter value
      query.setParameter("country", country);

      return query.execute().list();
   }

   /**
    * Find the cities with a given weather condition
    * @param condition
    *
    * @return city list
    */
   public List<String> findByCondition(WeatherCondition condition) {
      Query<Object[]> query = createFindLocationWeatherByConditionQuery(condition);
      return query.execute().list().stream().map(data -> (String) data[0]).collect(Collectors.toList());
   }

   /**
    * Find continuously the cities with a given weather condition
    * @param condition
    *
    * @return city list
    */
   public void findWeatherByConditionContinuously(WeatherCondition condition) {
      Query<Object[]> query = createFindLocationWeatherByConditionQuery(condition);

      ContinuousQuery<String, LocationWeather> continuousQuery = Search.getContinuousQuery(weather);

      // Create the continuous query listener.
      ContinuousQueryListener<String, Object[]> listener =
            new ContinuousQueryListener<>() {
               // This method will be executed every time new items that correspond with the query arrive
               @Override
               public void resultJoining(String key, Object[] data) {
                  System.out.printf("%s is now %s%n", data[0], condition);
               }
            };

      // And the listener corresponding the query to the continuous query
      continuousQuery.addContinuousQueryListener(query, listener);
   }

   /**
    * Create a query for the country code
    * @param condition, {@link WeatherCondition}
    * @return a Query than can be run in Infinispan
    */
   private Query<Object[]> createFindLocationWeatherByConditionQuery(WeatherCondition condition) {
      // Get the query factory
      QueryFactory queryFactory = Search.getQueryFactory(weather);

      // Use Ickle to run the query
      Query<Object[]> query = queryFactory.create("SELECT city FROM org.infinispan.tutorial.data.LocationWeather WHERE condition = :condition");

      // Set the parameter value
      query.setParameter("condition", condition.name());

      return query;
   }
}
