package model;

import java.math.BigDecimal;

public class Seva {
    private int sevaId;
    private String sevaName;
    private String timing;
    private BigDecimal price;

    // Default constructor
    public Seva() {
    }

    // Constructor with fields
    public Seva(String sevaName, String timing, BigDecimal price) {
        this.sevaName = sevaName;
        this.timing = timing;
        this.price = price;
    }

    // Getters and Setters
    public int getSevaId() {
        return sevaId;
    }

    public void setSevaId(int sevaId) {
        this.sevaId = sevaId;
    }

    public String getSevaName() {
        return sevaName;
    }

    public void setSevaName(String sevaName) {
        this.sevaName = sevaName;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seva{" +
                "sevaId=" + sevaId +
                ", sevaName='" + sevaName + '\'' +
                ", timing='" + timing + '\'' +
                ", price=" + price +
                '}';
    }
}