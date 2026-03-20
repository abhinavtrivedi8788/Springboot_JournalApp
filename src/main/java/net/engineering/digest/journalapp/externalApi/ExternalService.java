package net.engineering.digest.journalapp.externalApi;

import net.engineering.digest.journalapp.cache.ApplicationCache;
import net.engineering.digest.journalapp.enums.KEYS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationCache applicationCache;

    @Value("${weather.api.key}")
    private String API_KEY ;



    public WeatherModel getDataFromExternalApi(String city ) {
        final String API_URL = applicationCache.getAPP_CACHE().get(KEYS.WEATHER_APP.getValue()).replace("<api-key>", API_KEY).replace("<city>", city);
        ResponseEntity<WeatherModel> response = restTemplate.exchange(API_URL, HttpMethod.GET,null, WeatherModel.class);
        return response.getBody();
    }
    
}
