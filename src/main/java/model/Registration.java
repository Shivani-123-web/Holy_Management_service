package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Registration {
    private int registrationId;
    private int userId;
    private Integer eventId;  // Can be null
    private Integer sevaId;   // Can be null
    private Date registrationDate;
    private Date preferredDate;
    private int numberOfPeople;
    private String status;
    private String paymentStatus;
    private BigDecimal paymentAmount;
    private Timestamp createdAt;

    public Registration() {}

    // Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getSevaId() {
        return sevaId;
    }
    public void setSevaId(Integer sevaId) {
        this.sevaId = sevaId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getPreferredDate() {
        return preferredDate;
    }
    public void setPreferredDate(Date preferredDate) {
        this.preferredDate = preferredDate;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}