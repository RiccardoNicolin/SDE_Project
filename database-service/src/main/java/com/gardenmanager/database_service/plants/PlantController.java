package com.gardenmanager.database_service.plants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @PostMapping("/{userId}")
    public ResponseEntity<Plant> addPlant(@PathVariable int userId, @RequestBody Plant plant) {
        return ResponseEntity.ok(plantService.addPlant(userId, plant));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Plant>> getPlantsByUser(@PathVariable int userId) {
        return ResponseEntity.ok(plantService.getPlantsByUser(userId));
    }

    @DeleteMapping("/{plantId}")
    public ResponseEntity<String> deletePlant(@PathVariable int plantId) {
        plantService.deletePlant(plantId);
        return ResponseEntity.ok("Plant deleted successfully.");
    }
}
