package com.ers.dao;

import com.ers.model.ExpenseCategory;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseCategoryDaoImplTest {

    @Test
    void addExpenseCategory() {
        // Arrange
        // Arrange
        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Travel",
                        "Travel related expenses"
                );

        // Act
        ExpenseCategory actualResult =
                expenseCategoryDao.addExpenseCategory(
                        expenseCategory
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(
                actualResult.getCategory_id() > 0
        );
    }


    @Test
    void updateExpenseCategory() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Updated Travel",
                        "Updated travel related expenses"
                );

        expenseCategory.setCategory_id(1);

        // Act
        boolean actualResult =
                expenseCategoryDao.updateExpenseCategory(
                        expenseCategory
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getExpenseCategoryById() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        int categoryId = 1;

        // Act
        ExpenseCategory actualResult =
                expenseCategoryDao.getExpenseCategoryById(
                        categoryId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void getAllExpenseCategories() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        // Act
        List<ExpenseCategory> actualResult =
                expenseCategoryDao.getAllExpenseCategories();

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void deleteExpenseCategoryById() {

        // Arrange
        IExpenseCategoryDao expenseCategoryDao =
                new ExpenseCategoryDaoImpl(new JDBCUtil());

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        "Temporary Category",
                        "Category created for delete test"
                );

        ExpenseCategory savedCategory =
                expenseCategoryDao.addExpenseCategory(
                        expenseCategory
                );

        int categoryId =
                savedCategory.getCategory_id();

        // Act
        boolean actualResult =
                expenseCategoryDao.deleteExpenseCategoryById(
                        categoryId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }
}