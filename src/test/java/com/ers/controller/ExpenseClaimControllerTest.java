package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.service.IExpenseClaimService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class ExpenseClaimControllerTest {

    @Test
    void addExpenseClaim() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Travel expenses",
                        2500.50,
                        LocalDate.now(),
                        "DRAFT",
                        "travel.pdf"
                );

        Mockito.when(
                expenseClaimServiceMock.addExpenseClaim(
                        expenseClaim
                )
        ).thenReturn(expenseClaim);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        ExpenseClaim actualResult =
                controller.addExpenseClaim(
                        expenseClaim
                );

        // Assert
        Assertions.assertEquals(
                expenseClaim,
                actualResult
        );
    }

    @Test
    void updateExpenseClaim() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Updated travel",
                        3000.00,
                        LocalDate.now(),
                        "DRAFT",
                        "updated.pdf"
                );

        expenseClaim.setClaimId(1);

        Mockito.when(
                expenseClaimServiceMock.updateExpenseClaim(
                        expenseClaim
                )
        ).thenReturn(true);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        boolean actualResult =
                controller.updateExpenseClaim(
                        expenseClaim
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getExpenseClaimById() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Travel expenses",
                        2500.50,
                        LocalDate.now(),
                        "DRAFT",
                        "travel.pdf"
                );

        expenseClaim.setClaimId(1);

        Mockito.when(
                expenseClaimServiceMock.getExpenseClaimById(1)
        ).thenReturn(expenseClaim);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        ExpenseClaim actualResult =
                controller.getExpenseClaimById(1);

        // Assert
        Assertions.assertEquals(
                expenseClaim,
                actualResult
        );
    }

    @Test
    void getAllExpenseClaims() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        List<ExpenseClaim> expenseClaims =
                List.of(
                        new ExpenseClaim(
                                10,
                                "Travel expenses",
                                2500.50,
                                LocalDate.now(),
                                "DRAFT",
                                "travel.pdf"
                        ),
                        new ExpenseClaim(
                                11,
                                "Food expenses",
                                1500.00,
                                LocalDate.now(),
                                "SUBMITTED",
                                "food.pdf"
                        )
                );

        Mockito.when(
                expenseClaimServiceMock.getAllExpenseClaims()
        ).thenReturn(expenseClaims);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        List<ExpenseClaim> actualResult =
                controller.getAllExpenseClaims();

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }

    @Test
    void deleteExpenseClaimById() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        Mockito.when(
                expenseClaimServiceMock.deleteExpenseClaimById(1)
        ).thenReturn(true);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        boolean actualResult =
                controller.deleteExpenseClaimById(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getClaimsByEmployeeId() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        List<ExpenseClaim> expenseClaims =
                List.of(
                        new ExpenseClaim(
                                10,
                                "Travel expenses",
                                2500.50,
                                LocalDate.now(),
                                "DRAFT",
                                "travel.pdf"
                        )
                );

        Mockito.when(
                expenseClaimServiceMock.getClaimsByEmployeeId(10)
        ).thenReturn(expenseClaims);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        List<ExpenseClaim> actualResult =
                controller.getClaimsByEmployeeId(10);

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }

    @Test
    void submitClaim() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        Mockito.when(
                expenseClaimServiceMock.submitClaim(1)
        ).thenReturn(true);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        boolean actualResult =
                controller.submitClaim(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void approveClaim() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        Mockito.when(
                expenseClaimServiceMock.approveClaim(1)
        ).thenReturn(true);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        boolean actualResult =
                controller.approveClaim(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void rejectClaim() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        Mockito.when(
                expenseClaimServiceMock.rejectClaim(
                        1,
                        "Invalid expense"
                )
        ).thenReturn(true);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        boolean actualResult =
                controller.rejectClaim(
                        1,
                        "Invalid expense"
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getClaimsByStatus() {

        // Arrange
        IExpenseClaimService expenseClaimServiceMock =
                Mockito.mock(IExpenseClaimService.class);

        List<ExpenseClaim> expenseClaims =
                List.of(
                        new ExpenseClaim(
                                10,
                                "Travel expenses",
                                2500.50,
                                LocalDate.now(),
                                "DRAFT",
                                "travel.pdf"
                        )
                );

        Mockito.when(
                expenseClaimServiceMock.getClaimsByStatus("DRAFT")
        ).thenReturn(expenseClaims);

        ExpenseClaimController controller =
                new ExpenseClaimController(
                        expenseClaimServiceMock
                );

        // Act
        List<ExpenseClaim> actualResult =
                controller.getClaimsByStatus("DRAFT");

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }
}