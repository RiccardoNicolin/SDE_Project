package com.gardenmanager.green_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.gardenmanager.green_service.services.OpenFarmService;
import com.gardenmanager.green_service.services.PerenualService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plants")
public class PlantController {

    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

    private final PerenualService perenualService;
    private final OpenFarmService openFarmService;

    public PlantController(PerenualService perenualService, OpenFarmService openFarmService) {
        this.perenualService = perenualService;
        this.openFarmService = openFarmService;
    }

    @GetMapping
    public Mono<Map<String, Object>> getPlantInfo(@RequestParam String name) {
        String cropSlug = name.toLowerCase(); // Normalize slug
    
        Mono<Map<String, Object>> perenualMono = perenualService.getPlantId(name)
                .flatMap(perenualService::getPlantDetailsById);
    
        Mono<Map<String, Object>> openFarmMono = openFarmService.getPlantInfo(cropSlug);
    
        return Mono.zip(perenualMono, openFarmMono)
                .map(tuple -> {
                    Map<String, Object> perenualData = tuple.getT1();
                    Map<String, Object> openFarmData = tuple.getT2();
    
                    logger.info("Perenual Plant Details: {}", perenualData);
                    logger.info("OpenFarm Plant Details: {}", openFarmData);
    
                    // Extract important fields from Perenual
                    String plantName = (String) perenualData.get("common_name");
                    String imageUrl = perenualData.get("default_image") != null
                            ? (String) ((Map<String, Object>) perenualData.get("default_image")).get("original_url")
                            : null;
                    // String sowingPeriod = (String) perenualData.get("sowing");
                    String harvestTime = (String) perenualData.get("harvest_season");
                    String cycle = (String) perenualData.get("cycle");
                    String watering = (String) perenualData.get("watering");

                    // Extract important fields from OpenFarm
                    String sowingMethod = (String) openFarmData.get("sowing_method");
                    String sunRequirements = (String) openFarmData.get("sun_requirements");
                    // Safely extract growing_degree_days
                    String gddString = (String) openFarmData.get("growing_degree_days");
                    int growing_degree_days = (gddString != null && !gddString.isEmpty()) ? Integer.parseInt(gddString) : 0;
                    
                    // Construct response
                    Map<String, Object> response = new HashMap<>();
                    response.put("name", plantName);
                    response.put("watering", watering);
                    response.put("sun_requirements", sunRequirements);
                    response.put("cycle", cycle);
                    response.put("growing_degree_days", growing_degree_days);
                    response.put("sowing_method", sowingMethod);
                    response.put("harvest_time", harvestTime);
                    response.put("image", imageUrl);

                    return response;
                });
    }
    

}