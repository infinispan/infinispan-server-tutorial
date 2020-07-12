package org.infinispan.tutorial.client;

import org.infinispan.tutorial.db.DataSourceConnector;

/**
 * Contains the logic of the Apps
 *  - Connect to the datasource
 *  - Execution of the app logic
 *  - Run the application until we quit
 *  - Shutdown the connection
 */
public abstract class App {

   protected DataSourceConnector admin = null;

   public void run() {
      try {
         admin = new DataSourceConnector();
         admin.connect();
         execute();
      } catch (Exception e) {
         e.printStackTrace();

      } finally {
         if(admin != null)
            admin.shutdown();
      }
   }

   protected abstract void execute() throws Exception;
}
