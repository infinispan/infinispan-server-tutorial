package org.infinispan.tutorial.services;

public interface WeatherLoader<T> {
   String[] LOCATIONS = {"Rome, Italy", "Como, Italy", "Basel, Switzerland", "Bern, Switzerland",
         "London, UK", "Newcastle, UK", "Madrid, Spain", "Bilbao, Spain", "Bucarest, Romania", "Cluj-Napoca, Romania", "Ottawa, Canada",
         "Toronto, Canada", "Lisbon, Portugal", "Porto, Portugal", "Raleigh, USA", "Washington, USA"};

   T getForLocation(String location);

   /**
    * Loads and displays all
    */
   default void loadAll() {
      System.out.println("---- Loading information ----");
      long start = System.currentTimeMillis();
      for (String location : WeatherLoader.LOCATIONS) {
         T weather = this.getForLocation(location);
         System.out.printf("%s - %s\n", location, weather);
      }
      System.out.printf("---- Loaded in %dms ----\n", System.currentTimeMillis() - start);
   }
}
