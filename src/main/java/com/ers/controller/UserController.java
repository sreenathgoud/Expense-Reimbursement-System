
        package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.User;
import com.ers.service.IUserService;
import com.ers.model.Employee;
import com.ers.service.ExpenseReimbursementService;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UserController {

    private final IUserService userService;
    private final Scanner scanner;
    private final ExpenseReimbursementService
            expenseReimbursementService;

    public UserController(
            IUserService userService,
            ExpenseReimbursementService expenseReimbursementService) {

        this.userService = userService;
        this.expenseReimbursementService =
                expenseReimbursementService;

        this.scanner = new Scanner(System.in);
    }
    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    public void start() {
        logger.info("Started UserController.start()");

        boolean running = true;

        while (running) {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "          USER MANAGEMENT"
            );

            System.out.println(
                    "======================================"
            );

            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Get User By ID");
            System.out.println("4. Get All Users");
            System.out.println("5. Delete User");
            System.out.println("6. Get User By Username");
            System.out.println("7. Update User Status");
            System.out.println("8. Back");

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Enter your choice:"
            );

            String choice =
                    scanner.nextLine();

            try {

                switch (choice) {

                    case "1":

                        addUserFromInput();

                        break;

                    case "2":

                        updateUserFromInput();

                        break;

                    case "3":

                        getUserByIdFromInput();

                        break;

                    case "4":

                        displayAllUsers();

                        break;

                    case "5":

                        deleteUserFromInput();

                        break;

                    case "6":

                        getUserByUsernameFromInput();

                        break;

                    case "7":

                        updateUserStatusFromInput();

                        break;

                    case "8":

                        running = false;

                        System.out.println(
                                "Returning to main menu."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid menu choice: " + choice
                        );
                }
                logger.info("Ending at UserController.start()");

            } catch (IllegalArgumentException e) {
                logger.error("Invalid input at UserController.start()",e);

                System.out.println(
                        "Invalid user input: "
                                + e.getMessage()
                );

            } catch (SQLException e) {
                logger.error("exception at UserController.start()",e);
                System.out.println(
                        "Database error in UserController: "
                                + e.getMessage()
                );

                e.printStackTrace();

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in UserController: "
                                + e.getMessage()
                );

                e.printStackTrace();
            }
        }
    }

    private void addUserFromInput()
            throws SQLException {
        logger.info("Invalid input at UserController.addUserFromInput()");

        System.out.println(
                "========== ADD USER =========="
        );

        System.out.println(
                "Enter username:"
        );

        String username =
                scanner.nextLine();

        System.out.println(
                "Enter password:"
        );

        String password =
                scanner.nextLine();

        System.out.println(
                "Enter role (EMPLOYEE / MANAGER / "
                        + "FINANCE_EXECUTIVE / ADMIN):"
        );

        String role =
                scanner.nextLine().toUpperCase();

        User user =
                new User(
                        username,
                        password,
                        role,
                        true,
                        LocalDateTime.now()
                );

        /*
         * ADMIN does not need an employee record.
         */

        if ("ADMIN".equals(role)) {

            User result =
                    userService.addUser(user);

            if (result != null) {

                System.out.println(
                        "Admin user added successfully. "
                                + "User ID="
                                + result.getUserId()
                );

            } else {

                System.out.println(
                        "Failed to add admin user."
                );
            }

            return;
        }

        /*
         * EMPLOYEE, MANAGER and FINANCE_EXECUTIVE
         * need an employee record.
         */

        System.out.println(
                "Enter full name:"
        );

        String fullName =
                scanner.nextLine();

        System.out.println(
                "Enter email:"
        );

        String email =
                scanner.nextLine();

        System.out.println(
                "Enter Department ID (enter 0 if none):"
        );

        int departmentId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        /*
         * Convert 0 to null because department_id
         * is nullable in the database.
         */

        Integer department =
                departmentId == 0
                        ? null
                        : departmentId;

        Employee employee =
                new Employee(
                        0,
                        fullName,
                        email,
                        department
                );

        Employee result =
                expenseReimbursementService
                        .createUserAndEmployee(
                                user,
                                employee
                        );

        if (result != null) {

            System.out.println(
                    "User and Employee created successfully."
            );

            System.out.println(
                    "User ID="
                            + employee.getUserId()
            );

            System.out.println(
                    "Employee ID="
                            + result.getEmployeeId()
            );

            if ("MANAGER".equals(role)) {

                System.out.println(
                        "Manager assigned to Department ID="
                                + departmentId
                );
            }

        } else {

            System.out.println(
                    "User and Employee creation failed. "
                            + "Transaction rolled back."
            );
        }
    }

    private void updateUserFromInput() {

        System.out.println(
                "========== UPDATE USER =========="
        );

        System.out.println(
                "Enter User ID:"
        );

        int userId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter username:"
        );

        String username =
                scanner.nextLine();

        System.out.println(
                "Enter password:"
        );

        String password =
                scanner.nextLine();

        System.out.println(
                "Enter role:"
        );

        String role =
                scanner.nextLine().toUpperCase();

        System.out.println(
                "Is active? (true/false):"
        );

        boolean active =
                Boolean.parseBoolean(
                        scanner.nextLine()
                );

        User user =
                new User(
                        username,
                        password,
                        role,
                        active,
                        LocalDateTime.now()
                );

        user.setUserId(userId);

        boolean result =
                updateUser(user);

        if (result) {

            System.out.println(
                    "User updated successfully. "
                            + "User ID="
                            + userId
            );

        } else {

            System.out.println(
                    "User update failed. "
                            + "User ID="
                            + userId
            );
        }
    }

    private void getUserByIdFromInput() {

        System.out.println(
                "========== GET USER BY ID =========="
        );

        System.out.println(
                "Enter User ID:"
        );

        int userId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        User user =
                getUserById(userId);

        if (user != null) {

            System.out.println(
                    "User found:"
            );

            System.out.println(user);

        } else {

            System.out.println(
                    "No user found with ID="
                            + userId
            );
        }
    }

    private void displayAllUsers() {

        System.out.println(
                "========== ALL USERS =========="
        );

        List<User> users =
                getAllUsers();

        if (users.isEmpty()) {

            System.out.println(
                    "No users found."
            );

        } else {

            System.out.println(
                    "Total users found: "
                            + users.size()
            );

            for (User user : users) {

                System.out.println(user);
            }
        }
    }

    private void deleteUserFromInput() {

        System.out.println(
                "========== DELETE USER =========="
        );

        System.out.println(
                "Enter User ID:"
        );

        int userId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                deleteUserById(userId);

        if (result) {

            System.out.println(
                    "User deleted successfully. "
                            + "User ID="
                            + userId
            );

        } else {

            System.out.println(
                    "User deletion failed. "
                            + "User ID="
                            + userId
            );
        }
    }

    private void getUserByUsernameFromInput() {

        System.out.println(
                "========== GET USER BY USERNAME =========="
        );

        System.out.println(
                "Enter username:"
        );

        String username =
                scanner.nextLine();

        User user =
                getUserByUsername(username);

        if (user != null) {

            System.out.println(
                    "User found:"
            );

            System.out.println(user);

        } else {

            System.out.println(
                    "No user found with username: "
                            + username
            );
        }
    }

    private void updateUserStatusFromInput() {

        System.out.println(
                "========== UPDATE USER STATUS =========="
        );

        System.out.println(
                "Enter User ID:"
        );

        int userId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Set active? (true/false):"
        );

        boolean active =
                Boolean.parseBoolean(
                        scanner.nextLine()
                );

        boolean result =
                updateUserStatus(
                        userId,
                        active
                );

        if (result) {

            System.out.println(
                    "User status updated successfully. "
                            + "User ID="
                            + userId
                            + ", active="
                            + active
            );

        } else {

            System.out.println(
                    "User status update failed. "
                            + "User ID="
                            + userId
            );
        }
    }

    public User addUser(User user)
            throws SQLException {

        return userService.addUser(user);
    }

    public boolean updateUser(User user) {

        return userService.updateUser(user);
    }

    public User getUserById(int userId) {

        return userService.getUserById(userId);
    }

    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    public boolean deleteUserById(int userId) {

        return userService.deleteUserById(userId);
    }

    public User getUserByUsername(String username) {

        return userService.getUserByUsername(username);
    }

    public boolean updateUserStatus(
            int userId,
            boolean active) {

        return userService.updateUserStatus(
                userId,
                active
        );
    }
}
