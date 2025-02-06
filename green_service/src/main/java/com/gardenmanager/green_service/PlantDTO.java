package com.gardenmanager.green_service;

public class PlantDTO {
    private String name;
    private String imageUrl;
    private String sowingPeriod;
    private String harvestTime;

    // Constructor
    public PlantDTO(String name, String imageUrl, String sowingPeriod, String harvestTime) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.sowingPeriod = sowingPeriod;
        this.harvestTime = harvestTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSowingPeriod() {
        return sowingPeriod;
    }

    public void setSowingPeriod(String sowingPeriod) {
        this.sowingPeriod = sowingPeriod;
    }

    public String getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(String harvestTime) {
        this.harvestTime = harvestTime;
    }

}