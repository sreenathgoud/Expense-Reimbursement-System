package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.service.IFinanceExecutiveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

public class FinanceExecutiveControllerTest {

    @Test
    void addFinanceExecutiveForAdminDeniesNonAdmin() {
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);
        FinanceExecutiveController controller =
                new FinanceExecutiveController(service, null, null, "FINANCE_EXECUTIVE");

        Assertions.assertFalse(controller.addFinanceExecutiveForAdmin());
        Mockito.verifyNoInteractions(service);
    }

    @Test
    void addNewFinanceExecutiveTest() {

        // Arrange
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Finance Executive",
                        "finance@gmail.com",
                        1
                );

        Mockito.when(
                service.addFinanceExecutive(
                        financeExecutive)
        ).thenReturn(financeExecutive);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        FinanceExecutive actualResult =
                controller.addNewFinanceExecutive(
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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Updated Finance Executive",
                        "updatedfinance@gmail.com",
                        3
                );

        Mockito.when(
                service.updateFinanceExecutive(
                        financeExecutive)
        ).thenReturn(true);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        boolean actualResult =
                controller.updateFinanceExecutive(
                        financeExecutive
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getFinanceExecutiveByIdTest() {

        // Arrange
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        20,
                        "Finance Executive",
                        "finance@gmail.com",
                        1
                );

        Mockito.when(
                service.getFinanceExecutiveById(20)
        ).thenReturn(financeExecutive);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        FinanceExecutive actualResult =
                controller.getFinanceExecutiveById(20);

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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        List<FinanceExecutive> financeExecutives =
                List.of(
                        new FinanceExecutive(
                                20,
                                "Finance Executive",
                                "finance@gmail.com",
                                2
                        )
                );

        Mockito.when(
                service.getAllFinanceExecutives()
        ).thenReturn(financeExecutives);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        List<FinanceExecutive> actualResult =
                controller.getAllFinanceExecutives();

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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        Mockito.when(
                service.deleteFinanceExecutiveById(20)
        ).thenReturn(true);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        boolean actualResult =
                controller.deleteFinanceExecutiveById(20);

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getPendingClaimsTest() {

        // Arrange
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

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
                service.getPendingClaims()
        ).thenReturn(claims);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        List<ExpenseClaim> actualResult =
                controller.getPendingClaims();

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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

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
                service.getClaimById(3)
        ).thenReturn(claim);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        ExpenseClaim actualResult =
                controller.getClaimById(3);

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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

        Mockito.when(
                service.processPayment(
                        3,
                        20,
                        "BANK_TRANSFER"
                )
        ).thenReturn(true);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        boolean actualResult =
                controller.processPayment(
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
        IFinanceExecutiveService service =
                Mockito.mock(IFinanceExecutiveService.class);

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
                service.getReimbursementHistory(20)
        ).thenReturn(reimbursements);

        FinanceExecutiveController controller =
                new FinanceExecutiveController(service);

        // Act
        List<Reimbursement> actualResult =
                controller.getReimbursementHistory(20);

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