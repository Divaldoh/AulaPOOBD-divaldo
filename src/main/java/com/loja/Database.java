package com.loja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/LOJA";
    private static final String USER = "divaldoh";
    private static final String PASSWORD = "123456";

    private Database() {}


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
