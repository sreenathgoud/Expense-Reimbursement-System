package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.model.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceImplTest {

    @Test
    void addDepartment() {
        // Arrange
        IDepartmentDao departmentDaoMock =
                Mockito.mock(IDepartmentDao.class);

        Department department =
                new Department("Finance", null);

        Mockito.when(departmentDaoMock.addDepartment(department))
                .thenReturn(department);

        DepartmentServiceImpl departmentService =
                new DepartmentServiceImpl(departmentDaoMock);

        // Act
        Department actualResult =
                departmentService.addDepartment(department);

        // Assert
        Assertions.assertEquals(department, actualResult);
    }

    @Test
    void updateDepartment() {
        // Arrange
        IDepartmentDao departmentDaoMock =
                Mockito.mock(IDepartmentDao.class);

        Department department =
                new Department("Finance", null);

        department.setDepartmentId(1);

        Mockito.when(departmentDaoMock.updateDepartment(department))
                .thenReturn(true);

        DepartmentServiceImpl departmentService =
                new DepartmentServiceImpl(departmentDaoMock);

        // Act
        boolean actualResult =
                departmentService.updateDepartment(department);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getDepartmentById() {
    }

    @Test
    void getAllDepartments() {
        // Arrange
        IDepartmentDao departmentDaoMock =
                Mockito.mock(IDepartmentDao.class);

        Department department1 =
                new Department("Finance", null);

        Department department2 =
                new Department("HR", null);

        List<Department> departments =
                Arrays.asList(department1, department2);

        Mockito.when(departmentDaoMock.getAllDepartments())
                .thenReturn(departments);

        DepartmentServiceImpl departmentService =
                new DepartmentServiceImpl(departmentDaoMock);

        // Act
        List<Department> actualResult =
                departmentService.getAllDepartments();

        // Assert
        Assertions.assertEquals(departments, actualResult);
    }

    @Test
    void deleteDepartmentById() {
        // Arrange
        IDepartmentDao departmentDaoMock =
                Mockito.mock(IDepartmentDao.class);

        int departmentId = 1;

        Mockito.when(departmentDaoMock.deleteDepartmentById(departmentId))
                .thenReturn(true);

        DepartmentServiceImpl departmentService =
                new DepartmentServiceImpl(departmentDaoMock);

        // Act
        boolean actualResult =
                departmentService.deleteDepartmentById(departmentId);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}