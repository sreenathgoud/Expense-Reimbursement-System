package com.ers.controller;

import com.ers.dao.*;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.service.*;
import com.ers.util.JDBCUtil;

import java.util.Scanner;

public class AppController {

    private final Scanner scanner;
    private final User user;
    private final Employee employee;

    private final UserController userController;
    private final EmployeeController employeeController;
    private final EmployeeProfileController employeeProfileController;
    private final DepartmentController departmentController;
    private final ExpenseCategoryController expenseCategoryController;
    private final ExpenseClaimController expenseClaimController;
    private final ClaimItemController claimItemController;
    private final FinanceExecutiveController financeExecutiveController;
    private final ReimbursementController reimbursementController;

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

        // =========================
        // EMPLOYEE
        // =========================

        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(
                        new JDBCUtil()
                );

        IEmployeeService employeeService =
                new EmployeeServiceImpl(
                        employeeDao
                );

        employeeController =
                new EmployeeController(
                        employeeService,
                        user.getRole()
                );

        // =========================
        // DEPARTMENT
        // =========================

        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        IDepartmentService departmentService =
                new DepartmentServiceImpl(
                        departmentDao
                );

        IFinanceExecutiveDao financeExecutiveDao =
                new FinanceExecutiveDaoImpl(
                        new JDBCUtil()
                );

        employeeProfileController = employee == null
                ? null
                : new EmployeeProfileController(
                employee,
                user,
                employeeService,
                userService,
                departmentService,
                scanner
        );

        departmentController =
                new DepartmentController(
                        departmentService
                );

        // =========================================
        // USER + EMPLOYEE TRANSACTION SERVICE
        // =========================================

        ExpenseReimbursementService
                expenseReimbursementService =
                new ExpenseReimbursementService(
                        userDao,
                        employeeDao,
                        departmentDao,
                        financeExecutiveDao
                );

        // =========================
        // USER CONTROLLER
        // =========================

        userController =
                new UserController(
                        userService,
                        expenseReimbursementService
                );

        // =========================
        // EXPENSE CATEGORY
        // =========================

        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(
                        new JDBCUtil()
                );

