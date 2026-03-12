package net.engineeringdigest.journalApp.externalApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalService {
    private static final String API_KEY = "Dummy key ";// use your own API key from weatherstack.com

    private static final String API = "http://api.weatherstack.com/current?access_key=key_value&query=city_name";


    @Autowired
    private RestTemplate restTemplate;

    public WeatherModel getDataFromExternalApi(String city ) {
        final String API_URL = API.replace("key_value", API_KEY).replace("city_name", city);
        ResponseEntity<WeatherModel> response = restTemplate.exchange(API_URL, HttpMethod.GET,null, WeatherModel.class);
        return response.getBody();
    }


}
