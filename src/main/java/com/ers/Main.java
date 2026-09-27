package com.ers;

import ch.qos.logback.classic.Logger;
import com.ers.controller.AppController;
import com.ers.dao.EmployeeDaoImpl;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.dao.UserDaoImpl;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.service.EmployeeServiceImpl;
import com.ers.service.IEmployeeService;
import com.ers.service.IUserService;
import com.ers.service.UserServiceImpl;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // USER
        IUserDao userDao = new UserDaoImpl();
        IUserService userService =
                new UserServiceImpl(userDao);

        logger.info("======================================");
        logger.info("   EXPENSE REIMBURSEMENT SYSTEM");
        logger.info("                 LOGIN");
        logger.info("======================================");

        logger.info("Enter Username:");
        String username = scanner.nextLine();

        logger.info("Enter Password:");
        String password = scanner.nextLine();

        User user =
                userService.getUserByUsername(username);

        if (user == null) {
            logger.warn("Invalid username.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            logger.warn("Invalid password.");
            return;
        }

        if (!user.isActive()) {
            logger.warn("User account is inactive.");
            return;
        }

        logger.info("Login successful.");
        logger.info("Welcome, {}", user.getUserName());
        logger.info("Role: {}", user.getRole());

        /*
         * ==================================
         * GET EMPLOYEE DETAILS
         * ==================================
         */

        Employee employee = null;

        if ("EMPLOYEE".equals(user.getRole())) {

            IEmployeeDao employeeDao =
                    new EmployeeDaoImpl(new JDBCUtil());

            IEmployeeService employeeService =
                    new EmployeeServiceImpl(employeeDao);

            employee =
                    employeeService.getEmployeeByUserId(
                            user.getUserId()
                    );

            if (employee == null) {

                logger.warn(
                        "Employee record not found for user ID: {}",
                        user.getUserId()
                );

                return;
            }

            logger.info(
                    "Employee ID: {}",
                    employee.getEmployeeId()
            );
        }

        /*
         * ==================================
         * START APPLICATION
         * ==================================
         */

        AppController appController =
                new AppController(
                        scanner,
                        user,
                        employee
                );

        appController.start();
    }
}