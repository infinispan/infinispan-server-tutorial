package org.infinispan.tutorial.data;

/**
 * Annotated with Protostream
 */
public class LocationWeather {

   //STEP Annotate the POJO LocationWeather

   float temperature;

   String condition;

   String city;

   String country;

   public LocationWeather() {

   }

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
