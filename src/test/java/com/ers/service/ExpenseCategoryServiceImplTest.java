package com.ers.service;

import com.ers.dao.IExpenseCategoryDao;
import com.ers.model.ExpenseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class ExpenseCategoryServiceImplTest {

    @Test
    void addExpenseCategory() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDaoMock =
                Mockito.mock(IExpenseCategoryDao.class);

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Travel",
                        "Travel related expenses"
                );

        Mockito.when(
                expenseCategoryDaoMock.addExpenseCategory(
                        expenseCategory
                )
        ).thenReturn(expenseCategory);

        ExpenseCategoryServiceImpl expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDaoMock
                );

        // Act
        ExpenseCategory actualResult =
                expenseCategoryService.addExpenseCategory(
                        expenseCategory
                );

        // Assert
        Assertions.assertEquals(
                expenseCategory,
                actualResult
        );
    }

    @Test
    void updateExpenseCategory() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDaoMock =
                Mockito.mock(IExpenseCategoryDao.class);

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Updated Travel",
                        "Updated travel expenses"
                );

        expenseCategory.setCategory_id(1);

        Mockito.when(
                expenseCategoryDaoMock.updateExpenseCategory(
                        expenseCategory
                )
        ).thenReturn(true);

        ExpenseCategoryServiceImpl expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDaoMock
                );

        // Act
        boolean actualResult =
                expenseCategoryService.updateExpenseCategory(
                        expenseCategory
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getExpenseCategoryById() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDaoMock =
                Mockito.mock(IExpenseCategoryDao.class);

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Travel",
                        "Travel related expenses"
                );

        expenseCategory.setCategory_id(1);

        Mockito.when(
                expenseCategoryDaoMock.getExpenseCategoryById(1)
        ).thenReturn(expenseCategory);

        ExpenseCategoryServiceImpl expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDaoMock
                );

        // Act
        ExpenseCategory actualResult =
                expenseCategoryService.getExpenseCategoryById(1);

        // Assert
        Assertions.assertEquals(
                expenseCategory,
                actualResult
        );
    }

    @Test
    void getAllExpenseCategories() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDaoMock =
                Mockito.mock(IExpenseCategoryDao.class);

        List<ExpenseCategory> expenseCategories =
                List.of(
                        new ExpenseCategory(
                                "Travel",
                                "Travel expenses"
                        ),
                        new ExpenseCategory(
                                "Food",
                                "Food expenses"
                        )
                );

        Mockito.when(
                expenseCategoryDaoMock.getAllExpenseCategories()
        ).thenReturn(expenseCategories);

        ExpenseCategoryServiceImpl expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDaoMock
                );

        // Act
        List<ExpenseCategory> actualResult =
                expenseCategoryService.getAllExpenseCategories();

        // Assert
        Assertions.assertEquals(
                expenseCategories,
                actualResult
        );
    }

    @Test
    void deleteExpenseCategoryById() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDaoMock =
                Mockito.mock(IExpenseCategoryDao.class);

        Mockito.when(
                expenseCategoryDaoMock.deleteExpenseCategoryById(1)
        ).thenReturn(true);

        ExpenseCategoryServiceImpl expenseCategoryService =
                new ExpenseCategoryServiceImpl(
                        expenseCategoryDaoMock
                );

        // Act
        boolean actualResult =
                expenseCategoryService.deleteExpenseCategoryById(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}