package com.ers.dao;

import com.ers.model.Employee;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoImplTest {

  @Test
    void addEmployee() {

        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        Employee employee = new Employee(
                3,
                "king",
                "king.employee@gmail.com",
                6
        );

        // Act
        Employee actualResult =
                employeeDao.addEmployee(employee);

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void updateEmployee() {
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        Employee employee = new Employee(
                3,
                "Sreenath Updated",
                "sreenath.updated@gmail.com",
                6
        );

        employee.setEmployeeId(10);

        // Act
        boolean actualResult =
                employeeDao.updateEmployee(employee);

        // Assert
        Assertions.assertTrue(actualResult);
    }

   @Test
    void getEmployeeById() {
        // Arrange
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        int employeeId = 9;

        // Act
        Employee actualResult =
                employeeDao.getEmployeeById(employeeId);

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void getAllEmployees() {
        // Arrange
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        // Act
        List<Employee> actualResult =
                employeeDao.getAllEmployees();

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void deleteEmployeeById() {
        // Arrange
        IEmployeeDao employeeDao =
                new EmployeeDaoImpl(new JDBCUtil());

        int employeeId = 9;

        // Act
        boolean actualResult =
                employeeDao.deleteEmployeeById(employeeId);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}