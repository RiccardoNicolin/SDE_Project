package com.gardenmanager.database_service.plants;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/api/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @PostMapping("/{userId}")
    public ResponseEntity<Plant> addPlant(@PathVariable int userId, @RequestBody String plant) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(plant);
        Plant newPlant = new Plant();
        newPlant.setPlantname(json.get("plantname").asText());
        newPlant.setPlace("Not planted yet");
        Date date = new Date(0);
        newPlant.setStartTimeToHarvest(date);
        newPlant.setEndTimeToHarvest(date);
        return ResponseEntity.ok(plantService.addPlant(userId, newPlant));

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

    @Autowired
    private PlantRepository plantRepository;

    @GetMapping("/getId")
    public Integer getLastId() {
        int id = plantRepository.getLastId();
        return id;
    }

    @PutMapping("/{plantId}")
    public ResponseEntity<Plant> updatePlant(@PathVariable int plantId, @RequestBody String args) throws JsonProcessingException {
        System.out.println("args: " + args);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(args);
        Date planted = new Date(json.get("planted").asLong());
        Date toHarvest = new Date(json.get("toHarvest").asLong());
        String place = json.get("place").asText();
        int PlantId = json.get("id").asInt();
        Plant Plant = plantRepository.findById(PlantId).get();
        if (Plant == null) {
            return ResponseEntity.notFound().build();
        }
        Plant.setPlace(place);
        Plant.setStartTimeToHarvest(planted);
        Plant.setEndTimeToHarvest(toHarvest);

        return ResponseEntity.ok(plantService.updatePlant(plantId, Plant));
    }
}
