package com.gardenmanager.database_service.plants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
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
