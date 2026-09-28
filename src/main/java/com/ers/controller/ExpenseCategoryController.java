package com.ers.controller;

import com.ers.model.ExpenseCategory;
import com.ers.service.IExpenseCategoryService;

import java.util.List;
import java.util.Scanner;

public class ExpenseCategoryController {

    private final IExpenseCategoryService expenseCategoryService;
    private final Scanner scanner;

    public ExpenseCategoryController(
            IExpenseCategoryService expenseCategoryService) {

        this.expenseCategoryService = expenseCategoryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("    EXPENSE CATEGORY MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Expense Category");
            System.out.println("2. Update Expense Category");
            System.out.println("3. Get Expense Category By ID");
            System.out.println("4. Get All Expense Categories");
            System.out.println("5. Delete Expense Category");
            System.out.println("6. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addExpenseCategoryFromInput();
                        break;

                    case "2":
                        updateExpenseCategoryFromInput();
                        break;

                    case "3":
                        getExpenseCategoryByIdFromInput();
                        break;

                    case "4":
                        displayAllExpenseCategories();
                        break;

                    case "5":
                        deleteExpenseCategoryFromInput();
                        break;

                    case "6":
                        running = false;
                        System.out.println(
                                "Returning to main menu."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice: " + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid expense category input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in ExpenseCategoryController: "
                                + e.getMessage()
                );

                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // ADD EXPENSE CATEGORY
    // ==========================================

    private void addExpenseCategoryFromInput() {

        System.out.println(
                "========== ADD EXPENSE CATEGORY =========="
        );

        System.out.println(
                "Enter Category Name:"
        );

        String categoryName =
                scanner.nextLine();

        System.out.println(
                "Enter Description:"
        );

        String description =
                scanner.nextLine();

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        categoryName,
                        description
                );

        ExpenseCategory result =
                addExpenseCategory(
                        expenseCategory
                );

        if (result != null) {

            System.out.println(
                    "Expense category added successfully. "
                            + "Category ID="
                            + result.getCategory_id()
            );

        } else {

            System.out.println(
                    "Failed to add expense category."
            );
        }
    }

    // ==========================================
    // UPDATE EXPENSE CATEGORY
    // ==========================================

    private void updateExpenseCategoryFromInput() {

        System.out.println(
                "========== UPDATE EXPENSE CATEGORY =========="
        );

        System.out.println(
                "Enter Category ID:"
        );

        int categoryId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Category Name:"
        );

        String categoryName =
                scanner.nextLine();

        System.out.println(
                "Enter Description:"
        );

        String description =
                scanner.nextLine();

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        categoryName,
                        description
                );

        expenseCategory.setCategory_id(
                categoryId
        );

        boolean result =
                updateExpenseCategory(
                        expenseCategory
                );

        if (result) {

            System.out.println(
                    "Expense category updated successfully. "
                            + "Category ID="
                            + categoryId
            );

        } else {

            System.out.println(
                    "Expense category update failed. "
                            + "Category ID="
                            + categoryId
            );
        }
    }

    // ==========================================
    // GET EXPENSE CATEGORY BY ID
    // ==========================================

    private void getExpenseCategoryByIdFromInput() {

        System.out.println(
                "========== GET EXPENSE CATEGORY BY ID =========="
        );

        System.out.println(
                "Enter Category ID:"
        );

        int categoryId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        ExpenseCategory expenseCategory =
                getExpenseCategoryById(
                        categoryId
                );

        if (expenseCategory != null) {

            System.out.println(
                    "Expense category found:"
            );

            System.out.println(
                    expenseCategory
            );

        } else {

            System.out.println(
                    "No expense category found with ID="
                            + categoryId
            );
        }
    }

    // ==========================================
    // GET ALL EXPENSE CATEGORIES
    // ==========================================

    private void displayAllExpenseCategories() {

        System.out.println(
                "========== ALL EXPENSE CATEGORIES =========="
        );

        List<ExpenseCategory> expenseCategories =
                getAllExpenseCategories();

        if (expenseCategories.isEmpty()) {

            System.out.println(
                    "No expense categories found."
            );

        } else {

            System.out.println(
                    "Total expense categories found: "
                            + expenseCategories.size()
            );

            for (ExpenseCategory expenseCategory :
                    expenseCategories) {

                System.out.println(
                        expenseCategory
                );
            }
        }
    }

    // ==========================================
    // DELETE EXPENSE CATEGORY
    // ==========================================

    private void deleteExpenseCategoryFromInput() {

        System.out.println(
                "========== DELETE EXPENSE CATEGORY =========="
        );

        System.out.println(
                "Enter Category ID:"
        );

        int categoryId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                deleteExpenseCategoryById(
                        categoryId
                );

        if (result) {

            System.out.println(
                    "Expense category deleted successfully. "
                            + "Category ID="
                            + categoryId
            );

        } else {

            System.out.println(
                    "Expense category deletion failed. "
                            + "Category ID="
                            + categoryId
            );
        }
    }

    // ==========================================
    // SERVICE DELEGATION METHODS
    // ==========================================

    public ExpenseCategory addExpenseCategory(
            ExpenseCategory expenseCategory) {

        return expenseCategoryService.addExpenseCategory(
                expenseCategory
        );
    }

    public boolean updateExpenseCategory(
            ExpenseCategory expenseCategory) {

        return expenseCategoryService.updateExpenseCategory(
                expenseCategory
        );
    }

    public ExpenseCategory getExpenseCategoryById(
            int categoryId) {

        return expenseCategoryService.getExpenseCategoryById(
                categoryId
        );
    }

    public List<ExpenseCategory> getAllExpenseCategories() {

        return expenseCategoryService
                .getAllExpenseCategories();
    }

    public boolean deleteExpenseCategoryById(
            int categoryId) {

        return expenseCategoryService
                .deleteExpenseCategoryById(
                        categoryId
                );
    }
}
