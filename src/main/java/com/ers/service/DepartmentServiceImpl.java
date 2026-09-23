package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.model.Department;
import com.ers.model.Employee;

import java.util.List;

public class DepartmentServiceImpl implements IDepartmentService{

    private IDepartmentDao departmentDao;

    public DepartmentServiceImpl(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
    @Override
    public Department addDepartment(Department department) {
       return departmentDao.addDepartment(department);
    }

    @Override
    public boolean updateDepartment(Department department) {
        return departmentDao.updateDepartment(department);
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        return departmentDao.getDepartmentById(departmentId);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    @Override
    public boolean deleteDepartmentById(int departmentId) {
        return departmentDao.deleteDepartmentById(departmentId);
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        return List.of();
    }

    @Override
    public Department getDepartmentByManagerId(int managerId) {
        return null;
    }
}
