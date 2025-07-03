package dao;

import java.sql.*;
import java.util.*;
import model.Darshan;
import dao.JDConnection;

public class DarshanDAO {
    private Connection connection;

    public DarshanDAO() throws SQLException {
        connection = JDConnection.getConnection();
    }

    // Method to reset auto-increment
    private void resetAutoIncrement() throws SQLException {
        String query = "ALTER TABLE darshans AUTO_INCREMENT = 1";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        }
    }

    // Method to update IDs sequentially
    private void updateIds() throws SQLException {
        String query = "SET @count = 0";
        String updateQuery = "UPDATE darshans SET darshan_id = @count:= @count + 1 ORDER BY darshan_id";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            stmt.execute(updateQuery);
            resetAutoIncrement();
        }
    }

    public List<Darshan> getAllDarshans() throws SQLException {
        List<Darshan> darshans = new ArrayList<>();
        String sql = "SELECT * FROM darshans";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while (rs.next()) {
                Darshan darshan = new Darshan();
                darshan.setDarshan_id(rs.getInt("darshan_id"));
                darshan.setDarshan_type(rs.getString("darshan_type"));
                darshan.setTime_slot(rs.getString("time_slot"));
                darshan.setPrice(rs.getDouble("price"));
                darshan.setMax_persons(rs.getInt("max_persons"));
                darshan.setDescription(rs.getString("description"));
                darshan.setStatus(rs.getString("status"));
                darshans.add(darshan);
            }
        }
        return darshans;
    }

    public Darshan getDarshanById(int darshan_id) throws SQLException {
        String sql = "SELECT * FROM darshans WHERE darshan_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, darshan_id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Darshan darshan = new Darshan();
                    darshan.setDarshan_id(rs.getInt("darshan_id"));
                    darshan.setDarshan_type(rs.getString("darshan_type"));
                    darshan.setTime_slot(rs.getString("time_slot"));
                    darshan.setPrice(rs.getDouble("price"));
                    darshan.setMax_persons(rs.getInt("max_persons"));
                    darshan.setDescription(rs.getString("description"));
                    darshan.setStatus(rs.getString("status"));
                    return darshan;
                }
            }
        }
        return null;
    }

    public boolean bookDarshan(int darshan_id, int user_id, String bookingDate, int persons) throws SQLException {
        PreparedStatement pst = null;
        try {
            String query = "INSERT INTO darshan_bookings (darshan_id, devotee_id, booking_date, persons, status) VALUES (?, ?, ?, ?, 'PENDING')";
            pst = connection.prepareStatement(query);
            pst.setInt(1, darshan_id);
            pst.setInt(2, user_id);
            pst.setString(3, bookingDate);
            pst.setInt(4, persons);
            
            int result = pst.executeUpdate();
            return result > 0;
        } finally {
            if (pst != null) pst.close();
        }
    }

    public boolean addDarshan(Darshan darshan) throws SQLException {
        String query = "INSERT INTO darshans (darshan_type, description, time_slot, price, max_persons, status) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, darshan.getDarshan_type());
            pst.setString(2, darshan.getDescription());
            pst.setString(3, darshan.getTime_slot());
            pst.setDouble(4, darshan.getPrice());
            pst.setInt(5, darshan.getMax_persons());
            pst.setString(6, darshan.getStatus());
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        darshan.setDarshan_id(rs.getInt(1));
                    }
                }
                updateIds(); // Update IDs after insertion
                return true;
            }
            return false;
        }
    }

    public boolean updateDarshan(Darshan darshan) throws SQLException {
        String query = "UPDATE darshans SET darshan_type=?, description=?, time_slot=?, " +
                      "price=?, max_persons=?, status=? WHERE darshan_id=?";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, darshan.getDarshan_type());
            pst.setString(2, darshan.getDescription());
            pst.setString(3, darshan.getTime_slot());
            pst.setDouble(4, darshan.getPrice());
            pst.setInt(5, darshan.getMax_persons());
            pst.setString(6, darshan.getStatus());
            pst.setInt(7, darshan.getDarshan_id());
            
            int result = pst.executeUpdate();
            if (result > 0) {
                updateIds(); // Update IDs after modification
                return true;
            }
            return false;
        }
    }

    public boolean deleteDarshan(int darshan_id) throws SQLException {
        String query = "DELETE FROM darshans WHERE darshan_id = ?";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, darshan_id);
            int result = pst.executeUpdate();
            
            if (result > 0) {
                updateIds(); // Update IDs after deletion
                return true;
            }
            return false;
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
} 