package org.infinispan.tutorial.data;

/**
 * Annotated with Protostream
 */
//STEP Annotate the POJO LocationWeather
public record LocationWeather(float temperature,
                              String condition,
                              String city,
                              String country) {
}
