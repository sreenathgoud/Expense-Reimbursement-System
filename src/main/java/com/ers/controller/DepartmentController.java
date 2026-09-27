package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.service.IDepartmentService;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class DepartmentController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(DepartmentController.class);

    private final IDepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            logger.info("======================================");
            logger.info("       DEPARTMENT MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Department");
            logger.info("2. Update Department");
            logger.info("3. Get Department By ID");
            logger.info("4. Get All Departments");
            logger.info("5. Delete Department");
            logger.info("6. Get Employees By Department ID");
            logger.info("7. Get Department By Manager ID");
            logger.info("8. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addDepartmentFromInput();
                        break;

                    case "2":
                        updateDepartmentFromInput();
                        break;

                    case "3":
                        getDepartmentByIdFromInput();
                        break;

                    case "4":
                        displayAllDepartments();
                        break;

                    case "5":
                        deleteDepartmentFromInput();
                        break;

                    case "6":
                        getEmployeesByDepartmentIdFromInput();
                        break;

                    case "7":
                        getDepartmentByManagerIdFromInput();
                        break;

                    case "8":
                        running = false;
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid department input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in DepartmentController.",
                        e
                );
            }
        }
    }

    private void addDepartmentFromInput() {

        logger.info("========== ADD DEPARTMENT ==========");

        logger.info("Enter Department Name:");
        String departmentName = scanner.nextLine();

        logger.info("Enter Manager ID (enter 0 if no manager):");
        int managerId = Integer.parseInt(scanner.nextLine());

        Integer manager = managerId == 0 ? null : managerId;

        Department department =
                new Department(departmentName, manager);

        Department result = addDepartment(department);

        if (result != null) {
            logger.info(
                    "Department added successfully. Department ID={}",
                    result.getDepartmentId()
            );
        } else {
            logger.warn("Failed to add department.");
        }
    }

    private void updateDepartmentFromInput() {

        logger.info("========== UPDATE DEPARTMENT ==========");

        logger.info("Enter Department ID:");
        int departmentId = Integer.parseInt(scanner.nextLine());

        logger.info("Enter Department Name:");
        String departmentName = scanner.nextLine();

        logger.info("Enter Manager ID (enter 0 if no manager):");
        int managerId = Integer.parseInt(scanner.nextLine());

        Integer manager = managerId == 0 ? null : managerId;

        Department department =
                new Department(departmentName, manager);

        department.setDepartmentId(departmentId);

        boolean result = updateDepartment(department);

        if (result) {
            logger.info(
                    "Department updated successfully. Department ID={}",
                    departmentId
            );
        } else {
            logger.warn(
                    "Department update failed. Department ID={}",
                    departmentId
            );
        }
    }

    private void getDepartmentByIdFromInput() {

        logger.info("========== GET DEPARTMENT BY ID ==========");

        logger.info("Enter Department ID:");
        int departmentId = Integer.parseInt(scanner.nextLine());

        Department department =
                getDepartmentById(departmentId);

        if (department != null) {

            logger.info("Department found:");
            logger.info("{}", department);

        } else {

            logger.warn(
                    "No department found with ID={}",
                    departmentId
            );
        }
    }

    private void displayAllDepartments() {

        logger.info("========== ALL DEPARTMENTS ==========");

        List<Department> departments =
                getAllDepartments();

        if (departments.isEmpty()) {

            logger.info("No departments found.");

        } else {

            logger.info(
                    "Total departments found: {}",
                    departments.size()
            );

            for (Department department : departments) {
                logger.info("{}", department);
            }
        }
    }

    private void deleteDepartmentFromInput() {

        logger.info("========== DELETE DEPARTMENT ==========");

        logger.info("Enter Department ID:");
        int departmentId = Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteDepartmentById(departmentId);

        if (result) {

            logger.info(
                    "Department deleted successfully. Department ID={}",
                    departmentId
            );

        } else {

            logger.warn(
                    "Department deletion failed. Department ID={}",
                    departmentId
            );
        }
    }

    private void getEmployeesByDepartmentIdFromInput() {

        logger.info(
                "========== EMPLOYEES BY DEPARTMENT =========="
        );

        logger.info("Enter Department ID:");
        int departmentId = Integer.parseInt(scanner.nextLine());

        List<Employee> employees =
                getEmployeesByDepartmentId(departmentId);

        if (employees.isEmpty()) {

            logger.info(
                    "No employees found for Department ID={}",
                    departmentId
            );

        } else {

            logger.info(
                    "Employees in Department ID={}:",
                    departmentId
            );

            for (Employee employee : employees) {
                logger.info("{}", employee);
            }
        }
    }

    private void getDepartmentByManagerIdFromInput() {

        logger.info(
                "========== DEPARTMENT BY MANAGER =========="
        );

        logger.info("Enter Manager ID:");
        int managerId = Integer.parseInt(scanner.nextLine());

        Department department =
                getDepartmentByManagerId(managerId);

        if (department != null) {

            logger.info("Department found:");
            logger.info("{}", department);

        } else {

            logger.warn(
                    "No department found for Manager ID={}",
                    managerId
            );
        }
    }

    // Service delegation methods

    public Department addDepartment(Department department) {
        return departmentService.addDepartment(department);
    }

    public boolean updateDepartment(Department department) {
        return departmentService.updateDepartment(department);
    }

    public Department getDepartmentById(int departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    public boolean deleteDepartmentById(int departmentId) {
        return departmentService.deleteDepartmentById(departmentId);
    }

    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        return departmentService.getEmployeesByDepartmentId(departmentId);
    }

    public Department getDepartmentByManagerId(int managerId) {
        return departmentService.getDepartmentByManagerId(managerId);
    }
}