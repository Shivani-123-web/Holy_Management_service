package dao;

import model.TempleHistory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TempleHistoryDAO {
    private Connection connection;

    public TempleHistoryDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addHistory(TempleHistory history) throws SQLException {
        String sql = "INSERT INTO temple_history_table (title, content, historical_period, significance, " +
                    "image_url, admin_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, history.getTitle());
            pstmt.setString(2, history.getContent());
            pstmt.setString(3, history.getHistoricalPeriod());
            pstmt.setString(4, history.getSignificance());
            pstmt.setString(5, history.getImageUrl());
            pstmt.setInt(6, history.getAdminId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<TempleHistory> getAllHistory() throws SQLException {
        List<TempleHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM temple_history_table ORDER BY historical_period";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                historyList.add(mapResultSetToHistory(rs));
            }
        }
        return historyList;
    }

    public TempleHistory getHistoryById(int historyId) throws SQLException {
        String sql = "SELECT * FROM temple_history_table WHERE history_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, historyId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToHistory(rs);
            }
        }
        return null;
    }

    public boolean updateHistory(TempleHistory history) throws SQLException {
        String sql = "UPDATE temple_history_table SET title=?, content=?, historical_period=?, " +
                    "significance=?, image_url=? WHERE history_id=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, history.getTitle());
            pstmt.setString(2, history.getContent());
            pstmt.setString(3, history.getHistoricalPeriod());
            pstmt.setString(4, history.getSignificance());
            pstmt.setString(5, history.getImageUrl());
            pstmt.setInt(6, history.getHistoryId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteHistory(int historyId) throws SQLException {
        String sql = "DELETE FROM temple_history_table WHERE history_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, historyId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private TempleHistory mapResultSetToHistory(ResultSet rs) throws SQLException {
        TempleHistory history = new TempleHistory();
        history.setHistoryId(rs.getInt("history_id"));
        history.setTitle(rs.getString("title"));
        history.setContent(rs.getString("content"));
        history.setHistoricalPeriod(rs.getString("historical_period"));
        history.setSignificance(rs.getString("significance"));
        history.setImageUrl(rs.getString("image_url"));
        history.setAdminId(rs.getInt("admin_id"));
        history.setCreatedAt(rs.getTimestamp("created_at"));
        history.setUpdatedAt(rs.getTimestamp("updated_at"));
        return history;
    }
}
