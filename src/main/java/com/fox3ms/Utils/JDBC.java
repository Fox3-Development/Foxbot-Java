package com.fox3ms.Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private Connection conn;

    public void DatabaseConnector() {
        Dotenv dotenv = Dotenv.load();
        String URL = dotenv.get("URL");
        String DBUSER = dotenv.get("DBUSER");
        String DBPASS = dotenv.get("DBPASS");
        try {
            conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
