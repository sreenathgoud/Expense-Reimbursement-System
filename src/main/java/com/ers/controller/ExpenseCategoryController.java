package com.ers.controller;

import com.ers.model.ExpenseCategory;
import com.ers.service.IExpenseCategoryService;

import java.util.List;

public class ExpenseCategoryController {
    private IExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(IExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }
    public ExpenseCategory addExpenseCategory(ExpenseCategory expenseCategory) {
        return expenseCategoryService.addExpenseCategory(
                expenseCategory);
    }
    public boolean updateExpenseCategory(ExpenseCategory expenseCategory) {
        return expenseCategoryService.updateExpenseCategory(
                expenseCategory
        );
    }
    public ExpenseCategory getExpenseCategoryById(int categoryId) {
        return expenseCategoryService.getExpenseCategoryById(
                categoryId
        );
    }
    public List<ExpenseCategory> getAllExpenseCategories() {
        return expenseCategoryService.getAllExpenseCategories();
    }
    public boolean deleteExpenseCategoryById(int categoryId) {
        return expenseCategoryService.deleteExpenseCategoryById(
                categoryId
        );
    }
}
