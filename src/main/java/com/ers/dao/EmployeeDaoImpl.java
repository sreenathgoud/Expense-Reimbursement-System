package com.ers.dao;

import com.ers.model.Employee;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements IEmployeeDao {
    JDBCUtil jdbcUtil;
    public EmployeeDaoImpl(JDBCUtil jdbcUtil){
        this.jdbcUtil=jdbcUtil;
    }
    //Crud operation will be done here
    @Override
    public Employee addEmployee(Employee employee) {

        String sql =
                "INSERT INTO employees(user_id, full_name, email, department_id) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, employee.getUserId());
            ps.setString(2, employee.getFullName());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getDepartmentId());

            int count = ps.executeUpdate();

            if (count > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    employee.setEmployeeId(rs.getInt(1));
                }

                System.out.println("Employee added successfully.");
                 return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String sql =
                "UPDATE employees " +
                        "SET user_id = ?, full_name = ?, email = ?, department_id = ? " +
                        "WHERE employee_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, employee.getUserId());
            ps.setString(2, employee.getFullName());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, employee.getEmployeeId());

            int count = ps.executeUpdate();

            if (count > 0) {
                System.out.println("Employee updated successfully.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        String sql =
                "SELECT * FROM employees WHERE employee_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("department_id")
                );

                employee.setEmployeeId(
                        rs.getInt("employee_id")
                );

                return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("department_id")
                );

                employee.setEmployeeId(
                        rs.getInt("employee_id")
                );

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean deleteEmployeeById(int employeeId) {
        String sql =
                "DELETE FROM employees WHERE employee_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, employeeId);

            int count = ps.executeUpdate();

            if (count > 0) {
                System.out.println("Employee deleted successfully.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
