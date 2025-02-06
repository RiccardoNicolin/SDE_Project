package com.gardenmanager.green_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${perenual.api.key}")
    private String perenualApiKey;

    @Value("${perenual.api.url}")
    private String perenualApiUrl;

    @Value("${openfarm.api.url}")
    private String openfarmApiUrl;

    public String getPerenualApiKey() {
        return perenualApiKey;
    }

    public String getPerenualApiUrl() {
        return perenualApiUrl;
    }

    public String getOpenfarmApiUrl() {
        return openfarmApiUrl;
    }
}
