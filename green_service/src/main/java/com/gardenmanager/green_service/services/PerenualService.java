package com.gardenmanager.green_service.services;

import com.gardenmanager.green_service.config.ApiConfig;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class PerenualService {

    private final WebClient webClient;
    private final String apiKey;
    private final String baseUrl;

    public PerenualService(ApiConfig apiConfig) {
        this.webClient = WebClient.builder().baseUrl(apiConfig.getPerenualApiUrl()).build();
        this.apiKey = apiConfig.getPerenualApiKey();
        this.baseUrl = apiConfig.getPerenualApiUrl();
    }

    // First call: Search for the plant and get its ID
    public Mono<Integer> getPlantId(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/species-list")
                        .queryParam("key", apiKey)
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .flatMap(response -> {
                    if (response != null && response.containsKey("data")) {
                        List<Map<String, Object>> plants = (List<Map<String, Object>>) response.get("data");
                        if (!plants.isEmpty()) {
                            return Mono.just((Integer) plants.get(0).get("id"));
                        }
                    }
                    return Mono.empty();
                });
    }

    // Second call: Get full details using the extracted ID
    public Mono<Map<String, Object>> getPlantDetailsById(int plantId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/species/details/" + plantId)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
