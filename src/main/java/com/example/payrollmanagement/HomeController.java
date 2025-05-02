package com.example.payrollmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class HomeController {

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            // Load login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/payrollmanagement/login.fxml"));
            Parent loginRoot = loader.load();

            // Get current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set new scene
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("Employee Login"); // Optional: Set window title
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // You can show an error alert here if needed
        }
    }
}