package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.dao.*;
import com.ers.service.*;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AppController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(AppController.class);

    private final Scanner scanner;

    private final UserController userController;
    private final EmployeeController employeeController;
    private final DepartmentController departmentController;
    private final ExpenseCategoryController expenseCategoryController;
    private final ExpenseClaimController expenseClaimController;
    private final ClaimItemController claimItemController;
    private final FinanceExecutiveController financeExecutiveController;
    private final ReimbursementController reimbursementController;

    public AppController() {

        scanner = new Scanner(System.in);

        /*
         * =========================
         * USER
         * =========================
         */

        IUserDao userDao =
                new UserDaoImpl();

        IUserService userService =
                new UserServiceImpl(userDao);

        userController =
                new UserController(userService);


        /*
         * =========================
         * EMPLOYEE
         * =========================
         */

        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        IEmployeeService employeeService =
                new EmployeeServiceImpl(employeeDao);

        employeeController =
                new EmployeeController(employeeService);


        /*
         * =========================
         * DEPARTMENT
         * =========================
         */

        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        IDepartmentService departmentService =
                new DepartmentServiceImpl(departmentDao);

        departmentController =
                new DepartmentController(departmentService);


        /*
         * =========================
         * EXPENSE CATEGORY
         * =========================
         */

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


        /*
         * =========================
         * EXPENSE CLAIM
         * =========================
         */

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


        /*
         * =========================
         * CLAIM ITEM
         * =========================
         */

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


        /*
         * =========================
         * FINANCE EXECUTIVE
         * =========================
         */

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


        /*
         * =========================
         * REIMBURSEMENT
         * =========================
         */

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

        logger.info(
                "Expense Reimbursement System started."
        );

        while (running) {

            logger.info("======================================");
            logger.info("   EXPENSE REIMBURSEMENT SYSTEM");
            logger.info("======================================");
            logger.info("1. User Management");
            logger.info("2. Employee Management");
            logger.info("3. Department Management");
            logger.info("4. Expense Category Management");
            logger.info("5. Expense Claim Management");
            logger.info("6. Claim Item Management");
            logger.info("7. Finance Executive");
            logger.info("8. Reimbursement");
            logger.info("9. Exit");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":

                        logger.info(
                                "Opening User Management."
                        );

                        userController.start();

                        break;


                    case "2":

                        logger.info(
                                "Opening Employee Management."
                        );

                        employeeController.start();

                        break;


                    case "3":

                        logger.info(
                                "Opening Department Management."
                        );

                        departmentController.start();

                        break;


                    case "4":

                        logger.info(
                                "Opening Expense Category Management."
                        );

                        expenseCategoryController.start();

                        break;


                    case "5":

                        logger.info(
                                "Opening Expense Claim Management."
                        );

                        expenseClaimController.start();

                        break;


                    case "6":

                        logger.info(
                                "Opening Claim Item Management."
                        );

                        claimItemController.start();

                        break;


                    case "7":

                        logger.info(
                                "Opening Finance Executive."
                        );

                        financeExecutiveController.start();

                        break;


                    case "8":

                        logger.info(
                                "Opening Reimbursement Management."
                        );

                        reimbursementController.start();

                        break;


                    case "9":

                        running = false;

                        logger.info(
                                "Exiting Expense Reimbursement System."
                        );

                        break;


                    default:

                        logger.warn(
                                "Invalid menu choice: {}",
                                choice
                        );
                }

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in application.",
                        e
                );
            }
        }

        logger.info(
                "Expense Reimbursement System stopped."
        );
    }
}