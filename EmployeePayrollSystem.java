
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Employee {
    String id, name, designation;
    double basic, hra, allowance, deduction;

    Employee(String id, String name, String designation,
             double basic, double hra, double allowance, double deduction) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.basic = basic;
        this.hra = hra;
        this.allowance = allowance;
        this.deduction = deduction;
    }

    double getGrossSalary() {
        return basic + hra + allowance;
    }

    double getNetSalary() {
        return getGrossSalary() - deduction;
    }
}

class LoginWindow extends Frame implements ActionListener {
    TextField username, password;
    Button login;

    LoginWindow() {
        setTitle("Employee Payroll System - Login");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        Label title = new Label("Employee Payroll Login", Label.CENTER);
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");

        username = new TextField();
        password = new TextField();
        password.setEchoChar('*');

        login = new Button("Login");
        login.addActionListener(this);

        add(title);
        add(new Label(""));
        add(userLabel);
        add(username);
        add(passLabel);
        add(password);
        add(new Label(""));
        add(login);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (username.getText().equals("giri") &&
            password.getText().equals("payroll@123")) {

            dispose();
            new EmployeePayrollSystem();

        } else {
            showMessage("Invalid username or password!");
        }
    }

    void showMessage(String message) {
        Dialog d = new Dialog(this, "Login", true);
        d.setLayout(new FlowLayout());
        d.add(new Label(message));

        Button ok = new Button("OK");
        ok.addActionListener(e -> d.dispose());
        d.add(ok);

        d.setSize(250, 120);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
}

public class EmployeePayrollSystem extends Frame implements ActionListener {

    TextField idField, nameField, designationField;
    TextField basicField, hraField, allowanceField, deductionField;
    TextArea output;

    ArrayList<Employee> employees = new ArrayList<>();

    Button addButton, updateButton, deleteButton;
    Button viewButton, salaryButton, clearButton, exitButton;

    EmployeePayrollSystem() {
        setTitle("Employee Payroll Management System");
        setSize(850, 600);
        setLayout(new BorderLayout(10, 10));

        Label heading = new Label(
            "EMPLOYEE PAYROLL MANAGEMENT SYSTEM",
            Label.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(8, 2, 8, 8));

        idField = new TextField();
        nameField = new TextField();
        designationField = new TextField();
        basicField = new TextField();
        hraField = new TextField();
        allowanceField = new TextField();
        deductionField = new TextField();

        form.add(new Label("Employee ID:"));
        form.add(idField);

        form.add(new Label("Employee Name:"));
        form.add(nameField);

        form.add(new Label("Designation:"));
        form.add(designationField);

        form.add(new Label("Basic Salary:"));
        form.add(basicField);

        form.add(new Label("HRA:"));
        form.add(hraField);

        form.add(new Label("Allowance:"));
        form.add(allowanceField);

        form.add(new Label("Deduction:"));
        form.add(deductionField);

        form.add(new Label("Salary Formula:"));
        form.add(new Label("Basic + HRA + Allowance - Deduction"));

        Panel buttons = new Panel(new GridLayout(2, 4, 8, 8));

        addButton = new Button("Add Employee");
        updateButton = new Button("Update Employee");
        deleteButton = new Button("Delete Employee");
        viewButton = new Button("View Employees");
        salaryButton = new Button("Calculate Salary");
        clearButton = new Button("Clear");
        exitButton = new Button("Exit");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(viewButton);
        buttons.add(salaryButton);
        buttons.add(clearButton);
        buttons.add(exitButton);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);
        salaryButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        Panel centerPanel = new Panel(new BorderLayout(10, 10));
        centerPanel.add(form, BorderLayout.NORTH);
        centerPanel.add(buttons, BorderLayout.CENTER);

        output = new TextArea("", 12, 80, TextArea.SCROLLBARS_BOTH);
        output.setEditable(false);

        Panel mainPanel = new Panel(new BorderLayout(10, 10));
        mainPanel.add(centerPanel, BorderLayout.NORTH);
        mainPanel.add(output, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        loadEmployees();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveEmployees();
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addButton) {
            addEmployee();
        } else if (source == updateButton) {
            updateEmployee();
        } else if (source == deleteButton) {
            deleteEmployee();
        } else if (source == viewButton) {
            viewEmployees();
        } else if (source == salaryButton) {
            calculateSalary();
        } else if (source == clearButton) {
            clearFields();
        } else if (source == exitButton) {
            saveEmployees();
            dispose();
        }
    }

    Employee findEmployee(String id) {
        for (Employee emp : employees) {
            if (emp.id.equals(id)) {
                return emp;
            }
        }
        return null;
    }

