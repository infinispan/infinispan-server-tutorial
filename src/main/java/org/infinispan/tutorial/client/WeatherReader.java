package org.infinispan.tutorial.client;

import org.infinispan.tutorial.services.InfinispanAdmin;

public class WeatherReader {

    public static void main(String[] args) {

        InfinispanAdmin admin = new InfinispanAdmin();
        admin.connect();

    }
}
