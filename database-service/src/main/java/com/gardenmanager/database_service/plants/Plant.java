package com.gardenmanager.database_service.plants;

import java.util.Date;

import com.gardenmanager.database_service.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String plantname;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startTimeToHarvest;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endTimeToHarvest;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlantname() {
        return plantname;
    }

    public void setPlantname(String plantname) {
        this.plantname = plantname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStartTimeToHarvest() {
        return startTimeToHarvest;
    }

    public void setStartTimeToHarvest(Date startTimeToHarvest) {
        this.startTimeToHarvest = startTimeToHarvest;
    }

    public Date getEndTimeToHarvest() {
        return endTimeToHarvest;
    }

    public void setEndTimeToHarvest(Date endTimeToHarvest) {
        this.endTimeToHarvest = endTimeToHarvest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
