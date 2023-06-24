package com.fox3ms.Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private Connection connection;
    Dotenv dotenv = Dotenv.load();
    String URL = dotenv.get("URL");
    String DBUSER = dotenv.get("DBUSER");
    String DBPASS = dotenv.get("DBPASS");


    public DatabaseConnector() {
        try {
            Class.forName("com.ddtek.jdbc.openedge.OpenEdgeDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("OpenEdge JDBC Driver not found!");
            e.printStackTrace();
            return;
        }
        System.out.println("OpenEdge JDBC Driver Registered!");

        try {
            connection = DriverManager.getConnection(URL, DBUSER, DBPASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console!");
            e.printStackTrace();
            return;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}