package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDConnection {
    // Update these values according to your MySQL installation
    private static final String URL = "jdbc:mysql://localhost:3306/temple_managment?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root"; // Replace with your actual password

    public static Connection getConnection() throws SQLException {
        try {
            // Print connection details (remove in production)
            System.out.println("Attempting to connect to database with:");
            System.out.println("URL: " + URL);
            System.out.println("Username: " + USERNAME);
            
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
            
            // Attempt connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("Database connected successfully!");
                // Verify database selection
                try (java.sql.Statement stmt = conn.createStatement()) {
                    java.sql.ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
                    if (rs.next()) {
                        System.out.println("Connected to database: " + rs.getString(1));
                    }
                }
            }
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            System.err.println("Database Connection Error:");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully");
            } catch (SQLException e) {
                System.err.println("Error closing connection:");
                e.printStackTrace();
            }
        }
    }
}