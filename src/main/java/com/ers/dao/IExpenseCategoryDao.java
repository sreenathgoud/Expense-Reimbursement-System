package com.ers.dao;

import com.ers.model.ExpenseCategory;

import java.util.List;

public interface IExpenseCategoryDao {
    ExpenseCategory addExpenseCategory(ExpenseCategory expenseCategory);
    boolean updateExpenseCategory(ExpenseCategory expenseCategory);
    ExpenseCategory getExpenseCategoryById(int categoryId);
    List<ExpenseCategory> getAllExpenseCategories();
    List<ExpenseCategory> searchExpenseCategories(String searchTerm);
    boolean deleteExpenseCategoryById(int categoryId);
}
