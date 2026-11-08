CREATE DATABASE IF NOT EXISTS employee_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE employee_db;

DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    salary DECIMAL(12, 2) NOT NULL,
    department_id INT NOT NULL,
    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id) REFERENCES departments(id)
);

INSERT INTO departments(name) VALUES ('Develop'), ('Admin');

INSERT INTO employees(name, role, salary, department_id) VALUES
    ('Ngoc', 'Developer', 1000.00, 1),
    ('Hoang Vinh', 'Developer', 2500.00, 1),
    ('Thu', 'Manager', 4000.00, 2);
