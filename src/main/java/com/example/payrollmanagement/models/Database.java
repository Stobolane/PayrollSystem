package com.example.payrollmanagement.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:payroll.db";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connection to SQLite has been established.");
                initializeDatabase();
            } catch (SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL CHECK (role IN ('ADMIN', 'EMPLOYEE'))" +
                ");";

        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
                "employee_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "department TEXT NOT NULL," +
                "position TEXT NOT NULL," +
                "basic_salary REAL NOT NULL," +
                "working_hours INTEGER NOT NULL," +
                "user_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";

        String createPayrollTable = "CREATE TABLE IF NOT EXISTS payroll (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "employee_id INTEGER NOT NULL," +
                "month INTEGER NOT NULL," +
                "year INTEGER NOT NULL," +
                "gross_salary REAL NOT NULL," +
                "tax_deduction REAL NOT NULL," +
                "insurance_deduction REAL NOT NULL," +
                "other_deductions REAL NOT NULL," +
                "net_salary REAL NOT NULL," +
                "FOREIGN KEY (employee_id) REFERENCES employees(employee_id)" +
                ");";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createEmployeesTable);
            stmt.execute(createPayrollTable);

            // Insert default admin user if not exists
            stmt.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES " +
                    "('admin', 'admin123', 'ADMIN')");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}