package com.ers.service;

import com.ers.dao.IExpenseCategoryDao;
import com.ers.model.ExpenseCategory;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExpenseCategoryServiceImpl
        implements IExpenseCategoryService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ExpenseCategoryServiceImpl.class);

    private final IExpenseCategoryDao expenseCategoryDao;

    public ExpenseCategoryServiceImpl(
            IExpenseCategoryDao expenseCategoryDao) {

        this.expenseCategoryDao = expenseCategoryDao;
    }

    @Override
    public ExpenseCategory addExpenseCategory(
            ExpenseCategory expenseCategory) {

        // Validation
        if (expenseCategory == null) {
            throw new IllegalArgumentException(
                    "Expense category cannot be null."
            );
        }

        if (expenseCategory.getCategory_name() == null ||
                expenseCategory.getCategory_name().isBlank()) {
            throw new IllegalArgumentException(
                    "Category name is required."
            );
        }

        ExpenseCategory result =
                expenseCategoryDao.addExpenseCategory(
                        expenseCategory
                );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add expense category."
            );
        }

        logger.info(
                "Expense category added successfully: "
                        + expenseCategory.getCategory_name()
        );

        return result;
    }

    @Override
    public boolean updateExpenseCategory(
            ExpenseCategory expenseCategory) {

        if (expenseCategory == null) {
            throw new IllegalArgumentException(
                    "Expense category cannot be null."
            );
        }

        if (expenseCategory.getCategory_id() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid category ID."
            );
        }

        if (expenseCategory.getCategory_name() == null ||
                expenseCategory.getCategory_name().isBlank()) {
            throw new IllegalArgumentException(
                    "Category name is required."
            );
        }

        boolean result =
                expenseCategoryDao.updateExpenseCategory(
                        expenseCategory
                );

        if (result) {
            logger.info(
                    "Expense category updated successfully: ID="
                            + expenseCategory.getCategory_id()
            );
        }

        return result;
    }

    @Override
    public ExpenseCategory getExpenseCategoryById(
            int categoryId) {

        if (categoryId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid category ID."
            );
        }

        ExpenseCategory category =
                expenseCategoryDao.getExpenseCategoryById(
                        categoryId
                );

        if (category == null) {
            logger.warn(
                    "No expense category found with ID="
                            + categoryId
            );
        }

        return category;
    }

    @Override
    public List<ExpenseCategory> getAllExpenseCategories() {

        return expenseCategoryDao.getAllExpenseCategories();
    }

    @Override
    public List<ExpenseCategory> searchExpenseCategories(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return expenseCategoryDao.getAllExpenseCategories();
        }
        return expenseCategoryDao.searchExpenseCategories(searchTerm.trim());
    }

    @Override
    public boolean deleteExpenseCategoryById(
            int categoryId) {

        if (categoryId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid category ID."
            );
        }

        boolean result =
                expenseCategoryDao.deleteExpenseCategoryById(
                        categoryId
                );

        if (result) {
            logger.info(
                    "Expense category deleted successfully: ID="
                            + categoryId
            );
        }

        return result;
    }
}