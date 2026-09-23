package com.ers.controller;

import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.service.IDepartmentService;

import java.util.List;

public class DepartmentController {
    private IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public Department addDepartment(Department department) {
        return departmentService.addDepartment(department);
    }

    public boolean updateDepartment(Department department) {
        return departmentService.updateDepartment(department);
    }

    public Department getDepartmentById(int departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    public boolean deleteDepartmentById(int departmentId) {
        return departmentService.deleteDepartmentById(departmentId);
    }

    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        return null;
    }

    public Department getDepartmentByManagerId(int managerId) {
        return null;
    }
}
