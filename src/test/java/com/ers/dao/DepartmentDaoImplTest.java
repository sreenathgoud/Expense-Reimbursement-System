package com.ers.dao;

import com.ers.model.Department;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoImplTest {

    @Test
    void addDepartment() {
        IDepartmentDao departmentDao = new DepartmentDaoImpl();

        Department department = new Department(
                "testing departement",
                null
        );

        // Act
        Department actualResult =
                departmentDao.addDepartment(department);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(actualResult.getDepartmentId() > 0);
    }
    @Test
    void updateManagerIdTest() throws SQLException, SQLException {

        // Arrange
        IDepartmentDao departmentDao =
                new DepartmentDaoImpl();

        int departmentId = 6;
        int employeeId = 10;

        Connection con = JDBCUtil.getConnection();
        con.setAutoCommit(false);

        // Act
        boolean actualResult =
                departmentDao.updateManagerId(
                        departmentId,
                        employeeId,
                        con
                );

        // Assert
        Assertions.assertTrue(actualResult);

        con.rollback();
        con.close();
    }
    @Test
    void updateDepartment() {
        // Arrange
        IDepartmentDao departmentDao = new DepartmentDaoImpl();

        // Use an existing department_id from your database
        Department department = new Department(
                "HR",
                null
        );

        department.setDepartmentId(6);

        // Act
        boolean actualResult =
                departmentDao.updateDepartment(department);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getAllDepartments() {
        // Arrange
        IDepartmentDao departmentDao = new DepartmentDaoImpl();

        // Act
        List<Department> actualResult =
                departmentDao.getAllDepartments();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isEmpty());
    }

    @Test
    void deleteDepartmentById() {
        // Arrange
        IDepartmentDao departmentDao = new DepartmentDaoImpl();

        // Use an existing department_id that can be deleted
        int departmentId = 7;

        // Act
        boolean actualResult =
                departmentDao.deleteDepartmentById(departmentId);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getEmployeesByDepartmentId() {
    }
}