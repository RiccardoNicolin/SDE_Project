package com.gardenmanager.database_service.plants;

import com.gardenmanager.database_service.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query("SELECT DISTINCT p FROM Plant p WHERE p.user = :user")
    List<Plant> findByUser(@Param("user") User user);
}