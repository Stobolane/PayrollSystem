module com.example.payrollmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.payrollmanagement to javafx.fxml;
    opens com.example.payrollmanagement.models to javafx.fxml;

    exports com.example.payrollmanagement;
    exports com.example.payrollmanagement.models;
}