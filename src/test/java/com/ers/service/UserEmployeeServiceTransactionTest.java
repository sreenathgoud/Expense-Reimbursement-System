package com.ers.service;

import com.ers.dao.DepartmentDaoImpl;
import com.ers.dao.EmployeeDaoImpl;
import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.dao.UserDaoImpl;
import com.ers.model.Employee;
import com.ers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserEmployeeServiceTransactionTest {

    @Test
    void createManagerTransactionTest() {

        // Arrange
        IUserDao userDao = new UserDaoImpl();
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new com.ers.util.JDBCUtil());
        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        UserEmployeeService service =
                new UserEmployeeService(
                        userDao,
                        employeeDao,
                        departmentDao
                );

        User user = new User(
                "transactionmanager",
                "test123",
                "MANAGER",
                true,
                LocalDateTime.now()
        );

        Employee employee = new Employee(
                0,
                "Transaction Manager",
                "transactionmanager@test.com",
                6
        );

        // Act
        Employee actualResult =
                service.createUserAndEmployee(
                        user,
                        employee
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(
                actualResult.getEmployeeId() > 0
        );
        Assertions.assertTrue(
                actualResult.getUserId() > 0
        );
    }
    @Test
    void createEmployeeTransactionTest() {

        // Arrange
        IUserDao userDao = new UserDaoImpl();

        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new com.ers.util.JDBCUtil());

        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        UserEmployeeService service =
                new UserEmployeeService(
                        userDao,
                        employeeDao,
                        departmentDao
                );

        User user = new User(
                "transactionemployee",
                "test123",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        Employee employee = new Employee(
                0,
                "Transaction Employee",
                "transactionemployee@test.com",
                6
        );

        // Act
        Employee actualResult =
                service.createUserAndEmployee(
                        user,
                        employee
                );

        // Assert
        Assertions.assertNotNull(actualResult);

        Assertions.assertTrue(
                actualResult.getUserId() > 0
        );

        Assertions.assertTrue(
                actualResult.getEmployeeId() > 0
        );
    }
}