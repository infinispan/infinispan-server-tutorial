package org.infinispan.tutorial.client.temperature;

import org.infinispan.tutorial.db.DataSourceConnector;
import org.infinispan.tutorial.services.temperature.TemperatureMonitor;

public class TemperatureAlertApp {

    public static void main(String[] args) throws Exception {
        DataSourceConnector admin = null;

        try {
            admin = new DataSourceConnector();
            admin.connect();
            TemperatureMonitor temperatureMonitor = new TemperatureMonitor(admin);

            Float temperature = temperatureMonitor.getTemperatureForLocation("Bilbao, Spain");
            System.out.printf("Temperature %s for Bilbao, Spain\n", temperature);

            temperatureMonitor.monitorLocation("Bilbao, Spain");

            while(System.in.available() == 0) {
            }

        } finally {
            if(admin != null)
                admin.shutdown();
        }
    }
}
