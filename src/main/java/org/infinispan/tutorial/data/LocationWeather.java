package org.infinispan.tutorial.data;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

/**
 * Annotated with Protostream
 */
@Indexed
public class LocationWeather {

   @Basic
   @ProtoField(number = 1, defaultValue = "0.0")
   float temperature;

   @Basic
   @ProtoField(number = 2)
   String condition;

   @Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
   @ProtoField(number = 3)
   String city;

   @Keyword(projectable = true, sortable = true, normalizer = "lowercase", indexNullAs = "unnamed", norms = false)
   @ProtoField(number = 4)
   String country;

   public LocationWeather() {

   }

   @ProtoFactory
   public LocationWeather(float temperature, String condition, String city, String country) {
      this.temperature = temperature;
      this.condition = condition;
      this.city = city;
      this.country = country;
   }

   @Override
   public String toString() {
      return "LocationWeather{" +
              "temperature=" + temperature +
              ", condition='" + condition + '\'' +
              ", city='" + city + '\'' +
              ", country='" + country + '\'' +
              '}';
   }

   public void setCondition(String condition) {
      this.condition = condition;
   }

   public float getTemperature() {
      return temperature;
   }

   public String getCondition() {
      return condition;
   }

   public String getCity() {
      return city;
   }

   public String getCountry() {
      return country;
   }
}
