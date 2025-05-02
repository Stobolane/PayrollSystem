package com.example.payrollmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/payroll_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Tebohose1234@"; // update if your MySQL has a password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}