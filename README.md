# Employee Payroll Management System

The **Employee Payroll Management System** is a Java-based desktop application developed to simplify employee record management and salary calculation. The project provides a graphical user interface (GUI) that allows users to manage employee information and perform basic payroll operations efficiently.

The application is developed using **Java AWT** for the graphical interface and event handling. It includes a login system that restricts access to the main application. After successful login, users can access the payroll dashboard to perform various employee management operations.

### Features

- **Login Authentication:** Provides a login screen with username and password validation.
- **Add Employee:** Add employee details such as ID, name, designation, basic salary, HRA, allowance, and deduction.
- **Update Employee:** Modify existing employee records.
- **Delete Employee:** Remove employee records using the employee ID.
- **View Employees:** Display stored employee details and salary information.
- **Salary Calculation:** Automatically calculates gross and net salaries.
- **Input Validation:** Prevents invalid inputs, duplicate employee IDs, negative salary values, and deductions exceeding gross salary.
- **File Storage:** Saves employee records to a text file and loads them when the application starts.

### Salary Calculation

Gross Salary = Basic Salary + HRA + Allowance

Net Salary = Gross Salary − Deduction

### Technologies Used

- Java
- Java AWT
- Event Handling
- ArrayList
- File Handling
- BufferedReader and BufferedWriter

### Purpose

This project demonstrates the practical application of Java programming concepts, object-oriented programming, GUI development, event handling, data validation, and file management. It provides a simple foundation for understanding how an employee payroll application works and can be extended in the future with database integration, enhanced security, payslip generation, and advanced payroll features.
