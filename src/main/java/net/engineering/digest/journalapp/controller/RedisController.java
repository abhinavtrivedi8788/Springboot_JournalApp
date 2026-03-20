package net.engineering.digest.journalapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineering.digest.journalapp.scheduler.UserWeatherAPI;
import net.engineering.digest.journalapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redis")
@Tag(name = "Redis API's")
public class RedisController {

    @Autowired
    private UserWeatherAPI userWeatherAPI;

    @Autowired
    private EmailService mailSender;

    @GetMapping(value = "/{city}",produces = "text/plain")
    public String getWeatherDataForCache(@PathVariable  String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        String weatherDataForCache = userWeatherAPI.getWeatherDataForCache(city, userName);
        mailSender.sendEmail("abhinavtrivedii@gmail.com","Weather Updates of : "+city,weatherDataForCache);
        return weatherDataForCache;
    }
}
