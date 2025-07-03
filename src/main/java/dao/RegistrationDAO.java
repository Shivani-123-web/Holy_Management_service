package dao;

import model.Registration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {
    private Connection connection;

    public RegistrationDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addRegistration(Registration registration) throws SQLException {
        String sql = "INSERT INTO registration_table (user_id, event_id, seva_id, registration_date, preferred_date, " +
                    "number_of_people, status, payment_status, payment_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, registration.getUserId());
            if (registration.getEventId() != null) {
                pstmt.setInt(2, registration.getEventId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            if (registration.getSevaId() != null) {
                pstmt.setInt(3, registration.getSevaId());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            pstmt.setDate(4, registration.getRegistrationDate());
            pstmt.setDate(5, registration.getPreferredDate());
            pstmt.setInt(6, registration.getNumberOfPeople());
            pstmt.setString(7, registration.getStatus());
            pstmt.setString(8, registration.getPaymentStatus());
            pstmt.setBigDecimal(9, registration.getPaymentAmount());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Registration> getUserRegistrations(int userId) throws SQLException {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registration_table WHERE user_id = ? ORDER BY registration_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                registrations.add(mapResultSetToRegistration(rs));
            }
        }
        return registrations;
    }

    public List<Registration> getEventRegistrations(int eventId) throws SQLException {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registration_table WHERE event_id = ? ORDER BY registration_date";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                registrations.add(mapResultSetToRegistration(rs));
            }
        }
        return registrations;
    }

    public List<Registration> getSevaRegistrations(int sevaId) throws SQLException {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registration_table WHERE seva_id = ? ORDER BY registration_date";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sevaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                registrations.add(mapResultSetToRegistration(rs));
            }
        }
        return registrations;
    }

    public boolean updateRegistrationStatus(int registrationId, String status) throws SQLException {
        String sql = "UPDATE registration_table SET status = ? WHERE registration_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, registrationId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updatePaymentStatus(int registrationId, String paymentStatus) throws SQLException {
        String sql = "UPDATE registration_table SET payment_status = ? WHERE registration_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paymentStatus);
            pstmt.setInt(2, registrationId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Registration mapResultSetToRegistration(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        registration.setRegistrationId(rs.getInt("registration_id"));
        registration.setUserId(rs.getInt("user_id"));
        registration.setEventId(rs.getInt("event_id"));
        registration.setSevaId(rs.getInt("seva_id"));
        registration.setRegistrationDate(rs.getDate("registration_date"));
        registration.setPreferredDate(rs.getDate("preferred_date"));
        registration.setNumberOfPeople(rs.getInt("number_of_people"));
        registration.setStatus(rs.getString("status"));
        registration.setPaymentStatus(rs.getString("payment_status"));
        registration.setPaymentAmount(rs.getBigDecimal("payment_amount"));
        registration.setCreatedAt(rs.getTimestamp("created_at"));
        return registration;
    }
}