package com.ers.dao;

import com.ers.model.Department;
import com.ers.model.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDepartmentDao {
    Department addDepartment(Department department);

    boolean updateDepartment(Department department);
    boolean updateManagerId(int departmentId, int employeeId, Connection con) throws SQLException;
    Department getDepartmentById(int departmentId);
    List<Department> getAllDepartments();
    boolean deleteDepartmentById(int departmentId);
    List<Employee> getEmployeesByDepartmentId(int departmentId);
    Department getDepartmentByManagerId(int managerId);
}
