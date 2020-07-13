package org.infinispan.tutorial.client.temperature;

import org.infinispan.tutorial.client.App;
import org.infinispan.tutorial.services.temperature.TemperatureLoader;

import java.util.concurrent.TimeUnit;

/**
 * Loads temperatures
 * Refresh every 5 seconds
 */
public class TemperatureLoaderApp extends App {

    @Override
    protected void execute() throws Exception {
        TemperatureLoader temperatureLoader = new TemperatureLoader(admin);

        System.out.println("---- Press any key to quit ----");
        while(System.in.available() == 0) {
            temperatureLoader.loadAll();
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public static void main(String[] args) {
        new TemperatureLoaderApp().run();
    }
}
