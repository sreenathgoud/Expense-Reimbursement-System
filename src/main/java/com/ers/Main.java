package com.ers;

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

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // =========================
        // USER
        // =========================

        IUserDao userDao =
                new UserDaoImpl();

        IUserService userService =
                new UserServiceImpl(userDao);

        System.out.println(
                "======================================"
        );

        System.out.println(
                "   EXPENSE REIMBURSEMENT SYSTEM"
        );

        System.out.println(
                "                 LOGIN"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Enter Username:"
        );

        String username =
                scanner.nextLine();

        System.out.println(
                "Enter Password:"
        );

        String password =
                scanner.nextLine();

        User user =
                userService.getUserByUsername(username);

        // =========================
        // USER VALIDATION
        // =========================

        if (user == null) {

            System.out.println(
                    "Invalid username."
            );

            return;
        }

        if (!user.getPassword().equals(password)) {

            System.out.println(
                    "Invalid password."
            );

            return;
        }

        if (!user.isActive()) {

            System.out.println(
                    "User account is inactive."
            );

            return;
        }

        System.out.println(
                "Login successful."
        );

        System.out.println(
                "Welcome, " + user.getUserName()
        );

        System.out.println(
                "Role: " + user.getRole()
        );

        /*
         * ==================================
         * GET EMPLOYEE DETAILS
         * ==================================
         */

        Employee employee = null;

        if ("EMPLOYEE".equals(user.getRole())) {

            IEmployeeDao employeeDao =
                    new EmployeeDaoImpl(
                            new JDBCUtil()
                    );

            IEmployeeService employeeService =
                    new EmployeeServiceImpl(
                            employeeDao
                    );

            employee =
                    employeeService.getEmployeeByUserId(
                            user.getUserId()
                    );

            if (employee == null) {

                System.out.println(
                        "Employee record not found for user ID: "
                                + user.getUserId()
                );

                return;
            }

            System.out.println(
                    "Employee ID: "
                            + employee.getEmployeeId()
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
