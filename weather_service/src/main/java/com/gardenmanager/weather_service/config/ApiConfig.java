package com.gardenmanager.weather_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public String getWeatherKey() {
        return weatherApiKey;
    }

    public String getWeatherUrl() {
        return weatherApiUrl;
    }
}
