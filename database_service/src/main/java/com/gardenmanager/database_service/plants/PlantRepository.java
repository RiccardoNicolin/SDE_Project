package com.gardenmanager.database_service.plants;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gardenmanager.database_service.users.User;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query("SELECT DISTINCT p FROM Plant p WHERE p.user = :user")
    List<Plant> findByUser(@Param("user") User user);

    @Query("SELECT id FROM Plant p ORDER BY p.id DESC LIMIT 1")
    Integer getLastId();

}