    Employee readEmployeeFields() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String designation = designationField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || designation.isEmpty()) {
            throw new IllegalArgumentException("Please fill all employee details.");
        }

        if (id.contains("\t") || name.contains("\t") ||
            designation.contains("\t") ||
            id.contains("\n") || name.contains("\n") ||
            designation.contains("\n")) {
            throw new IllegalArgumentException("Invalid text entered.");
        }

        double basic = Double.parseDouble(basicField.getText().trim());
        double hra = Double.parseDouble(hraField.getText().trim());
        double allowance = Double.parseDouble(allowanceField.getText().trim());
        double deduction = Double.parseDouble(deductionField.getText().trim());

        if (basic < 0 || hra < 0 || allowance < 0 || deduction < 0) {
            throw new IllegalArgumentException("Salary values cannot be negative.");
        }

        if (deduction > basic + hra + allowance) {
            throw new IllegalArgumentException("Deduction cannot exceed gross salary.");
        }

        return new Employee(id, name, designation,
                basic, hra, allowance, deduction);
    }

    void addEmployee() {
        try {
            Employee emp = readEmployeeFields();

            if (findEmployee(emp.id) != null) {
                showMessage("Employee ID already exists.");
                return;
            }

            employees.add(emp);
            saveEmployees();
            showMessage("Employee added successfully.");
            clearFields();

        } catch (NumberFormatException ex) {
            showMessage("Enter valid numeric salary values.");
        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage());
        }
    }

    void updateEmployee() {
        try {
            String id = idField.getText().trim();
            Employee oldEmployee = findEmployee(id);

            if (oldEmployee == null) {
                showMessage("Employee not found.");
                return;
            }

            Employee updated = readEmployeeFields();

            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).id.equals(id)) {
                    employees.set(i, updated);
                    break;
                }
            }

            saveEmployees();
            showMessage("Employee updated successfully.");
            clearFields();

        } catch (NumberFormatException ex) {
            showMessage("Enter valid numeric salary values.");
        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage());
        }
    }

    void deleteEmployee() {
        String id = idField.getText().trim();
        Employee emp = findEmployee(id);

        if (emp == null) {
            showMessage("Employee not found.");
            return;
        }

        employees.remove(emp);
        saveEmployees();
        showMessage("Employee deleted successfully.");
        clearFields();
    }

    void viewEmployees() {
        output.setText("");

        if (employees.isEmpty()) {
            output.setText("No employee records found.");
            return;
        }

        for (Employee emp : employees) {
            output.append(
                "Employee ID: " + emp.id + "\n" +
                "Name: " + emp.name + "\n" +
                "Designation: " + emp.designation + "\n" +
                "Basic Salary: " + emp.basic + "\n" +
                "HRA: " + emp.hra + "\n" +
                "Allowance: " + emp.allowance + "\n" +
                "Deduction: " + emp.deduction + "\n" +
                "Gross Salary: " + emp.getGrossSalary() + "\n" +
                "Net Salary: " + emp.getNetSalary() + "\n" +
                "----------------------------------------\n"
            );
        }
    }

    void calculateSalary() {
        String id = idField.getText().trim();
        Employee emp = findEmployee(id);

        if (emp == null) {
            showMessage("Employee not found.");
            return;
        }

        output.setText(
            "SALARY DETAILS\n\n" +
            "Employee ID: " + emp.id + "\n" +
            "Employee Name: " + emp.name + "\n" +
            "Designation: " + emp.designation + "\n\n" +
            "Basic Salary: " + emp.basic + "\n" +
            "HRA: " + emp.hra + "\n" +
            "Allowance: " + emp.allowance + "\n" +
            "Gross Salary: " + emp.getGrossSalary() + "\n" +
            "Deduction: " + emp.deduction + "\n" +
            "Net Salary: " + emp.getNetSalary()
        );
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        designationField.setText("");
        basicField.setText("");
        hraField.setText("");
        allowanceField.setText("");
        deductionField.setText("");
        output.setText("");
    }

    void saveEmployees() {
        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter("employees.txt"))) {

            for (Employee emp : employees) {
                writer.write(
                    emp.id + "\t" +
                    emp.name + "\t" +
                    emp.designation + "\t" +
                    emp.basic + "\t" +
                    emp.hra + "\t" +
                    emp.allowance + "\t" +
                    emp.deduction
                );
                writer.newLine();
            }

        } catch (IOException ex) {
            showMessage("Error saving employee records.");
        }
    }

    void loadEmployees() {
        File file = new File("employees.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t", -1);

                if (data.length == 7) {
                    try {
                        Employee emp = new Employee(
                            data[0],
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Double.parseDouble(data[4]),
                            Double.parseDouble(data[5]),
                            Double.parseDouble(data[6])
                        );

                        employees.add(emp);
                    } catch (NumberFormatException ex) {
                        System.out.println("Skipping invalid employee record.");
                    }
                }
            }

        } catch (IOException ex) {
            showMessage("Error loading employee records.");
        }
    }

    void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new FlowLayout());

        dialog.add(new Label(message));

        Button ok = new Button("OK");
        ok.addActionListener(e -> dialog.dispose());

        dialog.add(ok);
        dialog.setSize(350, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}
