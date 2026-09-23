package com.ers.controller;

import com.ers.model.Department;
import com.ers.service.IDepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentControllerTest {

    @Test
    void addDepartment() {
        // Arrange
        IDepartmentService departmentServiceMock =
                Mockito.mock(IDepartmentService.class);

        Department department =
                new Department("Finance", null);

        Mockito.when(departmentServiceMock.addDepartment(department))
                .thenReturn(department);

        DepartmentController departmentController =
                new DepartmentController(departmentServiceMock);

        // Act
        Department actualResult =
                departmentController.addDepartment(department);

        // Assert
        Assertions.assertEquals(department, actualResult);
    }

    @Test
    void updateDepartment() {
        // Arrange
        IDepartmentService departmentServiceMock =
                Mockito.mock(IDepartmentService.class);

        Department department =
                new Department("Finance", null);

        department.setDepartmentId(1);

        Mockito.when(departmentServiceMock.updateDepartment(department))
                .thenReturn(true);

        DepartmentController departmentController =
                new DepartmentController(departmentServiceMock);

        // Act
        boolean actualResult =
                departmentController.updateDepartment(department);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getDepartmentById() {
        // Arrange
        IDepartmentService departmentServiceMock =
                Mockito.mock(IDepartmentService.class);

        int departmentId = 1;

        Department department =
                new Department("Finance", null);

        department.setDepartmentId(departmentId);

        Mockito.when(departmentServiceMock.getDepartmentById(departmentId))
                .thenReturn(department);

        DepartmentController departmentController =
                new DepartmentController(departmentServiceMock);

        // Act
        Department actualResult =
                departmentController.getDepartmentById(departmentId);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(departmentId,
                actualResult.getDepartmentId());
    }

    @Test
    void getAllDepartments() {
        // Arrange
        IDepartmentService departmentServiceMock =
                Mockito.mock(IDepartmentService.class);

        Department department1 =
                new Department("Finance", null);

        Department department2 =
                new Department("HR", null);

        List<Department> departments =
                Arrays.asList(department1, department2);

        Mockito.when(departmentServiceMock.getAllDepartments())
                .thenReturn(departments);

        DepartmentController departmentController =
                new DepartmentController(departmentServiceMock);

        // Act
        List<Department> actualResult =
                departmentController.getAllDepartments();

        // Assert
        Assertions.assertEquals(departments, actualResult);
    }

    @Test
    void deleteDepartmentById() {
        // Arrange
        IDepartmentService departmentServiceMock =
                Mockito.mock(IDepartmentService.class);

        int departmentId = 1;

        Mockito.when(departmentServiceMock.deleteDepartmentById(departmentId))
                .thenReturn(true);

        DepartmentController departmentController =
                new DepartmentController(departmentServiceMock);

        // Act
        boolean actualResult =
                departmentController.deleteDepartmentById(departmentId);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}