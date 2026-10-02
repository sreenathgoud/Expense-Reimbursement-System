package com.ers.service;

import com.ers.dao.IDepartmentDao;
import com.ers.dao.IEmployeeDao;
import com.ers.dao.IFinanceExecutiveDao;
import com.ers.dao.IUserDao;
import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.model.FinanceExecutive;
import com.ers.model.User;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseReimbursementServiceTest {

    @Test
    void createUserAndEmployeeAutomaticallyCreatesFinanceExecutive() throws Exception {
        IUserDao userDao = Mockito.mock(IUserDao.class);
        IEmployeeDao employeeDao = Mockito.mock(IEmployeeDao.class);
        IDepartmentDao departmentDao = Mockito.mock(IDepartmentDao.class);
        IFinanceExecutiveDao financeExecutiveDao = Mockito.mock(IFinanceExecutiveDao.class);
        Connection connection = Mockito.mock(Connection.class);
        User user = new User("financeuser", "password", "FINANCE_EXECUTIVE", true, LocalDateTime.now());
        User savedUser = new User("financeuser", "password", "FINANCE_EXECUTIVE", true, LocalDateTime.now());
        savedUser.setUserId(46);
        Employee employee = new Employee(0, "Suru Harshit", "suruharshit@gmail.com", 13);
        Employee savedEmployee = new Employee(46, "Suru Harshit", "suruharshit@gmail.com", 13);
        savedEmployee.setEmployeeId(48);

        when(departmentDao.getDepartmentById(13)).thenReturn(Mockito.mock(Department.class));
        when(userDao.addUser(same(user), same(connection))).thenReturn(savedUser);
        when(employeeDao.addEmployee(same(employee), same(connection))).thenReturn(savedEmployee);
        when(financeExecutiveDao.addFinanceExecutive(any(FinanceExecutive.class), same(connection)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExpenseReimbursementService service = new ExpenseReimbursementService(
                userDao,
                employeeDao,
                departmentDao,
                financeExecutiveDao
        );

        try (MockedStatic<JDBCUtil> jdbcUtil = Mockito.mockStatic(JDBCUtil.class)) {
            jdbcUtil.when(JDBCUtil::getConnection).thenReturn(connection);

            Employee result = service.createUserAndEmployee(user, employee);

            Assertions.assertSame(savedEmployee, result);
            ArgumentCaptor<FinanceExecutive> financeExecutiveCaptor =
                    ArgumentCaptor.forClass(FinanceExecutive.class);
            verify(financeExecutiveDao).addFinanceExecutive(financeExecutiveCaptor.capture(), same(connection));
            Assertions.assertEquals(48, financeExecutiveCaptor.getValue().getEmployeeId());
            Assertions.assertEquals(13, financeExecutiveCaptor.getValue().getDepartmentId());
            verify(connection).commit();
            verify(connection, never()).rollback();
        }
    }
}