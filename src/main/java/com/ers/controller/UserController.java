package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.User;
import com.ers.service.IUserService;
import org.slf4j.LoggerFactory;
import com.ers.model.Employee;
import com.ers.service.ExpenseReimbursementService;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UserController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;
    private final Scanner scanner;
    private final ExpenseReimbursementService
            expenseReimbursementService;
    public UserController(IUserService userService, ExpenseReimbursementService expenseReimbursementService) {
        this.userService = userService;
        this.expenseReimbursementService = expenseReimbursementService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            logger.info("======================================");
            logger.info("          USER MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add User");
            logger.info("2. Update User");
            logger.info("3. Get User By ID");
            logger.info("4. Get All Users");
            logger.info("5. Delete User");
            logger.info("6. Get User By Username");
            logger.info("7. Update User Status");
            logger.info("8. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

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
                        "Invalid user input: {}",
                        e.getMessage()
                );

            } catch (SQLException e) {

                logger.error(
                        "Database error in UserController.",
                        e
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in UserController.",
                        e
                );
            }
        }
    }

    private void addUserFromInput() throws SQLException {

        logger.info("========== ADD USER ==========");

        logger.info("Enter username:");
        String username = scanner.nextLine();

        logger.info("Enter password:");
        String password = scanner.nextLine();

        logger.info(
                "Enter role (EMPLOYEE / MANAGER / " +
                        "FINANCE_EXECUTIVE / ADMIN):"
        );

        String role =
                scanner.nextLine().toUpperCase();

        User user = new User(
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

                logger.info(
                        "Admin user added successfully. User ID={}",
                        result.getUserId()
                );

            } else {

                logger.warn("Failed to add admin user.");
            }

            return;
        }

        /*
         * EMPLOYEE, MANAGER and FINANCE_EXECUTIVE
         * need an employee record.
         */

        logger.info("Enter full name:");
        String fullName = scanner.nextLine();

        logger.info("Enter email:");
        String email = scanner.nextLine();

        logger.info("Enter Department ID (enter 0 if none):");

        int departmentId =
                Integer.parseInt(scanner.nextLine());

        /*
         * Convert 0 to null because department_id
         * is nullable in the database.
         */
        Integer department =
                departmentId == 0 ? null : departmentId;

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

            logger.info(
                    "User and Employee created successfully."
            );

            logger.info(
                    "User ID={}",
                    employee.getUserId()
            );

            logger.info(
                    "Employee ID={}",
                    result.getEmployeeId()
            );

            if ("MANAGER".equals(role)) {

                logger.info(
                        "Manager assigned to Department ID={}",
                        departmentId
                );
            }

        } else {

            logger.warn(
                    "User and Employee creation failed. " +
                            "Transaction rolled back."
            );
        }
    }

    private void updateUserFromInput() {

        logger.info("========== UPDATE USER ==========");

        logger.info("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        logger.info("Enter username:");
        String username = scanner.nextLine();

        logger.info("Enter password:");
        String password = scanner.nextLine();

        logger.info("Enter role:");
        String role = scanner.nextLine().toUpperCase();

        logger.info("Is active? (true/false):");
        boolean active =
                Boolean.parseBoolean(scanner.nextLine());

        User user = new User(
                username,
                password,
                role,
                active,
                LocalDateTime.now()
        );

        user.setUserId(userId);

        boolean result = updateUser(user);

        if (result) {

            logger.info(
                    "User updated successfully. User ID={}",
                    userId
            );

        } else {

            logger.warn(
                    "User update failed. User ID={}",
                    userId
            );
        }
    }

    private void getUserByIdFromInput() {

        logger.info("========== GET USER BY ID ==========");

        logger.info("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        User user = getUserById(userId);

        if (user != null) {

            logger.info("User found:");
            logger.info("{}", user);

        } else {

            logger.warn(
                    "No user found with ID={}",
                    userId
            );
        }
    }

    private void displayAllUsers() {

        logger.info("========== ALL USERS ==========");

        List<User> users = getAllUsers();

        if (users.isEmpty()) {

            logger.info("No users found.");

        } else {

            logger.info(
                    "Total users found: {}",
                    users.size()
            );

            for (User user : users) {
                logger.info("{}", user);
            }
        }
    }

    private void deleteUserFromInput() {

        logger.info("========== DELETE USER ==========");

        logger.info("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        boolean result = deleteUserById(userId);

        if (result) {

            logger.info(
                    "User deleted successfully. User ID={}",
                    userId
            );

        } else {

            logger.warn(
                    "User deletion failed. User ID={}",
                    userId
            );
        }
    }

    private void getUserByUsernameFromInput() {

        logger.info(
                "========== GET USER BY USERNAME =========="
        );

        logger.info("Enter username:");
        String username = scanner.nextLine();

        User user = getUserByUsername(username);

        if (user != null) {

            logger.info("User found:");
            logger.info("{}", user);

        } else {

            logger.warn(
                    "No user found with username: {}",
                    username
            );
        }
    }

    private void updateUserStatusFromInput() {

        logger.info(
                "========== UPDATE USER STATUS =========="
        );

        logger.info("Enter User ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        logger.info("Set active? (true/false):");
        boolean active =
                Boolean.parseBoolean(scanner.nextLine());

        boolean result =
                updateUserStatus(userId, active);

        if (result) {

            logger.info(
                    "User status updated successfully. " +
                            "User ID={}, active={}",
                    userId,
                    active
            );

        } else {

            logger.warn(
                    "User status update failed. User ID={}",
                    userId
            );
        }
    }

    public User addUser(User user) throws SQLException {
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