package com.ers.dao;

import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements IDepartmentDao{
    @Override
    public Department addDepartment(Department department) {
            String sql =
                    "INSERT INTO departments (department_name, manager_id) " +
                            "VALUES (?, ?)";

            try (
                    Connection con = JDBCUtil.getConnection();
                    PreparedStatement ps =
                            con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {

                ps.setString(1, department.getDepartmentName());
                if (department.getManagerId() == null) {
                    ps.setNull(2, Types.INTEGER);
                } else {
                    ps.setInt(2, department.getManagerId());
                }

                int count = ps.executeUpdate();

                if (count > 0) {

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            department.setDepartmentId(rs.getInt(1));
                        }
                    }

                    System.out.println("Department added successfully.");
                    return department;
                }

            } catch ( SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public boolean updateDepartment(Department department) {
        String sql =
                "UPDATE departments SET department_name = ?, manager_id = ? " +
                        "WHERE department_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, department.getDepartmentName());

            if (department.getManagerId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, department.getManagerId());
            }

            ps.setInt(3, department.getDepartmentId());

            int count = ps.executeUpdate();

            if (count > 0) {
                System.out.println("Department updated successfully");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        String sql =
                "SELECT * FROM departments WHERE department_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Integer managerId =
                            rs.getObject("manager_id", Integer.class);

                    Department department = new Department(
                            rs.getString("department_name"),
                            managerId
                    );

                    department.setDepartmentId(
                            rs.getInt("department_id")
                    );

                    return department;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();

        String sql = "SELECT * FROM departments";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Integer managerId =
                        rs.getObject("manager_id", Integer.class);

                Department department = new Department(
                        rs.getString("department_name"),
                        managerId
                );

                department.setDepartmentId(
                        rs.getInt("department_id")
                );

                departments.add(department);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    @Override
    public boolean deleteDepartmentById(int departmentId) {
        String sql =
                "DELETE FROM departments WHERE department_id = ?";

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, departmentId);

            int count = ps.executeUpdate();

            if (count > 0) {
                System.out.println("Department deleted successfully");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
