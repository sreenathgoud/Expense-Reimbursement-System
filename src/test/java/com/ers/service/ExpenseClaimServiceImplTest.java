package com.ers.service;

import com.ers.dao.IExpenseClaimDao;
import com.ers.model.ExpenseClaim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class ExpenseClaimServiceImplTest {

    @Test
    void addExpenseClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

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
                expenseClaimDaoMock.addExpenseClaim(expenseClaim)
        ).thenReturn(expenseClaim);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        ExpenseClaim actualResult =
                expenseClaimService.addExpenseClaim(expenseClaim);

        // Assert
        Assertions.assertEquals(
                expenseClaim,
                actualResult
        );
    }

    @Test
    void addExpenseClaimNull() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.addExpenseClaim(null)
        );
    }

    @Test
    void updateExpenseClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Updated travel expenses",
                        3000.00,
                        LocalDate.now(),
                        "DRAFT",
                        "updated.pdf"
                );

        expenseClaim.setClaimId(1);

        Mockito.when(
                expenseClaimDaoMock.updateExpenseClaim(expenseClaim)
        ).thenReturn(true);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        boolean actualResult =
                expenseClaimService.updateExpenseClaim(expenseClaim);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void updateExpenseClaimInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Updated travel expenses",
                        3000.00,
                        LocalDate.now(),
                        "DRAFT",
                        "updated.pdf"
                );

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.updateExpenseClaim(expenseClaim)
        );
    }

    @Test
    void getExpenseClaimById() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

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
                expenseClaimDaoMock.getExpenseClaimById(1)
        ).thenReturn(expenseClaim);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        ExpenseClaim actualResult =
                expenseClaimService.getExpenseClaimById(1);

        // Assert
        Assertions.assertEquals(
                expenseClaim,
                actualResult
        );
    }

    @Test
    void getExpenseClaimByIdInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.getExpenseClaimById(0)
        );
    }

    @Test
    void getAllExpenseClaims() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

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
                expenseClaimDaoMock.getAllExpenseClaims()
        ).thenReturn(expenseClaims);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimService.getAllExpenseClaims();

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }

    @Test
    void deleteExpenseClaimById() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        Mockito.when(
                expenseClaimDaoMock.deleteExpenseClaimById(1)
        ).thenReturn(true);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        boolean actualResult =
                expenseClaimService.deleteExpenseClaimById(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void deleteExpenseClaimByIdInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.deleteExpenseClaimById(0)
        );
    }

    @Test
    void getClaimsByEmployeeId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

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
                expenseClaimDaoMock.getClaimsByEmployeeId(10)
        ).thenReturn(expenseClaims);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimService.getClaimsByEmployeeId(10);

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }

    @Test
    void getClaimsByEmployeeIdInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.getClaimsByEmployeeId(0)
        );
    }

    @Test
    void submitClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        Mockito.when(
                expenseClaimDaoMock.submitClaim(1)
        ).thenReturn(true);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        boolean actualResult =
                expenseClaimService.submitClaim(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void submitClaimInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.submitClaim(0)
        );
    }

    @Test
    void approveClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        Mockito.when(
                expenseClaimDaoMock.approveClaim(1, "APPROVED")
        ).thenReturn(true);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        boolean actualResult =
                expenseClaimService.approveClaim(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void approveClaimInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.approveClaim(0)
        );
    }

    @Test
    void rejectClaim() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        Mockito.when(
                expenseClaimDaoMock.rejectClaim(
                        1,
                        "Invalid expense"
                )
        ).thenReturn(true);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        boolean actualResult =
                expenseClaimService.rejectClaim(
                        1,
                        "Invalid expense"
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void rejectClaimInvalidId() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.rejectClaim(
                        0,
                        "Invalid expense"
                )
        );
    }

    @Test
    void rejectClaimEmptyReason() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.rejectClaim(
                        1,
                        ""
                )
        );
    }

    @Test
    void getClaimsByStatus() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

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
                expenseClaimDaoMock.getClaimsByStatus("DRAFT")
        ).thenReturn(expenseClaims);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        List<ExpenseClaim> actualResult =
                expenseClaimService.getClaimsByStatus("DRAFT");

        // Assert
        Assertions.assertEquals(
                expenseClaims,
                actualResult
        );
    }

    @Test
    void getExpenseClaimWithItemsForManager() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        10,
                        "Business trip",
                        5000.00,
                        LocalDate.now(),
                        "SUBMITTED",
                        null
                );
        expenseClaim.setClaimId(101);

        Mockito.when(
                expenseClaimDaoMock.getExpenseClaimWithItemsForManager(101, 7)
        ).thenReturn(expenseClaim);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act
        ExpenseClaim actualResult =
                expenseClaimService.getExpenseClaimWithItemsForManager(101, 7);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(101, actualResult.getClaimId());
        Assertions.assertEquals("Business trip", actualResult.getClaimDesc());
    }

    @Test
    void getClaimsForManager() {
        IExpenseClaimDao expenseClaimDao = Mockito.mock(IExpenseClaimDao.class);
        List<ExpenseClaim> claims = List.of(new ExpenseClaim(
                10, "Business trip", 5000.00, LocalDate.now(), "SUBMITTED", null
        ));
        Mockito.when(expenseClaimDao.getClaimsForManager(7)).thenReturn(claims);

        ExpenseClaimServiceImpl service = new ExpenseClaimServiceImpl(expenseClaimDao);

        Assertions.assertEquals(claims, service.getClaimsForManager(7));
    }

    @Test
    void getClaimsByStatusInvalidStatus() {

        // Arrange
        IExpenseClaimDao expenseClaimDaoMock =
                Mockito.mock(IExpenseClaimDao.class);

        ExpenseClaimServiceImpl expenseClaimService =
                new ExpenseClaimServiceImpl(expenseClaimDaoMock);

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> expenseClaimService.getClaimsByStatus("")
        );
    }
}