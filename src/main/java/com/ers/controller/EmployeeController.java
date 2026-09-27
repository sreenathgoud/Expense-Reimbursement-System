package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.Employee;
import com.ers.service.IEmployeeService;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(EmployeeController.class);

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

            logger.info("======================================");
            logger.info("        EMPLOYEE MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Employee");
            logger.info("2. Update Employee");
            logger.info("3. Get Employee By ID");
            logger.info("4. Get All Employees");
            logger.info("5. Delete Employee");
            logger.info("6. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

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
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn(
                                "Invalid menu choice: {}",
                                choice
                        );
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid employee input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in EmployeeController.",
                        e
                );
            }
        }
    }

    // =========================
    // ADD EMPLOYEE
    // =========================

    private void addEmployeeFromInput() {

        logger.info("========== ADD EMPLOYEE ==========");

        logger.info("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        logger.info("Enter Full Name:");
        String fullName = scanner.nextLine();

        logger.info("Enter Email:");
        String email = scanner.nextLine();

        logger.info("Enter Department ID:");
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

            logger.info(
                    "Employee added successfully. Employee ID={}",
                    result.getEmployeeId()
            );

        } else {

            logger.warn("Failed to add employee.");
        }
    }

    // =========================
    // UPDATE EMPLOYEE
    // =========================

    private void updateEmployeeFromInput() {

        logger.info("========== UPDATE EMPLOYEE ==========");

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter User ID:");
        int userId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Full Name:");
        String fullName = scanner.nextLine();

        logger.info("Enter Email:");
        String email = scanner.nextLine();

        logger.info("Enter Department ID:");
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

            logger.info(
                    "Employee updated successfully. Employee ID={}",
                    employeeId
            );

        } else {

            logger.warn(
                    "Employee update failed. Employee ID={}",
                    employeeId
            );
        }
    }

    // =========================
    // GET EMPLOYEE BY ID
    // =========================

    private void getEmployeeByIdFromInput() {

        logger.info(
                "========== GET EMPLOYEE BY ID =========="
        );

        logger.info("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(scanner.nextLine());

        Employee employee =
                getEmployeeById(employeeId);

        if (employee != null) {

            logger.info("Employee found:");
            logger.info("{}", employee);

        } else {

            logger.warn(
                    "No employee found with ID={}",
                    employeeId
            );
        }
    }

    // =========================
    // GET ALL EMPLOYEES
    // =========================

    private void displayAllEmployees() {

        logger.info("========== ALL EMPLOYEES ==========");

        List<Employee> employees =
                getAllEmployees();

        if (employees.isEmpty()) {

            logger.info("No employees found.");

        } else {

            logger.info(
                    "Total employees found: {}",
                    employees.size()
            );

            for (Employee employee : employees) {
                logger.info("{}", employee);
            }
        }
    }

    // =========================
    // DELETE EMPLOYEE
    // =========================

    private void deleteEmployeeFromInput() {

        logger.info("========== DELETE EMPLOYEE ==========");

        logger.info("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteEmployeeById(employeeId);

        if (result) {

            logger.info(
                    "Employee deleted successfully. Employee ID={}",
                    employeeId
            );

        } else {

            logger.warn(
                    "Employee deletion failed. Employee ID={}",
                    employeeId
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