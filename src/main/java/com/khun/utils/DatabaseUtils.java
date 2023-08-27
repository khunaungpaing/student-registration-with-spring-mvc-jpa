package com.khun.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

	// JDBC connection URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_registration";
    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "toor";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Register the JDBC driver (optional for newer JDBC versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
