package com.gardenmanager.database_service.users;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gardenmanager.database_service.plants.Plant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @JsonIgnore // Prevents infinite recursion when serializing
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Plant> getPlants() {
        return plants;
    }
    
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
