package com.gardenmanager.green_service.services;

import com.gardenmanager.green_service.config.ApiConfig;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class OpenFarmService {

    private final WebClient webClient;
    private final String baseUrl;

    public OpenFarmService(ApiConfig apiConfig) {
        this.baseUrl = apiConfig.getOpenfarmApiUrl();
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<Map<String, Object>> getPlantInfo(String cropSlug) {
        return webClient.get()
                .uri("/{cropSlug}", cropSlug)  // Use the cropSlug directly
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}