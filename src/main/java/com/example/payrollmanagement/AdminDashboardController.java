package com.example.payrollmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label totalUsersLabel;
    @FXML private Label pendingRequestsLabel;
    @FXML private TableView<Activity> homeActivityTable;
    @FXML private TableColumn<Activity, String> activityColumn;
    @FXML private StackPane contentArea;
    @FXML private VBox homeContent;
    @FXML private VBox payslipContent;
    @FXML private VBox salaryCalcContent;


    @FXML
    private void showHome(ActionEvent event) {
        try {
            loadContent("AdminHome.fxml");
        } catch (IOException e) {
            showErrorAlert("Error Loading Home", "Could not load HomeContent.fxml", e);
        }
    }

    @FXML
    private void showDashboard(ActionEvent event) {
        try {
            loadContent("Dashboard.fxml");
        } catch (IOException e) {
            showErrorAlert("Error Loading Dashboard", "Could not load Dashboard.fxml", e);
        }
    }


    @FXML
    private void showEmployeeManagement(ActionEvent event) {
        try {
            loadContent("EmployeeManagement.fxml");
        } catch (IOException e) {
            showErrorAlert("Error Loading Employee Management", "Could not load EmployeeManagement.fxml", e);
        }
    }

    @FXML
    private void showPayroll(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PayrollReports.fxml"));
            Parent payrollView = loader.load();
            contentArea.getChildren().setAll(payrollView);
        } catch (IOException e) {
            showErrorAlert("Load Error", "Could not load PayrollReports.fxml", e);
        }
    }

    @FXML
    private void showPayslipGeneration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payslip.fxml"));
            Parent payslipView = loader.load();
            contentArea.getChildren().setAll(payslipView);
        } catch (IOException e) {
            showErrorAlert("Error Loading Payslip", "Could not load Payslip.fxml", e);
        }
    }

    @FXML
    private void showSalaryCalculation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SalaryCalculation.fxml"));
            Parent salaryView = loader.load();
            contentArea.getChildren().setAll(salaryView);
        } catch (IOException e) {
            showErrorAlert("Error Loading Salary Calculator", "Could not load SalaryCalculation.fxml", e);
        }
    }

    @FXML
    private void handleReportsButtonClick(ActionEvent event) {
        openReportsWindow();
    }

    // ✅ Proper Reports window opening using VBox layout
    private void openReportsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reports.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Payroll Reports");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Failed to Load Reports", "Could not open Reports.fxml", e);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Logout clicked");
    }

    @FXML
    private void handleGeneratePayslip(ActionEvent event) {
        showPayslipGeneration();
    }

    @FXML
    private void handleCalculateSalary(ActionEvent event) {
        showSalaryCalculation(event);
    }

    private void loadContent(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Node view = loader.load();
        contentArea.getChildren().setAll(view);
    }

    private void showErrorAlert(String title, String header, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        e.printStackTrace();
    }

    private Employee fetchEmployeeFromDatabase(String employeeId) {
        Employee emp = null;
        String query = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String department = rs.getString("department");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");
                int hours = rs.getInt("hours");

                emp = new Employee(employeeId, name, department, position, salary, hours);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;
    }

    public static class Activity {
        private String activity;

        public Activity(String activity) {
            this.activity = activity;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }
    }
}
