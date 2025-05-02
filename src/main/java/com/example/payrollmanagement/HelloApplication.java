package com.example.payrollmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/payrollmanagement/Home.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Payroll Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Add these VM options programmatically
        System.setProperty("javafx.macosx.embedded", "true");
        System.setProperty("prism.allowhidpi", "false");

        launch();
    }
}