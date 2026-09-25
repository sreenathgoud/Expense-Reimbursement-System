package com.ers.service;

import com.ers.dao.IEmployeeDao;
import com.ers.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    @Test
    void addEmployee() {
        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Employee employee = new Employee(
                2,
                "Sreenath",
                "sreenath@gmail.com",
                6
        );

        Mockito.when(employeeDaoMock.addEmployee(employee))
                .thenReturn(employee);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        Employee actualResult =
                employeeService.addEmployee(employee);

        // Assert
        Assertions.assertEquals(employee, actualResult);
    }

    @Test
    void updateEmployee() {
        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Employee employee = new Employee(
                2,
                "Sreenath Updated",
                "sreenath.updated@gmail.com",
                6
        );

        employee.setEmployeeId(9);

        Mockito.when(employeeDaoMock.updateEmployee(employee))
                .thenReturn(true);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        boolean actualResult =
                employeeService.updateEmployee(employee);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getEmployeeById() {
        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Employee employee = new Employee(
                2,
                "Sreenath",
                "sreenath@gmail.com",
                6
        );

        employee.setEmployeeId(9);

        Mockito.when(employeeDaoMock.getEmployeeById(9))
                .thenReturn(employee);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        Employee actualResult =
                employeeService.getEmployeeById(9);

        // Assert
        Assertions.assertEquals(employee, actualResult);
    }

    @Test
    void getAllEmployees() {
        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        List<Employee> employees = List.of(
                new Employee(
                        2,
                        "Sreenath",
                        "sreenath@gmail.com",
                        6
                )
        );

        Mockito.when(employeeDaoMock.getAllEmployees())
                .thenReturn(employees);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        List<Employee> actualResult =
                employeeService.getAllEmployees();

        // Assert
        Assertions.assertEquals(employees, actualResult);
    }

    @Test
    void deleteEmployeeById() {
        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Mockito.when(employeeDaoMock.deleteEmployeeById(9))
                .thenReturn(true);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        boolean actualResult =
                employeeService.deleteEmployeeById(9);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}