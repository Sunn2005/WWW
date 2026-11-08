package com.se.demorestdb.model;

public class Employee {
    private int id;
    private String name;
    private double salary;
    private String role;
    private int departmentId;

    // Constructors, Getters, Setters
    public Employee() {}

    public Employee(int id, String name, double salary, String role, int departmentId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.role = role;
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
}
