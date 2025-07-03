package model;

import java.sql.Timestamp;

public class Prasadam {
    private int prasadamId;
    private String name;
    private String description;
    private String type;
    private String availableDays;
    private String specialOccasion;
    private int quantityAvailable;
    private int adminId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Prasadam() {}

    // Getters and Setters
    public int getPrasadamId() {
        return prasadamId;
    }
    public void setPrasadamId(int prasadamId) {
        this.prasadamId = prasadamId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAvailableDays() {
        return availableDays;
    }
    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public String getSpecialOccasion() {
        return specialOccasion;
    }
    public void setSpecialOccasion(String specialOccasion) {
        this.specialOccasion = specialOccasion;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}