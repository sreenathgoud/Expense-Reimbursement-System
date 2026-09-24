package com.ers.dao;

import com.ers.model.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoImplTest {

    @Test
    void addDepartment() {
        IDepartmentDao departmentDao = new DepartmentDaoImpl();

        Department department = new Department(
                "IT",
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
        int departmentId = 4;

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