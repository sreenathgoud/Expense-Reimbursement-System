package com.ers.dao;

import com.ers.model.Employee;
import com.ers.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;

public class EmployeeDaoImpl implements IEmployeeDao {
    JDBCUtil jdbcUtil;
    public EmployeeDaoImpl(JDBCUtil jdbcUtil){
        this.jdbcUtil=jdbcUtil;
    }
    //Crud operation will be done here
    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeDaoImpl.class);
    private final String addQuery =
            "INSERT INTO employees(user_id, full_name, email, department_id) " +
                    "VALUES (?, ?, ?, ?)";
    private final String updateQuery =
            "UPDATE employees " +
                    "SET user_id = ?, full_name = ?, email = ?, department_id = ? " +
                    "WHERE employee_id = ?";
   private final String getQuery =
            "SELECT * FROM employees WHERE employee_id = ?";
   private final   String selectQuery = "SELECT * FROM employees";
    private final String removeQuery =
            "DELETE FROM employees WHERE employee_id = ?";
   private final String addcon =
            "INSERT INTO employees(user_id, full_name, email, department_id) " +
                    "VALUES (?, ?, ?, ?)";

    @Override
    public Employee addEmployee(Employee employee) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)
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
                logger.info("employee added successfully");
//                System.out.println("Employee added successfully.");
                 return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee addEmployee(Employee employee, Connection con) throws SQLException {
        try (
                PreparedStatement ps =
                        con.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, employee.getUserId());
            ps.setString(2, employee.getFullName());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getDepartmentId());

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setEmployeeId(rs.getInt(1));
                    }
                }

                logger.info("Employee added successfully through transaction");

                return employee;
            }
        }
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(updateQuery)
        ) {

            ps.setInt(1, employee.getUserId());
            ps.setString(2, employee.getFullName());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, employee.getEmployeeId());

            int count = ps.executeUpdate();

            if (count > 0) {
                logger.info("User updated successfully");
//                System.out.println("Employee updated successfully.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(getQuery)
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

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(selectQuery);
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

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(removeQuery)
        ) {

            ps.setInt(1, employeeId);

            int count = ps.executeUpdate();

            if (count > 0) {
                logger.info("Employee deleted successfully");
//                System.out.println("Employee deleted successfully.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
