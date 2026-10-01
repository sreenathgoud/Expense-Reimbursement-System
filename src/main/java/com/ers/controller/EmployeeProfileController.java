package com.ers.controller;

import com.ers.model.Employee;
import com.ers.model.Department;
import com.ers.model.User;
import com.ers.service.IDepartmentService;
import com.ers.service.IEmployeeService;
import com.ers.service.IUserService;

import java.util.Scanner;

public class EmployeeProfileController {

    private final Employee employee;
    private final User user;
    private final IEmployeeService employeeService;
    private final IUserService userService;
    private final IDepartmentService departmentService;
    private final Scanner scanner;

    public EmployeeProfileController(
            Employee employee,
            User user,
            IEmployeeService employeeService,
            IUserService userService,
            IDepartmentService departmentService,
            Scanner scanner) {
        this.employee = employee;
        this.user = user;
        this.employeeService = employeeService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("========== EMPLOYEE PROFILE ==========");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Back");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        displayProfile();
                        break;
                    case "2":
                        updateProfile();
                        break;
                    case "3":
                        changePassword();
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid profile menu choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid profile input: " + e.getMessage());
            }
        }
    }

    private void displayProfile() {
        System.out.println("Employee ID: " + employee.getEmployeeId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("Role: " + user.getRole());
        System.out.println("Full name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        Department department =
                departmentService.getDepartmentById(employee.getDepartmentId());

        if (department == null) {
            System.out.println("Department: Not found (ID " + employee.getDepartmentId() + ")");
            System.out.println("Manager: Not available.");
            return;
        }

        System.out.println(
                "Department: " + department.getDepartmentName()
                        + " (ID " + department.getDepartmentId() + ")"
        );

        if (department.getManagerId() == null) {
            System.out.println("Manager: Not assigned to this department.");
            return;
        }

        Employee manager = employeeService.getEmployeeById(department.getManagerId());
        if (manager == null) {
            System.out.println(
                    "Manager employee record not found (ID "
                            + department.getManagerId() + ")."
            );
            return;
        }

        System.out.println(
                "Manager: " + manager.getFullName()
                        + " (Employee ID " + manager.getEmployeeId() + ")"
        );
    }

    private void updateProfile() {
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine().trim();
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();
        if (fullName.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("A name and valid email are required.");
        }

        Employee updatedEmployee = new Employee(
                employee.getUserId(),
                fullName,
                email,
                employee.getDepartmentId()
        );
        updatedEmployee.setEmployeeId(employee.getEmployeeId());
        if (employeeService.updateEmployee(updatedEmployee)) {
            employee.setFullName(fullName);
            employee.setEmail(email);
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Profile update failed.");
        }
    }

    private void changePassword() {
        System.out.println("Enter current password:");
        String currentPassword = scanner.nextLine();
        if (!currentPassword.equals(user.getPassword())) {
            System.out.println("Current password is incorrect.");
            return;
        }

        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        System.out.println("Confirm new password:");
        String confirmation = scanner.nextLine();
        if (newPassword.isBlank() || !newPassword.equals(confirmation)) {
            throw new IllegalArgumentException("Passwords must be non-empty and match.");
        }

        User updatedUser = new User(
                user.getUserName(),
                newPassword,
                user.getRole(),
                user.isActive(),
                user.getCreatedAt()
        );
        updatedUser.setUserId(user.getUserId());
        if (userService.updateUser(updatedUser)) {
            user.setPassword(newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password change failed.");
        }
    }
}