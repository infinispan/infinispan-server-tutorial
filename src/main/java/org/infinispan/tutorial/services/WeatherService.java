package org.infinispan.tutorial.services;

public interface WeatherService<T> {
   String[] LOCATIONS = {"Rome, Italy", "Como, Italy", "Basel, Switzerland", "Bern, Switzerland",
         "London, UK", "Newcastle, UK", "Madrid, Spain", "Bilbao, Spain", "Bucarest, Romania", "Cluj-Napoca, Romania", "Ottawa, Canada",
         "Toronto, Canada", "Lisbon, Portugal", "Porto, Portugal", "Raleigh, USA", "Washington, USA"};

   T getWeatherForLocation(String location);
}
