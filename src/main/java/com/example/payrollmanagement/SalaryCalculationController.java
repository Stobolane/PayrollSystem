package com.example.payrollmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalaryCalculationController {

    @FXML private ComboBox<Employee> employeeComboBox;
    @FXML private Label basicSalaryLabel;
    @FXML private Label workingHoursLabel;
    @FXML private TextField overtimeField;
    @FXML private TextField deductionsField;
    @FXML private Label netSalaryLabel;

    private double currentSalary = 0;
    private int currentWorkingHours = 0;

    @FXML
    public void initialize() {
        loadEmployees();
    }

    private void loadEmployees() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String sql = "SELECT id, name, department, position, salary, hours FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getInt("hours")            // use `hours` not `working_hours`
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "DB error: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }

        employeeComboBox.setItems(list);
        employeeComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Employee e) {
                return e == null ? "" : e.getEmployeeId() + " – " + e.getName();
            }
            @Override public Employee fromString(String s) { return null; }
        });
    }

    @FXML
    private void handleSelection(ActionEvent ev) {
        Employee e = employeeComboBox.getValue();
        if (e == null) return;

        currentSalary = e.getSalary();
        currentWorkingHours = e.getWorkingHours();

        basicSalaryLabel.setText(String.format("%.2f", currentSalary));
        workingHoursLabel.setText(String.valueOf(currentWorkingHours));
        netSalaryLabel.setText("");
    }

    @FXML
    private void handleCalculateSalary(ActionEvent ev) {
        try {
            double overtime = Double.parseDouble(overtimeField.getText());
            double deductions = Double.parseDouble(deductionsField.getText());
            double net = currentSalary + overtime - deductions;
            netSalaryLabel.setText(String.format("%.2f", net));
        } catch (NumberFormatException ex) {
            netSalaryLabel.setText("Invalid input");
        }
    }
}