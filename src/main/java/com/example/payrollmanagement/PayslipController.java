package com.example.payrollmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.print.PrinterJob;
import javafx.util.StringConverter;

import java.sql.*;

public class PayslipController {

    @FXML private ComboBox<Employee> employeeComboBox;
    @FXML private Label empIdLabel, nameLabel;
    @FXML private Label basicSalaryLabel, workingHoursLabel, overtimePayLabel, deductionsLabel, netSalaryLabel;
    @FXML private TextField overtimeField, deductionsField;

    @FXML
    public void initialize() {
        loadEmployees();
        employeeComboBox.setOnAction(e -> onEmployeeSelected());
    }

    private void loadEmployees() {
        String query = "SELECT id, name, department, position, salary, hours FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            employeeComboBox.setConverter(new StringConverter<>() {
                @Override public String toString(Employee e) { return e == null ? "" : e.getName(); }
                @Override public Employee fromString(String s) { return null; }
            });

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getInt("hours")
                );
                employeeComboBox.getItems().add(emp);
            }

            if (employeeComboBox.getItems().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "No Data", "No employees found in database.");
            }

        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load employees:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void onEmployeeSelected() {
        Employee emp = employeeComboBox.getValue();
        if (emp == null) return;

        empIdLabel.setText(emp.getEmployeeId());
        nameLabel.setText(emp.getName());
        basicSalaryLabel.setText(String.format("%.2f", emp.getSalary()));
        workingHoursLabel.setText(String.valueOf(emp.getWorkingHours()));

        // clear previous inputs
        overtimeField.clear();
        deductionsField.clear();
        overtimePayLabel.setText("");
        deductionsLabel.setText("");
        netSalaryLabel.setText("");
    }

    @FXML
    private void onGeneratePayslip() {
        Employee emp = employeeComboBox.getValue();
        if (emp == null) {
            showAlert(Alert.AlertType.WARNING, "No Employee", "Please select an employee first.");
            return;
        }

        double overtime = parseDoubleField(overtimeField, "Overtime");
        double deductions = parseDoubleField(deductionsField, "Deductions");
        double net = emp.getSalary() + overtime - deductions;

        // update labels
        overtimePayLabel.setText(String.format("%.2f", overtime));
        deductionsLabel.setText(String.format("%.2f", deductions));
        netSalaryLabel.setText(String.format("%.2f", net));

        // save to database
        String sql = """
        INSERT INTO payslips (employee_id, name, department, position, basic_salary, working_hours, net_salary)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getEmployeeId());      // employee_id
            ps.setString(2, emp.getName());            // name
            ps.setString(3, emp.getDepartment());      // department
            ps.setString(4, emp.getPosition());        // position
            ps.setDouble(5, emp.getSalary());          // basic_salary
            ps.setInt(6, emp.getWorkingHours());       // working_hours
            ps.setDouble(7, net);                      // net_salary (calculated)

            ps.executeUpdate(); // Insert the payslip into the database

            showAlert(Alert.AlertType.INFORMATION, "Saved", "Payslip saved for " + emp.getName());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Save Failed", ex.getMessage());
            ex.printStackTrace();
        }
    }


    @FXML
    private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            if (job.printPage(employeeComboBox.getScene().getRoot())) {
                job.endJob();
            }
        }
    }

    private double parseDoubleField(TextField field, String name) {
        try {
            return Double.parseDouble(field.getText());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid " + name, name + " must be a number.");
            throw e;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}
