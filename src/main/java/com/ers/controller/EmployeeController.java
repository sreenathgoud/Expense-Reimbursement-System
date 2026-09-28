package com.ers.controller;

import com.ers.model.Employee;
import com.ers.service.IEmployeeService;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {

    private final IEmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
        this.scanner = new Scanner(System.in);
    }

    // =========================
    // EMPLOYEE MENU
    // =========================

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("        EMPLOYEE MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Get Employee By ID");
            System.out.println("4. Get All Employees");
            System.out.println("5. Delete Employee");
            System.out.println("6. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addEmployeeFromInput();
                        break;

                    case "2":
                        updateEmployeeFromInput();
                        break;

                    case "3":
                        getEmployeeByIdFromInput();
                        break;

                    case "4":
                        displayAllEmployees();
                        break;

                    case "5":
                        deleteEmployeeFromInput();
                        break;

                    case "6":
                        running = false;
                        System.out.println("Returning to main menu.");
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice: " + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid employee input: " + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in EmployeeController."
                );
                e.printStackTrace();
            }
        }
    }

    // =========================
    // ADD EMPLOYEE
    // =========================

    private void addEmployeeFromInput() {

        System.out.println("========== ADD EMPLOYEE ==========");

        System.out.println("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Full Name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Department ID:");
        int departmentId =
                Integer.parseInt(scanner.nextLine());

        Employee employee = new Employee(
                userId,
                fullName,
                email,
                departmentId
        );

        Employee result = addNewEmployee(employee);

        if (result != null) {

            System.out.println(
                    "Employee added successfully. Employee ID="
                            + result.getEmployeeId()
            );

        } else {

            System.out.println("Failed to add employee.");
        }
    }

    // =========================
    // UPDATE EMPLOYEE
    // =========================

    private void updateEmployeeFromInput() {

        System.out.println("========== UPDATE EMPLOYEE ==========");

        System.out.println("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter User ID:");
        int userId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Full Name:");
        String fullName = scanner.nextLine();

        System.out.println("Enter Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Department ID:");
        int departmentId =
                Integer.parseInt(scanner.nextLine());

        Employee employee = new Employee(
                userId,
                fullName,
                email,
                departmentId
        );

        employee.setEmployeeId(employeeId);

        boolean result = updateEmployee(employee);

        if (result) {

            System.out.println(
                    "Employee updated successfully. Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Employee update failed. Employee ID="
                            + employeeId
            );
        }
    }

    // =========================
    // GET EMPLOYEE BY ID
    // =========================

    private void getEmployeeByIdFromInput() {

        System.out.println(
                "========== GET EMPLOYEE BY ID =========="
        );

        System.out.println("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(scanner.nextLine());

        Employee employee =
                getEmployeeById(employeeId);

        if (employee != null) {

            System.out.println("Employee found:");
            System.out.println(employee);

        } else {

            System.out.println(
                    "No employee found with ID="
                            + employeeId
            );
        }
    }

    // =========================
    // GET ALL EMPLOYEES
    // =========================

    private void displayAllEmployees() {

        System.out.println("========== ALL EMPLOYEES ==========");

        List<Employee> employees =
                getAllEmployees();

        if (employees.isEmpty()) {

            System.out.println("No employees found.");

        } else {

            System.out.println(
                    "Total employees found: "
                            + employees.size()
            );

            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    // =========================
    // DELETE EMPLOYEE
    // =========================

    private void deleteEmployeeFromInput() {

        System.out.println("========== DELETE EMPLOYEE ==========");

        System.out.println("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteEmployeeById(employeeId);

        if (result) {

            System.out.println(
                    "Employee deleted successfully. Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Employee deletion failed. Employee ID="
                            + employeeId
            );
        }
    }

    // =========================
    // SERVICE DELEGATION METHODS
    // =========================

    public Employee addNewEmployee(Employee employee) {
        return employeeService.addEmployee(employee);
    }

    public boolean updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public boolean deleteEmployeeById(int employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }
}