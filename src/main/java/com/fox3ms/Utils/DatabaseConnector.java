package com.fox3ms.Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.ddtek.jdbc.openedge.OpenEdgeDriver;

public class DatabaseConnector {
    private Connection connection;
    Dotenv dotenv = Dotenv.load();
    String URL = dotenv.get("URL");
    String DBUSER = dotenv.get("DBUSER");
    String DBPASS = dotenv.get("DBPASS");


    public DatabaseConnector() {

        try {
            Class.forName("com.ddtek.jdbc.openedge.OpenEdgeDriver");
            connection = DriverManager.getConnection(URL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/*
        Dotenv dotenv = Dotenv.load();
        String URL = dotenv.get("URL");
        String DBUSER = dotenv.get("DBUSER");
        String DBPASS = dotenv.get("DBPASS");
 */