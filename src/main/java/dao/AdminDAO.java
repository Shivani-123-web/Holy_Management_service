package dao;

import model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin_table (username, password, full_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getFullName());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getPhone());
            return pstmt.executeUpdate() > 0;
        }
    }

    public Admin getAdminByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM admin_table WHERE username = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setFullName(rs.getString("full_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPhone(rs.getString("phone"));
                admin.setCreatedAt(rs.getTimestamp("created_at"));
                admin.setLastLogin(rs.getTimestamp("last_login"));
                return admin;
            }
        }
        return null;
    }

    public boolean updateLastLogin(int adminId) throws SQLException {
        String sql = "UPDATE admin_table SET last_login = CURRENT_TIMESTAMP WHERE admin_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, adminId);
            return pstmt.executeUpdate() > 0;
        }
    }
}