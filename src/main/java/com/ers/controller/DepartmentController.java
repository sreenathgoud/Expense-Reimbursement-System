package com.ers.controller;

import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.service.IDepartmentService;

import java.util.List;
import java.util.Scanner;

public class DepartmentController {

    private final IDepartmentService departmentService;
    private final Scanner scanner;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("       DEPARTMENT MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Get Department By ID");
            System.out.println("4. Get All Departments");
            System.out.println("5. Delete Department");
            System.out.println("6. Get Employees By Department ID");
            System.out.println("7. Get Department By Manager ID");
            System.out.println("8. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

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
                        System.out.println("Returning to main menu.");
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice: " + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid department input: " + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in DepartmentController."
                );
                e.printStackTrace();
            }
        }
    }

    // =========================
    // ADD DEPARTMENT
    // =========================

    private void addDepartmentFromInput() {

        System.out.println("========== ADD DEPARTMENT ==========");

        System.out.println("Enter Department Name:");
        String departmentName = scanner.nextLine();

        System.out.println(
                "Enter Manager ID (enter 0 if no manager):"
        );
        int managerId =
                Integer.parseInt(scanner.nextLine());

        Integer manager =
                managerId == 0 ? null : managerId;

        Department department =
                new Department(departmentName, manager);

        Department result =
                addDepartment(department);

        if (result != null) {

            System.out.println(
                    "Department added successfully. Department ID="
                            + result.getDepartmentId()
            );

        } else {

            System.out.println(
                    "Failed to add department."
            );
        }
    }

    // =========================
    // UPDATE DEPARTMENT
    // =========================

    private void updateDepartmentFromInput() {

        System.out.println(
                "========== UPDATE DEPARTMENT =========="
        );

        System.out.println("Enter Department ID:");
        int departmentId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Department Name:");
        String departmentName =
                scanner.nextLine();

        System.out.println(
                "Enter Manager ID (enter 0 if no manager):"
        );
        int managerId =
                Integer.parseInt(scanner.nextLine());

        Integer manager =
                managerId == 0 ? null : managerId;

        Department department =
                new Department(departmentName, manager);

        department.setDepartmentId(departmentId);

        boolean result =
                updateDepartment(department);

        if (result) {

            System.out.println(
                    "Department updated successfully. Department ID="
                            + departmentId
            );

        } else {

            System.out.println(
                    "Department update failed. Department ID="
                            + departmentId
            );
        }
    }

    // =========================
    // GET DEPARTMENT BY ID
    // =========================

    private void getDepartmentByIdFromInput() {

        System.out.println(
                "========== GET DEPARTMENT BY ID =========="
        );

        System.out.println("Enter Department ID:");

        int departmentId =
                Integer.parseInt(scanner.nextLine());

        Department department =
                getDepartmentById(departmentId);

        if (department != null) {

            System.out.println("Department found:");
            System.out.println(department);

        } else {

            System.out.println(
                    "No department found with ID="
                            + departmentId
            );
        }
    }

    // =========================
    // GET ALL DEPARTMENTS
    // =========================

    private void displayAllDepartments() {

        System.out.println(
                "========== ALL DEPARTMENTS =========="
        );

        List<Department> departments =
                getAllDepartments();

        if (departments.isEmpty()) {

            System.out.println(
                    "No departments found."
            );

        } else {

            System.out.println(
                    "Total departments found: "
                            + departments.size()
            );

            for (Department department : departments) {
                System.out.println(department);
            }
        }
    }

    // =========================
    // DELETE DEPARTMENT
    // =========================

    private void deleteDepartmentFromInput() {

        System.out.println(
                "========== DELETE DEPARTMENT =========="
        );

        System.out.println("Enter Department ID:");

        int departmentId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteDepartmentById(departmentId);

        if (result) {

            System.out.println(
                    "Department deleted successfully. Department ID="
                            + departmentId
            );

        } else {

            System.out.println(
                    "Department deletion failed. Department ID="
                            + departmentId
            );
        }
    }

    // =========================
    // GET EMPLOYEES BY DEPARTMENT
    // =========================

    private void getEmployeesByDepartmentIdFromInput() {

        System.out.println(
                "========== EMPLOYEES BY DEPARTMENT =========="
        );

        System.out.println("Enter Department ID:");

        int departmentId =
                Integer.parseInt(scanner.nextLine());

        List<Employee> employees =
                getEmployeesByDepartmentId(departmentId);

        if (employees.isEmpty()) {

            System.out.println(
                    "No employees found for Department ID="
                            + departmentId
            );

        } else {

            System.out.println(
                    "Employees in Department ID="
                            + departmentId + ":"
            );

            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    // =========================
    // GET DEPARTMENT BY MANAGER
    // =========================

    private void getDepartmentByManagerIdFromInput() {

        System.out.println(
                "========== DEPARTMENT BY MANAGER =========="
        );

        System.out.println("Enter Manager ID:");

        int managerId =
                Integer.parseInt(scanner.nextLine());

        Department department =
                getDepartmentByManagerId(managerId);

        if (department != null) {

            System.out.println("Department found:");
            System.out.println(department);

        } else {

            System.out.println(
                    "No department found for Manager ID="
                            + managerId
            );
        }
    }

    // =========================
    // SERVICE DELEGATION METHODS
    // =========================

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