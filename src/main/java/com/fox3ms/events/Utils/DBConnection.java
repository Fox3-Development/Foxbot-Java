package com.fox3ms.events.Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /*Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TOKEN");*/
    static Dotenv dotenv = Dotenv.load();
    static String DRIVER = dotenv.get("DRIVER");
    static String DB_URL = dotenv.get("DBURL");
    static String DB_USER = dotenv.get("DBUSER");
    static String DB_PASS = dotenv.get("DBPASS");

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!" + ex.getMessage());
            System.exit(1);
        }
        return conn;
    }

}
