package com.example.payrollmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeManagementController {

    @FXML private TextField employeeIdField;
    @FXML private TextField nameField;
    @FXML private TextField departmentField;
    @FXML private TextField positionField;
    @FXML private TextField salaryField;
    @FXML private TextField hoursField;

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> departmentColumn;
    @FXML private TableColumn<Employee, String> positionColumn;
    @FXML private TableColumn<Employee, Double> salaryColumn;
    @FXML private TableColumn<Employee, Integer> hoursColumn;

    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        employeeList = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        hoursColumn.setCellValueFactory(cellData -> cellData.getValue().workingHoursProperty().asObject());

        employeeTable.setItems(employeeList);

        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> populateForm(newSelection));
    }

    private void populateForm(Employee employee) {
        if (employee != null) {
            employeeIdField.setText(employee.getEmployeeId());
            nameField.setText(employee.getName());
            departmentField.setText(employee.getDepartment());
            positionField.setText(employee.getPosition());
            salaryField.setText(String.valueOf(employee.getSalary()));
            hoursField.setText(String.valueOf(employee.getWorkingHours()));
        }
    }

    @FXML
    private void handleAdd() {
        Employee emp = getEmployeeFromForm();
        if (emp != null) {
            // Add to table list
            employeeList.add(emp);

            // Insert into DB
            try (Connection conn = DBUtil.getConnection()) {
                String sql = "INSERT INTO employees (id, name, department, position, salary, hours) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, emp.getEmployeeId());
                stmt.setString(2, emp.getName());
                stmt.setString(3, emp.getDepartment());
                stmt.setString(4, emp.getPosition());
                stmt.setDouble(5, emp.getSalary());
                stmt.setInt(6, emp.getWorkingHours());
                stmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Employee added to database.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error saving to database: " + e.getMessage());
            }

            clearForm();
        }
    }

    @FXML
    private void handleUpdate() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Employee updatedEmp = getEmployeeFromForm();
            if (updatedEmp != null) {
                int index = employeeList.indexOf(selected);
                employeeList.set(index, updatedEmp);
                clearForm();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeList.remove(selected);
            clearForm();
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
        employeeTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        System.out.println("Back button pressed");
    }

    private Employee getEmployeeFromForm() {
        try {
            String id = employeeIdField.getText();
            String name = nameField.getText();
            String dept = departmentField.getText();
            String pos = positionField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            int hours = Integer.parseInt(hoursField.getText());

            return new Employee(id, name, dept, pos, salary, hours);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage());
            return null;
        }
    }

    private void clearForm() {
        employeeIdField.clear();
        nameField.clear();
        departmentField.clear();
        positionField.clear();
        salaryField.clear();
        hoursField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}