package com.ers.controller;

import com.ers.model.Employee;
import com.ers.service.IEmployeeService;

import java.util.List;

public class EmployeeController {
    private IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService){
        this.employeeService = employeeService;
    }

    public Employee addNewEmployee(Employee employee){
        return employeeService.addEmployee(employee);
    }
    public boolean updateEmployee(Employee employee){
        return employeeService.updateEmployee(employee);
    }
    public Employee getEmployeeById(int employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();

    }
    public boolean deleteEmployeeById(int employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }
}
