package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.ClaimItem;
import com.ers.model.ExpenseClaim;
import com.ers.service.IClaimItemService;
import com.ers.service.IExpenseClaimService;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeClaimController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(
                    EmployeeClaimController.class
            );

    private final IExpenseClaimService expenseClaimService;
    private final IClaimItemService claimItemService;
    private final Scanner scanner;
    private final int employeeId;

    public EmployeeClaimController(
            IExpenseClaimService expenseClaimService,
            IClaimItemService claimItemService,
            Scanner scanner,
            int employeeId) {

        this.expenseClaimService = expenseClaimService;
        this.claimItemService = claimItemService;
        this.scanner = scanner;
        this.employeeId = employeeId;
    }

    public void start() {

        boolean running = true;

        while (running) {

            logger.info("======================================");
            logger.info("          EMPLOYEE CLAIM MENU");
            logger.info("======================================");
            logger.info("1. Raise Expense Claim");
            logger.info("2. My Expense Claims");
            logger.info("3. Add Claim Item");
            logger.info("4. My Claim Items");
            logger.info("5. Submit Claim");
            logger.info("6. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addExpenseClaim();
                        break;

                    case "2":
                        getMyExpenseClaims();
                        break;

                    case "3":
                        addClaimItem();
                        break;

                    case "4":
                        getMyClaimItems();
                        break;

                    case "5":
                        submitClaim();
                        break;

                    case "6":
                        running = false;
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn(
                                "Invalid employee claim menu choice: {}",
                                choice
                        );
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in EmployeeClaimController.",
                        e
                );
            }
        }
    }

    private void addExpenseClaim() {

        logger.info("========== RAISE EXPENSE CLAIM ==========");

        logger.info("Enter Claim Description:");
        String description = scanner.nextLine();

        logger.info("Enter Claim Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Claim Date (YYYY-MM-DD):");
        LocalDate claimDate =
                LocalDate.parse(scanner.nextLine());

        logger.info("Enter Document Path:");
        String documentPath = scanner.nextLine();

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        employeeId,
                        description,
                        amount,
                        claimDate,
                        "DRAFT",
                        documentPath
                );

        ExpenseClaim result =
                expenseClaimService.addExpenseClaim(
                        expenseClaim
                );

        if (result != null) {

            logger.info(
                    "Expense claim created successfully. Claim ID={}",
                    result.getClaimId()
            );

        } else {

            logger.warn(
                    "Failed to create expense claim."
            );
        }
    }

    private void getMyExpenseClaims() {

        logger.info("========== MY EXPENSE CLAIMS ==========");

        List<ExpenseClaim> claims =
                expenseClaimService.getClaimsByEmployeeId(
                        employeeId
                );

        if (claims.isEmpty()) {

            logger.info("No expense claims found.");

        } else {

            logger.info(
                    "Total claims: {}",
                    claims.size()
            );

            for (ExpenseClaim claim : claims) {
                logger.info("{}", claim);
            }
        }
    }

    private void addClaimItem() {

        logger.info("========== ADD CLAIM ITEM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        /*
         * Verify that the claim belongs to
         * the logged-in employee.
         */
        ExpenseClaim claim =
                expenseClaimService.getExpenseClaimById(
                        claimId
                );

        if (claim == null) {

            logger.warn(
                    "Claim not found. Claim ID={}",
                    claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            logger.warn(
                    "You can only add items to your own claims."
            );

            return;
        }

        logger.info("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Description:");
        String description =
                scanner.nextLine();

        logger.info("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Expense Date (YYYY-MM-DD):");
        LocalDate expenseDate =
                LocalDate.parse(scanner.nextLine());

        ClaimItem claimItem =
                new ClaimItem(
                        claimId,
                        categoryId,
                        description,
                        amount,
                        expenseDate
                );

        ClaimItem result =
                claimItemService.addClaimItem(
                        claimItem
                );

        if (result != null) {

            logger.info(
                    "Claim item added successfully. Item ID={}",
                    result.getItemId()
            );

        } else {

            logger.warn(
                    "Failed to add claim item."
            );
        }
    }

    private void getMyClaimItems() {

        logger.info("========== MY CLAIM ITEMS ==========");

        logger.info("Enter Claim ID:");

        int claimId =
                Integer.parseInt(scanner.nextLine());

        /*
         * Verify claim ownership first.
         */
        ExpenseClaim claim =
                expenseClaimService.getExpenseClaimById(
                        claimId
                );

        if (claim == null) {

            logger.warn(
                    "Claim not found. Claim ID={}",
                    claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            logger.warn(
                    "You can only view items belonging to your own claims."
            );

            return;
        }

        List<ClaimItem> items =
                claimItemService.getClaimItemsByClaimId(
                        claimId
                );

        if (items.isEmpty()) {

            logger.info("No claim items found.");

        } else {

            logger.info(
                    "Total claim items: {}",
                    items.size()
            );

            for (ClaimItem item : items) {
                logger.info("{}", item);
            }
        }
    }

    private void submitClaim() {

        logger.info("========== SUBMIT EXPENSE CLAIM ==========");

        logger.info("Enter Claim ID:");

        int claimId =
                Integer.parseInt(scanner.nextLine());

        /*
         * Verify claim ownership.
         */
        ExpenseClaim claim =
                expenseClaimService.getExpenseClaimById(
                        claimId
                );

        if (claim == null) {

            logger.warn(
                    "Claim not found. Claim ID={}",
                    claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            logger.warn(
                    "You can only submit your own claims."
            );

            return;
        }

        boolean result =
                expenseClaimService.submitClaim(
                        claimId
                );

        if (result) {

            logger.info(
                    "Claim submitted successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Claim submission failed. Claim ID={}",
                    claimId
            );
        }
    }
}