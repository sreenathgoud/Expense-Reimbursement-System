package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.ExpenseCategory;
import com.ers.service.IExpenseCategoryService;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class ExpenseCategoryController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ExpenseCategoryController.class);

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

            logger.info("======================================");
            logger.info("    EXPENSE CATEGORY MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Expense Category");
            logger.info("2. Update Expense Category");
            logger.info("3. Get Expense Category By ID");
            logger.info("4. Get All Expense Categories");
            logger.info("5. Delete Expense Category");
            logger.info("6. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

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
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid expense category input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in ExpenseCategoryController.",
                        e
                );
            }
        }
    }

    private void addExpenseCategoryFromInput() {

        logger.info("========== ADD EXPENSE CATEGORY ==========");

        logger.info("Enter Category Name:");
        String categoryName = scanner.nextLine();

        logger.info("Enter Description:");
        String description = scanner.nextLine();

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        categoryName,
                        description
                );

        ExpenseCategory result =
                addExpenseCategory(expenseCategory);

        if (result != null) {

            logger.info(
                    "Expense category added successfully. Category ID={}",
                    result.getCategory_id()
            );

        } else {

            logger.warn("Failed to add expense category.");
        }
    }

    private void updateExpenseCategoryFromInput() {

        logger.info("========== UPDATE EXPENSE CATEGORY ==========");

        logger.info("Enter Category ID:");
        int categoryId = Integer.parseInt(scanner.nextLine());

        logger.info("Enter Category Name:");
        String categoryName = scanner.nextLine();

        logger.info("Enter Description:");
        String description = scanner.nextLine();

        ExpenseCategory expenseCategory =
                new ExpenseCategory(
                        categoryName,
                        description
                );

        expenseCategory.setCategory_id(categoryId);

        boolean result =
                updateExpenseCategory(expenseCategory);

        if (result) {

            logger.info(
                    "Expense category updated successfully. Category ID={}",
                    categoryId
            );

        } else {

            logger.warn(
                    "Expense category update failed. Category ID={}",
                    categoryId
            );
        }
    }

    private void getExpenseCategoryByIdFromInput() {

        logger.info(
                "========== GET EXPENSE CATEGORY BY ID =========="
        );

        logger.info("Enter Category ID:");
        int categoryId = Integer.parseInt(scanner.nextLine());

        ExpenseCategory expenseCategory =
                getExpenseCategoryById(categoryId);

        if (expenseCategory != null) {

            logger.info("Expense category found:");
            logger.info("{}", expenseCategory);

        } else {

            logger.warn(
                    "No expense category found with ID={}",
                    categoryId
            );
        }
    }

    private void displayAllExpenseCategories() {

        logger.info("========== ALL EXPENSE CATEGORIES ==========");

        List<ExpenseCategory> expenseCategories =
                getAllExpenseCategories();

        if (expenseCategories.isEmpty()) {

            logger.info("No expense categories found.");

        } else {

            logger.info(
                    "Total expense categories found: {}",
                    expenseCategories.size()
            );

            for (ExpenseCategory expenseCategory :
                    expenseCategories) {

                logger.info("{}", expenseCategory);
            }
        }
    }

    private void deleteExpenseCategoryFromInput() {

        logger.info(
                "========== DELETE EXPENSE CATEGORY =========="
        );

        logger.info("Enter Category ID:");
        int categoryId = Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteExpenseCategoryById(categoryId);

        if (result) {

            logger.info(
                    "Expense category deleted successfully. Category ID={}",
                    categoryId
            );

        } else {

            logger.warn(
                    "Expense category deletion failed. Category ID={}",
                    categoryId
            );
        }
    }

    // Service delegation methods

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

        return expenseCategoryService.getAllExpenseCategories();
    }

    public boolean deleteExpenseCategoryById(
            int categoryId) {

        return expenseCategoryService.deleteExpenseCategoryById(
                categoryId
        );
    }
}