package dao;

import model.Seva;
import dao.JDConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class SevaDAO {
    private Connection connection;

    public SevaDAO() throws SQLException {
        try {
            connection = JDConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }

    // Get all sevas
    public List<Seva> getAllSevas() {
        List<Seva> sevaList = new ArrayList<>();
        String query = "SELECT * FROM seva_table";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Seva seva = new Seva();
                seva.setSevaId(rs.getInt("seva_id"));
                seva.setSevaName(rs.getString("seva_name"));
                seva.setTiming(rs.getString("timing"));
                seva.setPrice(rs.getBigDecimal("price"));
                sevaList.add(seva);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving sevas: " + e.getMessage());
            e.printStackTrace();
        }
        return sevaList;
    }

    // Get seva by ID
    public Seva getSevaById(int sevaId) throws SQLException {
        String query = "SELECT * FROM seva_table WHERE seva_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, sevaId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Seva seva = new Seva();
                seva.setSevaId(rs.getInt("seva_id"));
                seva.setSevaName(rs.getString("seva_name"));
                seva.setTiming(rs.getString("timing"));
                seva.setPrice(rs.getBigDecimal("price"));
                return seva;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving seva by ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Create new seva
    public boolean addSeva(Seva seva) {
        String query = "INSERT INTO seva_table (seva_name, timing, price) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seva.getSevaName());
            ps.setString(2, seva.getTiming());
            ps.setBigDecimal(3, seva.getPrice());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    seva.setSevaId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding seva: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Update seva
    public boolean updateSeva(Seva seva) {
        String query = "UPDATE seva_table SET seva_name=?, timing=?, price=? WHERE seva_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, seva.getSevaName());
            ps.setString(2, seva.getTiming());
            ps.setBigDecimal(3, seva.getPrice());
            ps.setInt(4, seva.getSevaId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating seva: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Delete seva
    public boolean deleteSeva(int sevaId) throws SQLException {
        String sql = "DELETE FROM seva_table WHERE seva_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sevaId);
            
            // Add debug logging
            System.out.println("Executing delete query for seva_id: " + sevaId);
            
            int rowsAffected = stmt.executeUpdate();
            
            // Add debug logging
            System.out.println("Rows affected by delete: " + rowsAffected);
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting seva: " + e.getMessage());
            throw e;
        }
    }

    // Get seva by booking ID
    public Seva getSevaByBookingId(int bookingId) throws SQLException {
        String query = "SELECT s.* FROM seva_table s " +
                      "JOIN seva_bookings sb ON s.seva_id = sb.seva_id " +
                      "WHERE sb.booking_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Seva seva = new Seva();
                seva.setSevaId(rs.getInt("seva_id"));
                seva.setSevaName(rs.getString("seva_name"));
                seva.setPrice(rs.getBigDecimal("price"));
                seva.setTiming(rs.getString("timing"));
                return seva;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving seva by booking ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}