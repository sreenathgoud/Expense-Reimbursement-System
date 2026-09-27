package com.ers.service;

import com.ers.dao.IEmployeeDao;
import com.ers.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

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

        Mockito.when(
                employeeDaoMock.addEmployee(employee)
        ).thenReturn(employee);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        Employee actualResult =
                employeeService.addEmployee(employee);

        // Assert
        Assertions.assertEquals(
                employee,
                actualResult
        );
    }

    @Test
    void addEmployeeInvalidEmployee() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.addEmployee(null)
        );
    }

    @Test
    void addEmployeeInvalidEmail() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Employee employee = new Employee(
                2,
                "Sreenath",
                "invalid-email",
                6
        );

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.addEmployee(employee)
        );
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

        Mockito.when(
                employeeDaoMock.updateEmployee(employee)
        ).thenReturn(true);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        boolean actualResult =
                employeeService.updateEmployee(employee);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void updateEmployeeInvalidId() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Employee employee = new Employee(
                2,
                "Sreenath",
                "sreenath@gmail.com",
                6
        );

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.updateEmployee(employee)
        );
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

        Mockito.when(
                employeeDaoMock.getEmployeeById(9)
        ).thenReturn(employee);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        Employee actualResult =
                employeeService.getEmployeeById(9);

        // Assert
        Assertions.assertEquals(
                employee,
                actualResult
        );
    }

    @Test
    void getEmployeeByIdInvalidId() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.getEmployeeById(0)
        );
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

        Mockito.when(
                employeeDaoMock.getAllEmployees()
        ).thenReturn(employees);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        List<Employee> actualResult =
                employeeService.getAllEmployees();

        // Assert
        Assertions.assertEquals(
                employees,
                actualResult
        );
    }

    @Test
    void deleteEmployeeById() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        Mockito.when(
                employeeDaoMock.deleteEmployeeById(9)
        ).thenReturn(true);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act
        boolean actualResult =
                employeeService.deleteEmployeeById(9);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void deleteEmployeeByIdInvalidId() {

        // Arrange
        IEmployeeDao employeeDaoMock =
                Mockito.mock(IEmployeeDao.class);

        EmployeeServiceImpl employeeService =
                new EmployeeServiceImpl(employeeDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.deleteEmployeeById(0)
        );
    }
}