package com.gardenmanager.database_service.plants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gardenmanager.database_service.exceptions.PlantNotFoundException;
import com.gardenmanager.database_service.exceptions.UserNotFoundException;
import com.gardenmanager.database_service.users.User;
import com.gardenmanager.database_service.users.UserRepository;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    public Plant addPlant(int userId, Plant plant) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            plant.setUser(user.get());
            return plantRepository.save(plant);
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    public List<Plant> getPlantsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        List<Plant> plants = plantRepository.findByUser(user);

        // Ensure distinct plants are returned
        return plants.stream().distinct().collect(Collectors.toList());
    }

    public Plant getPlantById(int plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("Plant with ID " + plantId + " not found."));
    }

    public void deletePlant(int plantId) {
        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException("Plant with ID " + plantId + " not found.");
        }
        plantRepository.deleteById(plantId);
    }

    public Plant updatePlant(int plantId, Plant plant) {
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("Plant with ID " + plantId + " not found."));

        existingPlant.setPlantname(plant.getPlantname());
        existingPlant.setPlace(plant.getPlace());
        existingPlant.setStartTimeToHarvest(plant.getStartTimeToHarvest());
        existingPlant.setEndTimeToHarvest(plant.getEndTimeToHarvest());

        return plantRepository.save(existingPlant);
    }
}
