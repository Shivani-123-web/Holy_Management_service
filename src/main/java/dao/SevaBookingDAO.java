package dao;

import model.SevaBooking;
import dao.JDConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SevaBookingDAO {
    
    public int createBooking(SevaBooking booking) throws SQLException {
        String sql = "INSERT INTO seva_bookings (seva_id, full_name, email, phone_number, " +
                    "total_amount, booking_date, time_slot, payment_status, booking_status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', 'Pending')";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, booking.getSevaId());
            pstmt.setString(2, booking.getFullName());
            pstmt.setString(3, booking.getEmail());
            pstmt.setString(4, booking.getPhoneNumber());
            pstmt.setBigDecimal(5, booking.getTotalAmount());
            pstmt.setDate(6, booking.getBookingDate());
            pstmt.setString(7, booking.getTimeSlot());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        booking.setBookingId(rs.getInt(1)); // Set the generated booking ID
                        return rs.getInt(1); // Return the generated booking ID
                    }
                }
            }
            return -1; // Return -1 if insertion failed
        }
    }

    public List<SevaBooking> getAllBookings() throws SQLException {
        List<SevaBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM seva_bookings ORDER BY booking_date DESC";
        
        try (Connection conn = JDConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
        return bookings;
    }

    public List<SevaBooking> getBookingsByUser(String phoneNumber) throws SQLException {
        List<SevaBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM seva_bookings WHERE phone_number = ? ORDER BY booking_date DESC";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, phoneNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        }
        return bookings;
    }

    private SevaBooking mapResultSetToBooking(ResultSet rs) throws SQLException {
        SevaBooking booking = new SevaBooking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setSevaId(rs.getInt("seva_id"));
        booking.setFullName(rs.getString("full_name"));
        booking.setEmail(rs.getString("email"));
        booking.setPhoneNumber(rs.getString("phone_number"));
        booking.setTotalAmount(rs.getBigDecimal("total_amount"));
        booking.setBookingDate(rs.getDate("booking_date"));
        booking.setTimeSlot(rs.getString("time_slot"));
        booking.setPaymentStatus(rs.getString("payment_status"));
        booking.setBookingStatus(rs.getString("booking_status"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        return booking;
    }

    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE seva_bookings SET booking_status = ? WHERE booking_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updatePaymentStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE seva_bookings SET payment_status = ? WHERE booking_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    // Add this method to the existing SevaBookingDAO class
public List<SevaBooking> getBookingsByUserId(int userId) throws SQLException {
    List<SevaBooking> bookings = new ArrayList<>();
    String sql = "SELECT * FROM seva_bookings WHERE user_id = ? ORDER BY booking_date DESC";
    
    try (Connection conn = JDConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, userId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
    }
    return bookings;
}

// ... existing code ...

public List<SevaBooking> getBookingsByUserId(int userId) throws SQLException {
    List<SevaBooking> bookings = new ArrayList<>();
    // Get user's phone number first
    String phoneQuery = "SELECT phone FROM users WHERE user_id = ?";
    String userPhone = null;
    
    try (Connection conn = JDConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(phoneQuery)) {
        
        pstmt.setInt(1, userId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                userPhone = rs.getString("phone");
            }
        }
    }
    
    if (userPhone != null) {
        String sql = "SELECT * FROM seva_bookings WHERE phone_number = ? ORDER BY booking_date DESC";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userPhone);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        }
    }
    return bookings;
}
}