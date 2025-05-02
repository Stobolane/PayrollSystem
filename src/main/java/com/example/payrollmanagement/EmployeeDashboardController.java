package com.example.payrollmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private StackPane contentPane;

    // Set welcome label text (e.g., "Welcome, John!")
    public void setWelcomeMessage(String username) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username + "!");
        }
    }

    // Logout and return to login screen
    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/payrollmanagement/login.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // My Profile button action
    @FXML
    private void showProfile() {
        showAlert("My Profile", "This is where your profile info will be displayed.");
    }

    // My Payslips button action
    @FXML
    private void showPayslips() {
        showAlert("My Payslips", "Your payslip records will appear here.");
    }

    // Salary Details button action
    @FXML
    private void showSalaryDetails() {
        showAlert("Salary Details", "Your salary details will be shown here.");
    }

    // Utility method to show alerts (placeholder for real content)
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
