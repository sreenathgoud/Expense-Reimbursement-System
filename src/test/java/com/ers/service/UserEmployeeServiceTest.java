package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IUserDao;
import com.ers.model.Employee;
import com.ers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserEmployeeServiceTest {

    @Test
    void createUserAndEmployee() throws SQLException {
        // Arrange
        IUserDao userDao = mock(IUserDao.class);
        IEmployeeDao employeeDao = mock(IEmployeeDao.class);
        IDepartmentDao departmentDao = mock(IDepartmentDao.class);

        UserEmployeeService service =
                new UserEmployeeService(
                        userDao,
                        employeeDao,
                        departmentDao
                );

        User user = new User(
                "managerTest",
                "test123",
                "MANAGER",
                true,
                LocalDateTime.now()
        );

        Employee employee = new Employee(
                0,
                "Manager Test",
                "manager@test.com",
                6
        );

        User savedUser = user;
        savedUser.setUserId(20);

        Employee savedEmployee = employee;
        savedEmployee.setEmployeeId(30);

        when(userDao.addUser(any(User.class), any()))
                .thenReturn(savedUser);

        when(employeeDao.addEmployee(any(Employee.class), any()))
                .thenReturn(savedEmployee);

        when(departmentDao.updateManagerId(
                eq(6),
                eq(30),
                any()
        )).thenReturn(true);

        // Act
        Employee actualResult =
                service.createUserAndEmployee(
                        user,
                        employee
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                30,
                actualResult.getEmployeeId()
        );

    }
}