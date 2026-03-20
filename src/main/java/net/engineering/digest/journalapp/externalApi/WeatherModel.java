package net.engineering.digest.journalapp.externalApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WeatherModel {

    private Current current;
    private Location location;

    @Getter
    @Setter
    @NoArgsConstructor
    public  class Current {

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public  class Location {

        private String name;
        private String country;
    }
}