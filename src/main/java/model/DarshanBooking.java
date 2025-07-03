package model;

public class DarshanBooking {
    private String bookingId;
    private int darshanId;
    private int devoteeId;
    private String bookingDate;
    private int persons;
    private double totalAmount;   // Only total_amount field
    private String transactionId;
    
    // Getters and Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public int getDarshanId() { return darshanId; }
    public void setDarshanId(int darshanId) { this.darshanId = darshanId; }
    
    public int getDevoteeId() { return devoteeId; }
    public void setDevoteeId(int devoteeId) { this.devoteeId = devoteeId; }
    
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    
    public int getPersons() { return persons; }
    public void setPersons(int persons) { this.persons = persons; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
} 