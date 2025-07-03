package dao;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    
    public EventDAO() {
        // Default constructor
    }

    public List<Event> getAllActiveEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        // Removed the is_active condition since the column doesn't exist
        String sql = "SELECT * FROM event_table ORDER BY event_name";
        
        try (Connection conn = JDConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setEventName(rs.getString("event_name"));
                event.setPrice(rs.getBigDecimal("price"));
                events.add(event);
            }
        }
        return events;
    }

    public Event getEventById(int eventId) throws SQLException {
        String sql = "SELECT * FROM event_table WHERE event_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setEventId(rs.getInt("event_id"));
                    event.setEventName(rs.getString("event_name"));
                    event.setPrice(rs.getBigDecimal("price"));
                    return event;
                }
            }
        }
        return null;
    }

    public boolean addEvent(Event event) throws SQLException {
        String sql = "INSERT INTO event_table (event_name, price) VALUES (?, ?)";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getEventName());
            stmt.setBigDecimal(2, event.getPrice());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        event.setEventId(rs.getInt(1));
                    }
                }
            }
            return affectedRows > 0;
        }
    }

    public boolean updateEvent(Event event) throws SQLException {
        String sql = "UPDATE event_table SET event_name = ?, price = ? WHERE event_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, event.getEventName());
            stmt.setBigDecimal(2, event.getPrice());
            stmt.setInt(3, event.getEventId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean hasActiveBookings(int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM event_bookings WHERE event_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean deleteEvent(int eventId) throws SQLException {
        // First check if there are any active bookings
        if (hasActiveBookings(eventId)) {
            throw new SQLException("Cannot delete event with active bookings");
        }

        String sql = "DELETE FROM event_table WHERE event_id = ?";
        
        try (Connection conn = JDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}