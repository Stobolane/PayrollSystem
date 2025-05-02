package com.example.payrollmanagement;

import javafx.beans.property.*;

public class Employee {
    private StringProperty employeeId;
    private StringProperty name;
    private StringProperty department;
    private StringProperty position;
    private DoubleProperty salary;
    private IntegerProperty workingHours;

    public Employee(String employeeId, String name, String department, String position, double salary, int workingHours) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.position = new SimpleStringProperty(position);
        this.salary = new SimpleDoubleProperty(salary);
        this.workingHours = new SimpleIntegerProperty(workingHours);
    }

    // Getters
    public String getEmployeeId() { return employeeId.get(); }
    public String getName() { return name.get(); }
    public String getDepartment() { return department.get(); }
    public String getPosition() { return position.get(); }
    public double getSalary() { return salary.get(); }
    public int getWorkingHours() { return workingHours.get(); }

    // Property getters
    public StringProperty employeeIdProperty() { return employeeId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty departmentProperty() { return department; }
    public StringProperty positionProperty() { return position; }
    public DoubleProperty salaryProperty() { return salary; }
    public IntegerProperty workingHoursProperty() { return workingHours; }
}
