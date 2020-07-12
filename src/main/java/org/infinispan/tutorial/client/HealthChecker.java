package org.infinispan.tutorial.client;

/**
 * Checks the connection
 */
public class HealthChecker extends App {

   @Override
   protected void execute() {
      admin.health();
   }

   public static void main(String[] args) {
     new HealthChecker().run();
   }
}
