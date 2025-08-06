package com.example;

import static spark.Spark.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {
    public static void main(String[] args) {
        // Set port FIRST
        port(7080);
String configPath = System.getProperty("config", "src/main/resources/config.properties");

        // Load DB config
        Properties props = new Properties();
	try (FileInputStream fis = new FileInputStream(configPath)) {
//        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Failed to load config.properties: " + e.getMessage());
            return;
        }

        String jdbcUrl = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // Enable CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type");
        });

        post("/add", (req, res) -> {
            res.type("application/json");

            JsonObject json = JsonParser.parseString(req.body()).getAsJsonObject();
            String name = json.get("name").getAsString();
            String address = json.get("address").getAsString();

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                String insertQuery = "INSERT INTO employee (name, address) VALUES (?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insertQuery);
                stmt.setString(1, name);
                stmt.setString(2, address);
                int rowsInserted = stmt.executeUpdate();

                String message = "Inserted " + rowsInserted + " row(s) successfully.";
                System.out.println(message);
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("status", "success");
                responseJson.addProperty("message", message);
                return responseJson.toString();
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                JsonObject errorJson = new JsonObject();
                errorJson.addProperty("status", "error");
                errorJson.addProperty("message", e.getMessage());
                return errorJson.toString();
            }
        });

        System.out.println("Server started on http://localhost:7080");
    }
}

