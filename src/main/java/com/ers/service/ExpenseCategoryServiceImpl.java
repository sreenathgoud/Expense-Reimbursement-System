package com.ers.service;

import com.ers.dao.IExpenseCategoryDao;
import com.ers.model.ExpenseCategory;

import java.util.List;

public class ExpenseCategoryServiceImpl implements IExpenseCategoryService{
    private IExpenseCategoryDao expenseCategoryDao;
    public ExpenseCategoryServiceImpl(IExpenseCategoryDao expenseCategoryDao){
        this.expenseCategoryDao=expenseCategoryDao;
    }
    @Override
    public ExpenseCategory addExpenseCategory(ExpenseCategory expenseCategory) {
        // Business validation
        if (expenseCategory == null) {
            return null;
        }

        if (expenseCategory.getCategory_name() == null ||
                expenseCategory.getCategory_name().isBlank()) {
            return null;
        }

        return expenseCategoryDao.addExpenseCategory(
                expenseCategory
        );
    }


    @Override
    public boolean updateExpenseCategory(ExpenseCategory expenseCategory) {
        // Business validation
        if (expenseCategory == null) {
            return false;
        }

        if (expenseCategory.getCategory_id() <= 0) {
            return false;
        }

        if (expenseCategory.getCategory_name() == null ||
                expenseCategory.getCategory_name().isBlank()) {
            return false;
        }

        return expenseCategoryDao.updateExpenseCategory(
                expenseCategory
        );
    }

    @Override
    public ExpenseCategory getExpenseCategoryById(int categoryId) {
        // Business validation
        if (categoryId <= 0) {
            return null;
        }

        return expenseCategoryDao.getExpenseCategoryById(
                categoryId
        );
    }

    @Override
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryDao.getAllExpenseCategories();
    }

    @Override
    public boolean deleteExpenseCategoryById(int categoryId) {

            // Business validation
            if (categoryId <= 0) {
                return false;
            }

            return expenseCategoryDao.deleteExpenseCategoryById(
                    categoryId
            );
    }
}
