package com.ers.dao;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FinanceExecutiveDaoImplTest {

    private final IFinanceExecutiveDao financeExecutiveDao =
            new FinanceExecutiveDaoImpl(new JDBCUtil());


    @Test
    void addFinanceExecutiveTest() {

        // Arrange
        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Finance Executive",
                        "finance@gmail.com",
                        "FINANCE"
                );

        // Act
        FinanceExecutive actualResult =
                financeExecutiveDao.addFinanceExecutive(
                        financeExecutive
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                20,
                actualResult.getEmployeeId()
        );
    }


    @Test
    void updateFinanceExecutiveTest() {

        // Arrange
        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Updated Finance Executive",
                        "updatedfinance@gmail.com",
                        "FINANCE"
                );

        // Act
        boolean actualResult =
                financeExecutiveDao.updateFinanceExecutive(
                        financeExecutive
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getFinanceExecutiveByIdTest() {

        // Arrange
        int employeeId = 20;

        // Act
        FinanceExecutive actualResult =
                financeExecutiveDao.getFinanceExecutiveById(
                        employeeId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                employeeId,
                actualResult.getEmployeeId()
        );
    }


    @Test
    void getAllFinanceExecutivesTest() {

        // Arrange

        // Act
        List<FinanceExecutive> actualResult =
                financeExecutiveDao.getAllFinanceExecutives();

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void getPendingClaimsTest() {

        // Arrange

        // Act
        List<ExpenseClaim> actualResult =
                financeExecutiveDao.getPendingClaims();

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void getClaimByIdTest() {

        // Arrange
        int claimId = 1;

        // Act
        ExpenseClaim actualResult =
                financeExecutiveDao.getClaimById(claimId);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                claimId,
                actualResult.getClaimId()
        );
    }


    @Test
    void getReimbursementHistoryTest() {

        // Arrange
        int financeExecutiveId = 20;

        // Act
        List<Reimbursement> actualResult =
                financeExecutiveDao.getReimbursementHistory(
                        financeExecutiveId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isEmpty());
        Assertions.assertEquals(
                20,
                actualResult.get(0).getProcessedBy()
        );
    }
    @Test
    void processPaymentTest() {

        // Arrange
        int claimId = 3;
        int financeExecutiveId = 20;
        String paymentMode = "BANK_TRANSFER";

        // Act
        boolean actualResult =
                financeExecutiveDao.processPayment(
                        claimId,
                        financeExecutiveId,
                        paymentMode
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }
}