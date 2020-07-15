package org.infinispan.tutorial.services.weather;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.query.dsl.Query;
import org.infinispan.tutorial.data.LocationWeather;
import org.infinispan.tutorial.data.WeatherCondition;
import org.infinispan.tutorial.db.DataSourceConnector;

import java.util.List;

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
      // STEP Create and run a FROM query
      return null;
   }

   /**
    * Find the cities with a given weather condition
    * @param condition
    *
    * @return city list
    */
   public List<String> findByCondition(WeatherCondition condition) {
      Query<Object[]> query = createFindLocationWeatherByConditionQuery(condition);

      // STEP Create and run a SELECT query - sub-step 4
      return null;
   }

   /**
    * Find continuously the cities with a given weather condition
    * @param condition
    *
    * @return city list
    */
   public void findWeatherByConditionContinuously(WeatherCondition condition) {
      Query query = createFindLocationWeatherByConditionQuery(condition);

      // STEP Create and run a continuous query
   }

   /**
    * Create a query for the country code
    * @param condition, {@link WeatherCondition}
    * @return a Query than can be run in Infinispan
    */
   private Query<Object[]> createFindLocationWeatherByConditionQuery(WeatherCondition condition) {
      // STEP Create and run a SELECT query - sub-steps 1-2-3
      return null;
   }
}
