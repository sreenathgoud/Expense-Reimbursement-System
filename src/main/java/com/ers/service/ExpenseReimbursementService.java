package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class ExpenseReimbursementService {

    private IUserDao userDao;
    private IEmployeeDao employeeDao;
    private IDepartmentDao departmentDao;

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

            // BEGIN TRANSACTION
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // 1. Add User
            User savedUser =
                    userDao.addUser(user, con);

            if (savedUser == null) {
                con.rollback();
                return null;
            }

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
                return null;
            }

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
                    return null;
                }
            }

            // COMMIT
            con.commit();

            return savedEmployee;

        } catch (SQLException e) {

            // ROLLBACK
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
            return null;

        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
