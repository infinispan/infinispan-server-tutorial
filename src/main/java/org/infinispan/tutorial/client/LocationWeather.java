package org.infinispan.tutorial.client;

import org.infinispan.protostream.annotations.ProtoField;

public class LocationWeather {

   @ProtoField(number = 1, defaultValue = "0.0")
   float temperature;

   @ProtoField(number = 2)
   String conditions;

   @ProtoField(number = 3)
   String country;

   public LocationWeather() {

   }

   public LocationWeather(float temperature, String conditions, String country) {
      this.temperature = temperature;
      this.conditions = conditions;
      this.country = country;
   }

   @Override
   public String toString() {
      return "LocationWeather{" +
              "temperature=" + temperature +
              ", conditions='" + conditions + '\'' +
              ", country='" + country + '\'' +
              '}';
   }
}
