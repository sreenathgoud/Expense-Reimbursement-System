package com.ers.dao;

import com.ers.model.ExpenseClaim;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class ExpenseClaimDaoImplTest {

    @Test
    void addExpenseClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        21,
                        "Travel expenses",
                        2500.50,
                        LocalDate.now(),
                        "DRAFT",
                        "travel_bill.pdf"
                );

        // Act
        ExpenseClaim actualResult =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        // Assert
        Assertions.assertNotNull(actualResult);

        Assertions.assertTrue(
                actualResult.getClaimId() > 0
        );
    }

    @Test
    void updateExpenseClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Updated travel expenses",
                        3000.00,
                        LocalDate.now(),
                        "DRAFT",
                        "updated_bill.pdf"
                );

        expenseClaim.setClaimId(1);

        // Act
        boolean actualResult =
                expenseClaimDao.updateExpenseClaim(
                        expenseClaim
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getExpenseClaimById() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        int claimId = 1;

        // Act
        ExpenseClaim actualResult =
                expenseClaimDao.getExpenseClaimById(
                        claimId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void getAllExpenseClaims() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimDao.getAllExpenseClaims();

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void deleteExpenseClaimById() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Temporary claim",
                        1000.00,
                        LocalDate.now(),
                        "DRAFT",
                        "temporary.pdf"
                );

        ExpenseClaim savedClaim =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        int claimId =
                savedClaim.getClaimId();

        // Act
        boolean actualResult =
                expenseClaimDao.deleteExpenseClaimById(
                        claimId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getClaimsByEmployeeId() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        int employeeId = 10;

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimDao.getClaimsByEmployeeId(
                        employeeId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void submitClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Submission test",
                        1500.00,
                        LocalDate.now(),
                        "DRAFT",
                        "submit.pdf"
                );

        ExpenseClaim savedClaim =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        int claimId =
                savedClaim.getClaimId();

        // Act
        boolean actualResult =
                expenseClaimDao.submitClaim(
                        claimId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void approveClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Approval test",
                        2000.00,
                        LocalDate.now(),
                        "SUBMITTED",
                        "approval.pdf"
                );

        ExpenseClaim savedClaim =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        int claimId =
                savedClaim.getClaimId();

        // Act
        boolean actualResult =
                expenseClaimDao.approveClaim(
                        claimId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void rejectClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Rejection test",
                        1800.00,
                        LocalDate.now(),
                        "SUBMITTED",
                        "reject.pdf"
                );

        ExpenseClaim savedClaim =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        int claimId =
                savedClaim.getClaimId();

        // Act
        boolean actualResult =
                expenseClaimDao.rejectClaim(
                        claimId,
                        "Invalid expense"
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getClaimsByStatus() {

        // Arrange
        IExpenseClaimDao expenseClaimDao =
                new ExpenseClaimDaoImpl(new JDBCUtil());

        String status = "DRAFT";

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimDao.getClaimsByStatus(
                        status
                );

        // Assert
        Assertions.assertNotNull(actualResult);
    }
}