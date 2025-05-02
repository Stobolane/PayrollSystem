package com.example.payrollmanagement;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import java.sql.*;

public class ReportsController {

    @FXML private StackPane chartContainer;

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Pie Chart: Show how many employees (by name)
    public void showAvailableEmployeesReport() {
        PieChart pieChart = new PieChart();
        String query = "SELECT name FROM employees";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            int total = 0;
            while (rs.next()) {
                total++;
            }
            pieChart.getData().add(new PieChart.Data("Total Employees", total));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pieChart.setTitle("Total Number of Employees");
        chartContainer.getChildren().setAll(pieChart);
    }

    // Bar Chart: Show Net Salary from payslips by employee
    public void showRevenueReport() {
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Net Salary");

        String query = "SELECT name, SUM(net_salary) AS total_salary FROM payslips GROUP BY name";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("name"), rs.getDouble("total_salary")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        barChart.setTitle("Net Salary by Employee");
        barChart.getData().add(series);
        chartContainer.getChildren().setAll(barChart);
    }

    // Line Chart: Show working hours over time
    public void showWorkingHoursReport() {
        LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Working Hours");

        String query = "SELECT name, working_hours FROM payslips ORDER BY generated_at DESC LIMIT 10";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("name"), rs.getInt("working_hours")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lineChart.setTitle("Working Hours (Recent 10 Payslips)");
        lineChart.getData().add(series);
        chartContainer.getChildren().setAll(lineChart);
    }
}
