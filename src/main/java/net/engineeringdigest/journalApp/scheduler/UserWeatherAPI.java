package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.RedisCacheService;
import net.engineeringdigest.journalApp.externalApi.ExternalService;
import net.engineeringdigest.journalApp.externalApi.WeatherModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserWeatherAPI {

    @Autowired
    private ExternalService externalWeatherAPIService;

    @Autowired
    private RedisCacheService redisCacheService;

   // @Scheduled(cron = "0 * * ? * *") // This cron expression means the method will be executed at the start of every minute.
    public  String scheduleUserJournal() {
        ResponseEntity<?> responseEntity =testExternalService();
        System.out.println("Executed scheduled task at: " + System.currentTimeMillis() + " with response: " + responseEntity.getBody());
        return "Executed scheduled task at: " + System.currentTimeMillis() + " with response: " + responseEntity.getBody();
    }

    public String getWeatherDataForCache(String city, String userName) {
        WeatherModel cacheValue = redisCacheService.getCacheValue("weather_of_" + city, WeatherModel.class);
        if (cacheValue != null) {
            return "Hi ..!! "+userName+" Cached Weather in " + cacheValue.getLocation().getName() + ": "
                    + cacheValue.getCurrent().getTemperature() + "°C, feels like "
                    + cacheValue.getCurrent().getFeelslike() + "°C";
        }else{
            WeatherModel weatherResponse = externalWeatherAPIService.getDataFromExternalApi(city);
            redisCacheService.setCacheValue("weather_of_" + city, weatherResponse, 300L); // Cache for 5 minutes
            return  "Hi ..!! "+userName+" Weather in " + weatherResponse.getLocation().getName() + ": "
                    + weatherResponse.getCurrent().getTemperature() + "°C, feels like "
                    + weatherResponse.getCurrent().getFeelslike() + "°C";
        }
    }

    public ResponseEntity<?> testExternalService() {
        WeatherModel weatherResponse = externalWeatherAPIService.getDataFromExternalApi("MUMBAI");
        return new ResponseEntity<>("Hello..!! " + " is calling external service to check  weather of :  "
                + weatherResponse.getLocation().getName() + " that is  "+ weatherResponse.getCurrent().getTemperature()
                + " and actually feels like : "+weatherResponse.getCurrent().getFeelslike() , HttpStatus.OK);
    }
}

