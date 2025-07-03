package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.DarshanBooking;
import dao.JDConnection;
import java.util.List;
import java.util.ArrayList;

public class DarshanBookingDAO {
    private Connection connection;
    
    public DarshanBookingDAO() throws SQLException {
        connection = JDConnection.getConnection();
    }
    
    public void saveBooking(DarshanBooking booking) throws SQLException {
        String sql = "INSERT INTO darshan_bookings (booking_id, darshan_id, devotee_id, " +
                    "booking_date, persons, total_amount, transaction_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, booking.getBookingId());
            stmt.setInt(2, booking.getDarshanId());
            stmt.setInt(3, booking.getDevoteeId());
            stmt.setString(4, booking.getBookingDate());
            stmt.setInt(5, booking.getPersons());
            stmt.setDouble(6, booking.getTotalAmount());
            stmt.setString(7, booking.getTransactionId());
           
            stmt.executeUpdate();
        }
    }
    
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    public DarshanBooking getBookingById(String bookingId) throws SQLException {
        String sql = "SELECT * FROM darshan_bookings WHERE booking_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bookingId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DarshanBooking booking = new DarshanBooking();
                    booking.setBookingId(rs.getString("booking_id"));
                    booking.setDarshanId(rs.getInt("darshan_id"));
                    booking.setDevoteeId(rs.getInt("devotee_id"));
                    booking.setBookingDate(rs.getString("booking_date"));
                    booking.setPersons(rs.getInt("persons"));
                    booking.setTotalAmount(rs.getDouble("total_amount"));
                    booking.setTransactionId(rs.getString("transaction_id"));
                    booking.setStatus(rs.getString("status"));
                    return booking;
                }
            }
        }
        return null;
    }

    public List<DarshanBooking> getBookingsByDevoteeId(int devoteeId) throws SQLException {
        List<DarshanBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM darshan_bookings WHERE devotee_id = ? ORDER BY booking_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, devoteeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DarshanBooking booking = new DarshanBooking();
                    booking.setBookingId(rs.getString("booking_id"));
                    booking.setDarshanId(rs.getInt("darshan_id"));
                    booking.setDevoteeId(rs.getInt("devotee_id"));
                    booking.setBookingDate(rs.getString("booking_date"));
                    booking.setPersons(rs.getInt("persons"));
                    booking.setTotalAmount(rs.getDouble("total_amount"));
                    booking.setTransactionId(rs.getString("transaction_id"));
                    booking.setStatus(rs.getString("status"));
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
} 