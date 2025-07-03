package dao;

import model.Prasadam;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrasadamDAO {
    private Connection connection;

    public PrasadamDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addPrasadam(Prasadam prasadam) throws SQLException {
        String sql = "INSERT INTO prasadam_table (name, description, type, available_days, special_occasion, " +
                    "quantity_available, admin_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, prasadam.getName());
            pstmt.setString(2, prasadam.getDescription());
            pstmt.setString(3, prasadam.getType());
            pstmt.setString(4, prasadam.getAvailableDays());
            pstmt.setString(5, prasadam.getSpecialOccasion());
            pstmt.setInt(6, prasadam.getQuantityAvailable());
            pstmt.setInt(7, prasadam.getAdminId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Prasadam> getAllPrasadam() throws SQLException {
        List<Prasadam> prasadamList = new ArrayList<>();
        String sql = "SELECT * FROM prasadam_table";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prasadamList.add(mapResultSetToPrasadam(rs));
            }
        }
        return prasadamList;
    }

    public Prasadam getPrasadamById(int prasadamId) throws SQLException {
        String sql = "SELECT * FROM prasadam_table WHERE prasadam_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, prasadamId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPrasadam(rs);
            }
        }
        return null;
    }

    public boolean updatePrasadam(Prasadam prasadam) throws SQLException {
        String sql = "UPDATE prasadam_table SET name=?, description=?, type=?, available_days=?, " +
                    "special_occasion=?, quantity_available=? WHERE prasadam_id=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, prasadam.getName());
            pstmt.setString(2, prasadam.getDescription());
            pstmt.setString(3, prasadam.getType());
            pstmt.setString(4, prasadam.getAvailableDays());
            pstmt.setString(5, prasadam.getSpecialOccasion());
            pstmt.setInt(6, prasadam.getQuantityAvailable());
            pstmt.setInt(7, prasadam.getPrasadamId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deletePrasadam(int prasadamId) throws SQLException {
        String sql = "DELETE FROM prasadam_table WHERE prasadam_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, prasadamId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Prasadam mapResultSetToPrasadam(ResultSet rs) throws SQLException {
        Prasadam prasadam = new Prasadam();
        prasadam.setPrasadamId(rs.getInt("prasadam_id"));
        prasadam.setName(rs.getString("name"));
        prasadam.setDescription(rs.getString("description"));
        prasadam.setType(rs.getString("type"));
        prasadam.setAvailableDays(rs.getString("available_days"));
        prasadam.setSpecialOccasion(rs.getString("special_occasion"));
        prasadam.setQuantityAvailable(rs.getInt("quantity_available"));
        prasadam.setAdminId(rs.getInt("admin_id"));
        prasadam.setCreatedAt(rs.getTimestamp("created_at"));
        prasadam.setUpdatedAt(rs.getTimestamp("updated_at"));
        return prasadam;
    }
}