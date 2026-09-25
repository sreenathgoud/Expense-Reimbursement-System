package com.ers.service;

import com.ers.dao.EmployeeDaoImpl;
import com.ers.dao.IEmployeeDao;
import com.ers.model.Employee;

import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService{
    IEmployeeDao employeeDao;
    public EmployeeServiceImpl(IEmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }
    //Write business logic here

    @Override
    public Employee addEmployee(Employee employee) {

        if (employee == null) {
            return null;
        }

        if (employee.getUserId() <= 0) {
            return null;
        }

        if (employee.getFullName() == null ||
                employee.getFullName().trim().isEmpty()) {
            return null;
        }

        if (employee.getEmail() == null ||
                employee.getEmail().trim().isEmpty()) {
            return null;
        }

        if (!employee.getEmail().contains("@")) {
            return null;
        }

        if (employee.getDepartmentId() <= 0) {
            return null;
        }

        return employeeDao.addEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }

        if (employee.getEmployeeId() <= 0) {
            return false;
        }

        if (employee.getUserId() <= 0) {
            return false;
        }

        if (employee.getFullName() == null ||
                employee.getFullName().trim().isEmpty()) {
            return false;
        }

        if (employee.getEmail() == null ||
                employee.getEmail().trim().isEmpty()) {
            return false;
        }

        if (!employee.getEmail().contains("@")) {
            return false;
        }

        if (employee.getDepartmentId() <= 0) {
            return false;
        }

        return employeeDao.updateEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        if (employeeId <= 0) {
            return null;
        }

        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public boolean deleteEmployeeById(int employeeId) {
        if (employeeId <= 0) {
            return false;
        }

        return employeeDao.deleteEmployeeById(employeeId);
    }
}
