package model;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Payment {
    private int paymentId;
    private int bookingId;
    private String transactionId;
    private String paymentMethod;
    private String paymentStatus;
    private Timestamp paymentDate;

    // Default constructor
    public Payment() {
        this.paymentMethod = "QR Code";
        this.paymentStatus = "Pending";
    }

    // Constructor with essential fields
    public Payment(int bookingId, String transactionId) {
        this.bookingId = bookingId;
        this.transactionId = transactionId;
        this.paymentMethod = "QR Code";
        this.paymentStatus = "Pending";
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", bookingId=" + bookingId +
                ", transactionId='" + transactionId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}