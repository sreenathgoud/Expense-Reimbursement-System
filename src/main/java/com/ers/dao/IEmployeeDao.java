package com.ers.dao;

import com.ers.model.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDao {

    Employee addEmployee(Employee employee);

    Employee addEmployee(Employee employee, Connection con)
            throws SQLException;

    boolean updateEmployee(Employee employee);

    Employee getEmployeeById(int employeeId);

    Employee getEmployeeByUserId(int userId);

    List<Employee> getAllEmployees();

    boolean deleteEmployeeById(int employeeId);
}