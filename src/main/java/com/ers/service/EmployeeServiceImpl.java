package com.ers.service;

import com.ers.dao.IEmployeeDao;
import com.ers.model.Employee;

import java.util.List;
import java.util.logging.Logger;

public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger logger =
            Logger.getLogger(EmployeeServiceImpl.class.getName());

    private final IEmployeeDao employeeDao;

    public EmployeeServiceImpl(IEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee addEmployee(Employee employee) {

        // Validation
        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null."
            );
        }

        if (employee.getUserId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        if (employee.getFullName() == null ||
                employee.getFullName().isBlank()) {
            throw new IllegalArgumentException(
                    "Full name is required."
            );
        }

        if (employee.getEmail() == null ||
                employee.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required."
            );
        }

        if (!employee.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "Invalid email address."
            );
        }

        if (employee.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        Employee result =
                employeeDao.addEmployee(employee);

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add employee."
            );
        }

        logger.info(
                "Employee added successfully: "
                        + employee.getFullName()
        );

        return result;
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null."
            );
        }

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        if (employee.getUserId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        if (employee.getFullName() == null ||
                employee.getFullName().isBlank()) {
            throw new IllegalArgumentException(
                    "Full name is required."
            );
        }

        if (employee.getEmail() == null ||
                employee.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required."
            );
        }

        if (!employee.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "Invalid email address."
            );
        }

        if (employee.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        boolean result =
                employeeDao.updateEmployee(employee);

        if (result) {
            logger.info(
                    "Employee updated successfully: ID="
                            + employee.getEmployeeId()
            );
        }

        return result;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        Employee employee =
                employeeDao.getEmployeeById(employeeId);

        if (employee == null) {
            logger.warning(
                    "No employee found with ID="
                            + employeeId
            );
        }

        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeDao.getAllEmployees();
    }

    @Override
    public boolean deleteEmployeeById(int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        boolean result =
                employeeDao.deleteEmployeeById(employeeId);

        if (result) {
            logger.info(
                    "Employee deleted successfully: ID="
                            + employeeId
            );
        }

        return result;
    }
    @Override
    public Employee getEmployeeByUserId(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "User ID must be greater than 0"
            );
        }

        return employeeDao.getEmployeeByUserId(userId);
    }
}