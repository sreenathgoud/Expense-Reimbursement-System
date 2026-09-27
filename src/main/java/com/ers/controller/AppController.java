package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.dao.*;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.service.*;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AppController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(AppController.class);

    private final Scanner scanner;
    private final User user;
    private final Employee employee;

    private final UserController userController;
    private final EmployeeController employeeController;
    private final DepartmentController departmentController;
    private final ExpenseCategoryController expenseCategoryController;
    private final ExpenseClaimController expenseClaimController;
    private final ClaimItemController claimItemController;
    private final FinanceExecutiveController financeExecutiveController;
    private final ReimbursementController reimbursementController;

    // Employee claim controller
    private final EmployeeClaimController employeeClaimController;


    public AppController(
            Scanner scanner,
            User user,
            Employee employee) {

        this.scanner = scanner;
        this.user = user;
        this.employee = employee;


        // =========================
        // USER
        // =========================

        IUserDao userDao =
                new UserDaoImpl();

        IUserService userService =
                new UserServiceImpl(userDao);

        userController =
                new UserController(userService);


        // =========================
        // EMPLOYEE
        // =========================

        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        IEmployeeService employeeService =
                new EmployeeServiceImpl(employeeDao);

        employeeController =
                new EmployeeController(employeeService);


        // =========================
        // DEPARTMENT
        // =========================

        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        IDepartmentService departmentService =
                new DepartmentServiceImpl(departmentDao);

        departmentController =
                new DepartmentController(departmentService);


        // =========================
        // EXPENSE CATEGORY
        // =========================

        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        IExpenseCategoryService expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDao
                );

        expenseCategoryController =
                new ExpenseCategoryController(
                        expenseCategoryService
                );


        // =========================
        // EXPENSE CLAIM
        // =========================

        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        IExpenseClaimService expenseClaimService =
                new ExpenseClaimServiceImpl(
                        expenseClaimDao
                );

        expenseClaimController =
                new ExpenseClaimController(
                        expenseClaimService
                );


        // =========================
        // CLAIM ITEM
        // =========================

        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        IClaimItemService claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDao
                );

        claimItemController =
                new ClaimItemController(
                        claimItemService
                );


        // =========================
        // EMPLOYEE CLAIM CONTROLLER
        // =========================

        if ("EMPLOYEE".equals(user.getRole())) {

            employeeClaimController =
                    new EmployeeClaimController(
                            expenseClaimService,
                            claimItemService,
                            scanner,
                            employee.getEmployeeId()
                    );

        } else {

            employeeClaimController = null;
        }


        // =========================
        // FINANCE EXECUTIVE
        // =========================

        IFinanceExecutiveDao financeExecutiveDao =
                new FinanceExecutiveDaoImpl(new JDBCUtil());

        IFinanceExecutiveService financeExecutiveService =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        financeExecutiveController =
                new FinanceExecutiveController(
                        financeExecutiveService
                );


        // =========================
        // REIMBURSEMENT
        // =========================

        IReimbursementDao reimbursementDao =
                new ReimbursementDaoImpl(new JDBCUtil());

        IReimbursementService reimbursementService =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        reimbursementController =
                new ReimbursementController(
                        reimbursementService
                );


        logger.info(
                "All controllers and services initialized successfully."
        );
    }


    public void start() {

        boolean running = true;

        String role = user.getRole();

        logger.info(
                "Expense Reimbursement System started."
        );

        logger.info(
                "Logged-in role: {}",
                role
        );

        while (running) {

            displayMenu(role);

            String choice = scanner.nextLine();

            try {

                switch (role) {

                    case "EMPLOYEE":

                        running =
                                handleEmployeeMenu(choice);

                        break;

                    case "MANAGER":

                        running =
                                handleManagerMenu(choice);

                        break;

                    case "FINANCE_EXECUTIVE":

                        running =
                                handleFinanceMenu(choice);

                        break;

                    case "ADMIN":

                        running =
                                handleAdminMenu(choice);

                        break;

                    default:

                        logger.warn(
                                "Invalid user role: {}",
                                role
                        );

                        running = false;
                }

            } catch (Exception e) {

                logger.error(
                        "Unexpected application error.",
                        e
                );
            }
        }

        logger.info("User logged out.");
    }


    // ==========================================
    // DISPLAY MENU
    // ==========================================

    private void displayMenu(String role) {

        logger.info("======================================");
        logger.info("   EXPENSE REIMBURSEMENT SYSTEM");
        logger.info("======================================");

        switch (role) {

            case "EMPLOYEE":

                logger.info("1. Expense Claims");
                logger.info("2. Exit");

                break;

            case "MANAGER":

                logger.info("1. Expense Claim Management");
                logger.info("2. Claim Item Management");
                logger.info("3. Exit");

                break;

            case "FINANCE_EXECUTIVE":

                logger.info("1. Finance Executive");
                logger.info("2. Reimbursement");
                logger.info("3. Exit");

                break;

            case "ADMIN":

                logger.info("1. User Management");
                logger.info("2. Employee Management");
                logger.info("3. Department Management");
                logger.info("4. Expense Category Management");
                logger.info("5. Expense Claim Management");
                logger.info("6. Claim Item Management");
                logger.info("7. Finance Executive");
                logger.info("8. Reimbursement");
                logger.info("9. Exit");

                break;
        }

        logger.info("======================================");
        logger.info("Enter your choice:");
    }


    // ==========================================
    // EMPLOYEE MENU
    // ==========================================

    private boolean handleEmployeeMenu(String choice) {

        switch (choice) {

            case "1":

                logger.info(
                        "Opening Employee Expense Claims."
                );

                employeeClaimController.start();

                break;

            case "2":

                logger.info(
                        "Employee logged out."
                );

                return false;

            default:

                logger.warn(
                        "Invalid employee menu choice: {}",
                        choice
                );
        }

        return true;
    }


    // ==========================================
    // MANAGER MENU
    // ==========================================

    private boolean handleManagerMenu(String choice) {

        switch (choice) {

            case "1":

                logger.info(
                        "Opening Expense Claim Management."
                );

                expenseClaimController.start();

                break;

            case "2":

                logger.info(
                        "Opening Claim Item Management."
                );

                claimItemController.start();

                break;

            case "3":

                logger.info(
                        "Manager logged out."
                );

                return false;

            default:

                logger.warn(
                        "Invalid manager menu choice: {}",
                        choice
                );
        }

        return true;
    }


    // ==========================================
    // FINANCE EXECUTIVE MENU
    // ==========================================

    private boolean handleFinanceMenu(String choice) {

        switch (choice) {

            case "1":

                logger.info(
                        "Opening Finance Executive."
                );

                financeExecutiveController.start();

                break;

            case "2":

                logger.info(
                        "Opening Reimbursement."
                );

                reimbursementController.start();

                break;

            case "3":

                logger.info(
                        "Finance Executive logged out."
                );

                return false;

            default:

                logger.warn(
                        "Invalid finance menu choice: {}",
                        choice
                );
        }

        return true;
    }


    // ==========================================
    // ADMIN MENU
    // ==========================================

    private boolean handleAdminMenu(String choice) {

        switch (choice) {

            case "1":

                userController.start();

                break;

            case "2":

                employeeController.start();

                break;

            case "3":

                departmentController.start();

                break;

            case "4":

                expenseCategoryController.start();

                break;

            case "5":

                expenseClaimController.start();

                break;

            case "6":

                claimItemController.start();

                break;

            case "7":

                financeExecutiveController.start();

                break;

            case "8":

                reimbursementController.start();

                break;

            case "9":

                logger.info(
                        "Admin logged out."
                );

                return false;

            default:

                logger.warn(
                        "Invalid admin menu choice: {}",
                        choice
                );
        }

        return true;
    }
}