package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.model.Department;
import com.ers.model.Employee;

import java.util.List;
import java.util.logging.Logger;

public class DepartmentServiceImpl implements IDepartmentService {

    private static final Logger logger =
            Logger.getLogger(DepartmentServiceImpl.class.getName());

    private final IDepartmentDao departmentDao;

    public DepartmentServiceImpl(
            IDepartmentDao departmentDao) {

        this.departmentDao = departmentDao;
    }

    @Override
    public Department addDepartment(
            Department department) {

        // Validation
        if (department == null) {
            throw new IllegalArgumentException(
                    "Department cannot be null."
            );
        }

        if (department.getDepartmentName() == null ||
                department.getDepartmentName().isBlank()) {
            throw new IllegalArgumentException(
                    "Department name is required."
            );
        }

        Department result =
                departmentDao.addDepartment(department);

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add department."
            );
        }

        logger.info(
                "Department added successfully: "
                        + department.getDepartmentName()
        );

        return result;
    }

    @Override
    public boolean updateDepartment(
            Department department) {

        if (department == null) {
            throw new IllegalArgumentException(
                    "Department cannot be null."
            );
        }

        if (department.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        if (department.getDepartmentName() == null ||
                department.getDepartmentName().isBlank()) {
            throw new IllegalArgumentException(
                    "Department name is required."
            );
        }

        boolean result =
                departmentDao.updateDepartment(department);

        if (result) {
            logger.info(
                    "Department updated successfully: ID="
                            + department.getDepartmentId()
            );
        }

        return result;
    }

    @Override
    public Department getDepartmentById(
            int departmentId) {

        if (departmentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        Department department =
                departmentDao.getDepartmentById(departmentId);

        if (department == null) {
            logger.warning(
                    "No department found with ID="
                            + departmentId
            );
        }

        return department;
    }

    @Override
    public List<Department> getAllDepartments() {

        return departmentDao.getAllDepartments();
    }

    @Override
    public boolean deleteDepartmentById(
            int departmentId) {

        if (departmentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        boolean result =
                departmentDao.deleteDepartmentById(
                        departmentId
                );

        if (result) {
            logger.info(
                    "Department deleted successfully: ID="
                            + departmentId
            );
        }

        return result;
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(
            int departmentId) {

        if (departmentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        return departmentDao.getEmployeesByDepartmentId(
                departmentId
        );
    }

    @Override
    public Department getDepartmentByManagerId(
            int managerId) {

        if (managerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid manager ID."
            );
        }

        Department department =
                departmentDao.getDepartmentByManagerId(
                        managerId
                );

        if (department == null) {
            logger.warning(
                    "No department found for manager ID="
                            + managerId
            );
        }

        return department;
    }
}