        IExpenseCategoryService
                expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDao
                );

        expenseCategoryController =
                new ExpenseCategoryController(
                        expenseCategoryService
                );

        // =========================
        // CLAIM ITEM
        // =========================

        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(
                        new JDBCUtil()
                );

        IClaimItemService claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDao,
                        expenseCategoryService
                );

        // =========================
        // EXPENSE CLAIM
        // =========================

        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(
                        new JDBCUtil()
                );

        IExpenseClaimService
                expenseClaimService =
                new ExpenseClaimServiceImpl(
                        expenseClaimDao
                );

        expenseClaimController =
                new ExpenseClaimController(
                        expenseClaimService,
                        claimItemService,
                        employeeService,
                        expenseCategoryService,
                        employee != null ? employee.getEmployeeId() : null
                );

        claimItemController =
                new ClaimItemController(
                        claimItemService
                );

        // =========================
        // FINANCE EXECUTIVE
        // =========================

        IFinanceExecutiveService
                financeExecutiveService =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        financeExecutiveController =
                new FinanceExecutiveController(
                        financeExecutiveService,
                        employee != null ? employee.getEmployeeId() : null,
                        employeeService,
                        user.getRole()
                );

        // =========================
        // REIMBURSEMENT
        // =========================

        IReimbursementDao reimbursementDao =
                new ReimbursementDaoImpl(
                        new JDBCUtil()
                );

        IReimbursementService
                reimbursementService =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        reimbursementController =
                new ReimbursementController(
                        reimbursementService
                );

        // =========================
        // EMPLOYEE CLAIM CONTROLLER
        // =========================

        if ("EMPLOYEE".equals(user.getRole()) && employee != null) {
            employeeClaimController = new EmployeeClaimController(
                    expenseClaimService,
                    reimbursementService,
                    scanner,
                    employee.getEmployeeId()
            );
        } else {
            employeeClaimController = null;
        }

        System.out.println(
                "All controllers and services initialized successfully."
        );
    }

    // ==========================================
    // START APPLICATION
    // ==========================================

    public void start() {

        boolean running = true;

        String role = user.getRole();

        System.out.println(
                "Expense Reimbursement System started."
        );

        System.out.println(
                "Logged-in role: " + role
        );

        if (employee != null) {
            System.out.println("Employee ID: " + employee.getEmployeeId());
        }

        while (running) {

            displayMenu(role);

            String choice =
                    scanner.nextLine();

            try {

                switch (role) {

                    case "EMPLOYEE":

                        running =
                                handleEmployeeMenu(
                                        choice
                                );

                        break;

                    case "MANAGER":

                        running =
                                handleManagerMenu(
                                        choice
                                );

                        break;

                    case "FINANCE_EXECUTIVE":

                        running =
                                handleFinanceMenu(
                                        choice
                                );

                        break;

                    case "ADMIN":

                        running =
                                handleAdminMenu(
                                        choice
                                );

                        break;

                    default:

                        System.out.println(
                                "Invalid user role: " + role
                        );

                        running = false;
                }

            } catch (Exception e) {

                System.out.println(
                        "Unexpected application error: "
                                + e.getMessage()
                );
            }
        }

        System.out.println(
                "User logged out."
        );
    }

    // ==========================================
    // DISPLAY MENU
    // ==========================================

    private void displayMenu(String role) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "   EXPENSE REIMBURSEMENT SYSTEM"
        );

        System.out.println(
                "======================================"
        );

        switch (role) {

            // ==================================
            // EMPLOYEE
            // ==================================

            case "EMPLOYEE":

                System.out.println(
                        "1. Employee Profile"
                );

                System.out.println(
                        "2. Expense Claims"
                );

                System.out.println(
                        "3. Exit"
                );

                break;

            // ==================================
            // MANAGER
            // ==================================

            case "MANAGER":

                System.out.println(
                        "1. Expense Claim Management"
                );

                System.out.println(
                        "2. Exit"
                );

                break;

            // ==================================
            // FINANCE EXECUTIVE
            // ==================================

            case "FINANCE_EXECUTIVE":

                System.out.println(
                        "1. Finance Executive"
                );

                System.out.println(
                        "2. Exit"
                );

                break;

            // ==================================
            // ADMIN
            // ==================================

            case "ADMIN":

                System.out.println(
                        "1. User Management"
                );

                System.out.println(
                        "2. Employee Management"
                );

                System.out.println(
                        "3. Department Management"
                );

                System.out.println(
                        "4. Expense Category Management"
                );

                System.out.println(
                        "5. Expense Claim Management"
                );

                System.out.println(
                        "6. Claim Item Management"
                );

                System.out.println(
                        "7. Finance Executive"
                );

                System.out.println(
                        "8. Add Finance Executive"
                );

                System.out.println(
                        "9. Reimbursement"
                );

                System.out.println(
                        "10. Exit"
                );

                break;
        }

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Enter your choice:"
        );
    }

    // ==========================================
    // EMPLOYEE MENU
    // ==========================================

    private boolean handleEmployeeMenu(
            String choice) {

        switch (choice) {

            case "1":

                if (employeeProfileController == null) {
                    System.out.println(
                            "No employee profile is available for this account."
                    );
                    return true;
                }

                employeeProfileController.start();

                break;

            case "2":

                if (employeeClaimController == null) {
                    System.out.println(
                            "No employee profile is available for this account."
                    );
                    return true;
                }

                System.out.println(
                        "Opening Employee Expense Claims."
                );

                employeeClaimController.start();

                break;

            case "3":

                System.out.println(
                        "Employee logged out."
                );

                return false;

            default:

                System.out.println(
                        "Invalid employee menu choice: "
                                + choice
                );
        }

        return true;
    }

    // ==========================================
    // MANAGER MENU
    // ==========================================

    private boolean handleManagerMenu(
            String choice) {

        switch (choice) {

            case "1":

                System.out.println(
                        "Opening Expense Claim Management."
                );

                expenseClaimController.startManager();

                break;

            case "2":

                System.out.println(
                        "Manager logged out."
                );

                return false;

            default:

                System.out.println(
                        "Invalid manager menu choice: "
                                + choice
                );
        }

        return true;
    }

    // ==========================================
    // FINANCE EXECUTIVE MENU
    // ==========================================

    private boolean handleFinanceMenu(
            String choice) {

        switch (choice) {

            case "1":

                System.out.println(
                        "Opening Finance Executive."
                );

                financeExecutiveController.start();

                break;

            case "2":

                System.out.println(
                        "Finance Executive logged out."
                );

                return false;

            default:

                System.out.println(
                        "Invalid finance menu choice: "
                                + choice
                );
        }

        return true;
    }

    // ==========================================
    // ADMIN MENU
    // ==========================================

    private boolean handleAdminMenu(
            String choice) {

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

                                financeExecutiveController.addFinanceExecutiveForAdmin();

                                break;

                        case "9":

                reimbursementController.start();

                break;

            case "10":

                System.out.println(
                        "Admin logged out."
                );

                return false;

            default:

                System.out.println(
                        "Invalid admin menu choice: "
                                + choice
                );
        }

        return true;
    }
}