package model;

public class Darshan {
    private int darshan_id;
    private String darshan_type;
    private String description;
    private String time_slot;
    private double price;
    private int max_persons;
    private String status;

    // Default constructor
    public Darshan() {}

    // Getters and Setters
    public int getDarshan_id() {
        return darshan_id;
    }

    public void setDarshan_id(int darshan_id) {
        this.darshan_id = darshan_id;
    }

    public String getDarshan_type() {
        return darshan_type;
    }

    public void setDarshan_type(String darshan_type) {
        this.darshan_type = darshan_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMax_persons() {
        return max_persons;
    }

    public void setMax_persons(int max_persons) {
        this.max_persons = max_persons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 