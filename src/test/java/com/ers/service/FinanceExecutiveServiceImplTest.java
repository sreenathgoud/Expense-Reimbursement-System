package com.ers.service;

import com.ers.dao.IFinanceExecutiveDao;
import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

public class FinanceExecutiveServiceImplTest {

    @Test
    void addFinanceExecutiveTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Finance Executive",
                        "finance@gmail.com",
                        "FINANCE"
                );

        Mockito.when(
                financeExecutiveDao.addFinanceExecutive(
                        financeExecutive)
        ).thenReturn(financeExecutive);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        FinanceExecutive actualResult =
                service.addFinanceExecutive(
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
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Updated Finance Executive",
                        "updatedfinance@gmail.com",
                        "FINANCE"
                );

        Mockito.when(
                financeExecutiveDao.updateFinanceExecutive(
                        financeExecutive)
        ).thenReturn(true);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        boolean actualResult =
                service.updateFinanceExecutive(
                        financeExecutive
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getFinanceExecutiveByIdTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Finance Executive",
                        "finance@gmail.com",
                        "FINANCE"
                );

        Mockito.when(
                financeExecutiveDao.getFinanceExecutiveById(20)
        ).thenReturn(financeExecutive);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        FinanceExecutive actualResult =
                service.getFinanceExecutiveById(20);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                20,
                actualResult.getEmployeeId()
        );
    }


    @Test
    void getAllFinanceExecutivesTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        List<FinanceExecutive> financeExecutives =
                List.of(
                        new FinanceExecutive(
                                20,
                                "Finance Executive",
                                "finance@gmail.com",
                                "FINANCE"
                        )
                );

        Mockito.when(
                financeExecutiveDao.getAllFinanceExecutives()
        ).thenReturn(financeExecutives);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        List<FinanceExecutive> actualResult =
                service.getAllFinanceExecutives();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
    }


    @Test
    void deleteFinanceExecutiveByIdTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        Mockito.when(
                financeExecutiveDao.deleteFinanceExecutiveById(20)
        ).thenReturn(true);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        boolean actualResult =
                service.deleteFinanceExecutiveById(20);

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getPendingClaimsTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        List<ExpenseClaim> claims =
                List.of(
                        new ExpenseClaim(
                                10,
                                "Travel expense",
                                2000.00,
                                LocalDate.now(),
                                "APPROVED",
                                null
                        )
                );

        Mockito.when(
                financeExecutiveDao.getPendingClaims()
        ).thenReturn(claims);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        List<ExpenseClaim> actualResult =
                service.getPendingClaims();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
    }


    @Test
    void getClaimByIdTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        ExpenseClaim claim =
                new ExpenseClaim(
                        10,
                        "Travel expense",
                        2000.00,
                        LocalDate.now(),
                        "APPROVED",
                        null
                );

        claim.setClaimId(3);

        Mockito.when(
                financeExecutiveDao.getClaimById(3)
        ).thenReturn(claim);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        ExpenseClaim actualResult =
                service.getClaimById(3);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                3,
                actualResult.getClaimId()
        );
    }


    @Test
    void processPaymentTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        Mockito.when(
                financeExecutiveDao.processPayment(
                        3,
                        20,
                        "BANK_TRANSFER"
                )
        ).thenReturn(true);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        boolean actualResult =
                service.processPayment(
                        3,
                        20,
                        "BANK_TRANSFER"
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getReimbursementHistoryTest() {

        // Arrange
        IFinanceExecutiveDao financeExecutiveDao =
                Mockito.mock(IFinanceExecutiveDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        reimbursement.setReimbursementId(1);

        List<Reimbursement> reimbursements =
                List.of(reimbursement);

        Mockito.when(
                financeExecutiveDao.getReimbursementHistory(20)
        ).thenReturn(reimbursements);

        FinanceExecutiveServiceImpl service =
                new FinanceExecutiveServiceImpl(
                        financeExecutiveDao
                );

        // Act
        List<Reimbursement> actualResult =
                service.getReimbursementHistory(20);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
        Assertions.assertEquals(
                20,
                actualResult.get(0).getProcessedBy()
        );
    }
}