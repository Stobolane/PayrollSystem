package com.example.payrollmanagement.models;

public class Employee {
    private int employeeId;
    private String name;
    private String department;
    private String position;
    private double basicSalary;
    private int workingHours;
    private int userId;

    public Employee() {}

    public Employee(String name, String department, String position,
                    double basicSalary, int workingHours, int userId) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.basicSalary = basicSalary;
        this.workingHours = workingHours;
        this.userId = userId;
    }

    // Getters and setters
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public int getWorkingHours() { return workingHours; }
    public void setWorkingHours(int workingHours) { this.workingHours = workingHours; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}