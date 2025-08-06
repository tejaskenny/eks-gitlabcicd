package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        String configPath = System.getProperty("config", "src/main/resources/config.properties");

        // Load DB config
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Failed to load config.properties: " + e.getMessage());
            return;
        }

        String jdbcUrl = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // Truncate the employee table
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String truncateQuery = "TRUNCATE TABLE employee";
            PreparedStatement stmt = connection.prepareStatement(truncateQuery);
            stmt.executeUpdate();
            System.out.println("Employee table truncated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to truncate employee table: " + e.getMessage());
            return;
        }

        System.out.println("Done. Exiting.");
    }
}
