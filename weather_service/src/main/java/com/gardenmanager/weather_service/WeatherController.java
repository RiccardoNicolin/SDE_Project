package com.gardenmanager.weather_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gardenmanager.weather_service.services.WeatherService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    ;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
        //this.openFarmService = openFarmService;
    }

    @GetMapping
    public Mono<Map<String, Map<String, Object>>> getWeatherInfo(@RequestParam String location) {
        //String cropSlug = name.toLowerCase(); // Normalize slug
        //"46.0400, 11.0759"
        if (location == null) {
            location = "46.0400, 11.0759";
        }
        Mono<Map<String, Object>> weatherData = weatherService.getWeatherInfo(location);

        return weatherData.map(data -> {
            Map<String, Map<String, Object>> response = new HashMap<>();
            LinkedHashMap<String, Object> future = (LinkedHashMap<String, Object>) data.get("forecast");
            List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) future.get("forecastday");
            forecastDays.forEach(day -> {
                Map<String, Object> dayData = new HashMap<>();
                Map<String, Object> singular = (Map<String, Object>) day.get("day");
                String date = (String) day.get("date");
                dayData.put("date", date);
                dayData.put("max_temp", singular.get("maxtemp_c"));
                dayData.put("min_temp", singular.get("mintemp_c"));
                dayData.put("precipitation", singular.get("daily_chance_of_rain"));
                dayData.put("icon", ((Map<String, Object>) singular.get("condition")).get("icon"));
                response.put(date, dayData);
            });
            return response;
        });

    }

}