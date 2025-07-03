package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.EventBooking;

public class EventBookingDAO {
    
    public int createBooking(EventBooking booking) throws SQLException {
        // Validate phone number format
        if (booking.getPhoneNumber() == null || !booking.getPhoneNumber().matches("\\d{10}")) {
            throw new SQLException("Invalid phone number format");
        }
        
        String sql = "INSERT INTO event_bookings (event_id, booking_date, total_amount, payment_id, " +
                    "full_name, email, phone_number, payment_status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, booking.getEventId());
            stmt.setDate(2, booking.getBookingDate());
            stmt.setBigDecimal(3, booking.getAmount());
            stmt.setString(4, booking.getPaymentId());
            stmt.setString(5, booking.getFullName());
            stmt.setString(6, booking.getEmail());
            stmt.setString(7, booking.getPhoneNumber());
            stmt.setString(8, "PENDING"); // Default payment status
            stmt.setInt(9, booking.getUserId()); // Add user_id
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        }
    }

    public EventBooking getBookingWithEventDetails(int bookingId) throws SQLException {
        String sql = "SELECT eb.*, et.event_name FROM event_bookings eb " +
                    "JOIN event_table et ON eb.event_id = et.event_id " +
                    "WHERE eb.booking_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EventBooking booking = new EventBooking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setEventId(rs.getInt("event_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setAmount(rs.getBigDecimal("total_amount"));
                    booking.setPaymentId(rs.getString("payment_id"));
                    booking.setFullName(rs.getString("full_name"));
                    booking.setEmail(rs.getString("email"));
                    booking.setPhoneNumber(rs.getString("phone_number"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    booking.setEventName(rs.getString("event_name"));
                    return booking;
                }
            }
        }
        return null;
    }

    public boolean updatePaymentStatus(int bookingId, String paymentStatus) throws SQLException {
        String sql = "UPDATE event_bookings SET payment_status = ? WHERE booking_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, paymentStatus);
            stmt.setInt(2, bookingId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public EventBooking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM event_bookings WHERE booking_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EventBooking booking = new EventBooking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setEventId(rs.getInt("event_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setAmount(rs.getBigDecimal("total_amount"));
                    booking.setPaymentId(rs.getString("payment_id"));
                    booking.setFullName(rs.getString("full_name"));
                    booking.setEmail(rs.getString("email"));
                    booking.setPhoneNumber(rs.getString("phone_number"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    return booking;
                }
            }
        }
        return null;
    }

    public List<EventBooking> getBookingsByUserId(int userId) throws SQLException {
        List<EventBooking> bookings = new ArrayList<>();
        String sql = "SELECT eb.*, et.event_name FROM event_bookings eb " +
                    "JOIN event_table et ON eb.event_id = et.event_id " +
                    "WHERE eb.user_id = ? " +
                    "ORDER BY eb.booking_date DESC";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EventBooking booking = new EventBooking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setEventId(rs.getInt("event_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setAmount(rs.getBigDecimal("total_amount"));
                    booking.setPaymentId(rs.getString("payment_id"));
                    booking.setFullName(rs.getString("full_name"));
                    booking.setEmail(rs.getString("email"));
                    booking.setPhoneNumber(rs.getString("phone_number"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    booking.setEventName(rs.getString("event_name"));
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
}






