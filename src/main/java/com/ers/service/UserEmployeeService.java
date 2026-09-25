package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.model.Employee;
import com.ers.model.User;
import com.ers.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class UserEmployeeService {

    private IUserDao userDao;
    private IEmployeeDao employeeDao;
    private IDepartmentDao departmentDao;

    public UserEmployeeService(
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
            // Begin transaction
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // 1. Insert User
            User savedUser =
                    userDao.addUser(user, con);

            if (savedUser == null) {
                con.rollback();
                return null;
            }

            // Set generated user_id into Employee
            employee.setUserId(
                    savedUser.getUserId()
            );

            // 2. Insert Employee
            Employee savedEmployee =
                    employeeDao.addEmployee(employee, con);

            if (savedEmployee == null) {
                con.rollback();
                return null;
            }

            // 3. If user is MANAGER,
            //    update department manager_id
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

            // All operations successful
            con.commit();

            return savedEmployee;

        } catch (SQLException e) {

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