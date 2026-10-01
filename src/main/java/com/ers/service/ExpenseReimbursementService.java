package com.ers.service;

import ch.qos.logback.classic.Logger;
import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ExpenseReimbursementService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(
                    ExpenseReimbursementService.class
            );

    private final IUserDao userDao;
    private final IEmployeeDao employeeDao;
    private final IDepartmentDao departmentDao;

    public ExpenseReimbursementService(
            IUserDao userDao,
            IEmployeeDao employeeDao,
            IDepartmentDao departmentDao) {

        this.userDao = userDao;
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
    }

    public Employee createUserAndEmployee(
            User user,
            Employee employee) {

        Connection con = null;

        try {

            if (employee == null) {
                throw new IllegalArgumentException(
                        "Employee details are required for employee/manager/finance roles."
                );
            }

            if (employee.getDepartmentId() <= 0) {
                throw new IllegalArgumentException(
                        "Employee must belong to a valid department."
                );
            }

            if (departmentDao.getDepartmentById(employee.getDepartmentId()) == null) {
                throw new IllegalArgumentException(
                        "Department ID " + employee.getDepartmentId() + " does not exist."
                );
            }

            // BEGIN TRANSACTION
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            logger.info("Transaction started.");

            // 1. Add User
            User savedUser =
                    userDao.addUser(user, con);

            if (savedUser == null) {

                con.rollback();

                logger.warn(
                        "User creation failed. Transaction rolled back."
                );

                return null;
            }

            logger.info(
                    "User created successfully. User ID={}",
                    savedUser.getUserId()
            );

            // Get generated user_id
            employee.setUserId(
                    savedUser.getUserId()
            );

            // 2. Add Employee
            Employee savedEmployee =
                    employeeDao.addEmployee(
                            employee,
                            con
                    );

            if (savedEmployee == null) {

                con.rollback();

                logger.warn(
                        "Employee creation failed. Transaction rolled back."
                );

                return null;
            }

            logger.info(
                    "Employee created successfully. Employee ID={}",
                    savedEmployee.getEmployeeId()
            );

            // 3. If Manager, update department manager
            if ("MANAGER".equals(user.getRole())) {

                boolean updated =
                        departmentDao.updateManagerId(
                                employee.getDepartmentId(),
                                savedEmployee.getEmployeeId(),
                                con
                        );

                if (!updated) {

                    con.rollback();

                    logger.warn(
                            "Department manager update failed. " +
                                    "Transaction rolled back."
                    );

                    return null;
                }

                logger.info(
                        "Department manager updated successfully."
                );
            }

            // COMMIT
            con.commit();

            logger.info(
                    "Transaction committed successfully."
            );

            return savedEmployee;

        } catch (SQLException e) {

            // ROLLBACK
            try {

                if (con != null) {
                    con.rollback();

                    logger.warn(
                            "Transaction rolled back due to SQL error."
                    );
                }

            } catch (SQLException rollbackException) {

                logger.error(
                        "Rollback failed.",
                        rollbackException
                );
            }

            logger.error(
                    "Error while creating user and employee.",
                    e
            );

            return null;

        } finally {

            try {

                if (con != null) {
                    con.close();

                    logger.info(
                            "Database connection closed."
                    );
                }

            } catch (SQLException e) {

                logger.error(
                        "Error while closing database connection.",
                        e
                );
            }
        }
    }
}