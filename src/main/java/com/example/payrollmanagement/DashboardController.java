package com.example.payrollmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {

    @FXML private TableView<AdminDashboardController.Activity> activityTable;
    @FXML private TableColumn<AdminDashboardController.Activity, String> activityColumn;

    @FXML
    public void initialize() {
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

        ObservableList<AdminDashboardController.Activity> activities = FXCollections.observableArrayList(
                new AdminDashboardController.Activity("User John Doe registered"),
                new AdminDashboardController.Activity("Payroll report generated"),
                new AdminDashboardController.Activity("Request Pending Approval")
        );

        activityTable.setItems(activities);
    }
}
