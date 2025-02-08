package com.gardenmanager.weather_service.services;

import com.gardenmanager.weather_service.config.ApiConfig;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final String baseUrl;
    private final String key;

    public WeatherService(ApiConfig apiConfig) {
        this.baseUrl = apiConfig.getWeatherUrl();
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.key = apiConfig.getWeatherKey();
    }

    public Mono<Map<String, Object>> getWeatherInfo(String location) {
        //System.out.println("URL: " + baseUrl);
        return webClient.get()
                .uri("forecast.json?key=" +key+"&q="+location+"&days=10&aqi=no&alerts=no")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                });
    }
}
