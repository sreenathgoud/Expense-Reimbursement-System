package com.ers.controller;

import com.ers.model.ClaimItem;
import com.ers.model.ExpenseClaim;
import com.ers.service.IClaimItemService;
import com.ers.service.IExpenseClaimService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeClaimController {

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

            System.out.println("======================================");
            System.out.println("          EMPLOYEE CLAIM MENU");
            System.out.println("======================================");
            System.out.println("1. Raise Expense Claim");
            System.out.println("2. My Expense Claims");
            System.out.println("3. Add Claim Item");
            System.out.println("4. My Claim Items");
            System.out.println("5. Submit Claim");
            System.out.println("6. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

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
                        System.out.println("Returning to main menu.");
                        break;

                    default:
                        System.out.println(
                                "Invalid employee claim menu choice: "
                                        + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error: "
                                + e.getMessage()
                );
            }
        }
    }

    // =========================
    // RAISE EXPENSE CLAIM
    // =========================

    private void addExpenseClaim() {

        System.out.println("========== RAISE EXPENSE CLAIM ==========");

        System.out.println("Enter Claim Description:");
        String description = scanner.nextLine();

        System.out.println("Enter Claim Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        System.out.println("Enter Claim Date (YYYY-MM-DD):");
        LocalDate claimDate =
                LocalDate.parse(scanner.nextLine());

        System.out.println("Enter Document Path:");
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

            System.out.println(
                    "Expense claim created successfully. Claim ID="
                            + result.getClaimId()
            );

        } else {

            System.out.println(
                    "Failed to create expense claim."
            );
        }
    }

    // =========================
    // GET MY EXPENSE CLAIMS
    // =========================

    private void getMyExpenseClaims() {

        System.out.println("========== MY EXPENSE CLAIMS ==========");

        List<ExpenseClaim> claims =
                expenseClaimService.getClaimsByEmployeeId(
                        employeeId
                );

        if (claims.isEmpty()) {

            System.out.println("No expense claims found.");

        } else {

            System.out.println(
                    "Total claims: " + claims.size()
            );

            for (ExpenseClaim claim : claims) {
                System.out.println(claim);
            }
        }
    }

    // =========================
    // ADD CLAIM ITEM
    // =========================

    private void addClaimItem() {

        System.out.println("========== ADD CLAIM ITEM ==========");

        System.out.println("Enter Claim ID:");
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

            System.out.println(
                    "Claim not found. Claim ID=" + claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            System.out.println(
                    "You can only add items to your own claims."
            );

            return;
        }

        System.out.println("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Description:");
        String description =
                scanner.nextLine();

        System.out.println("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        System.out.println("Enter Expense Date (YYYY-MM-DD):");
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

            System.out.println(
                    "Claim item added successfully. Item ID="
                            + result.getItemId()
            );

        } else {

            System.out.println(
                    "Failed to add claim item."
            );
        }
    }

    // =========================
    // GET MY CLAIM ITEMS
    // =========================

    private void getMyClaimItems() {

        System.out.println("========== MY CLAIM ITEMS ==========");

        System.out.println("Enter Claim ID:");

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

            System.out.println(
                    "Claim not found. Claim ID=" + claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            System.out.println(
                    "You can only view items belonging to your own claims."
            );

            return;
        }

        List<ClaimItem> items =
                claimItemService.getClaimItemsByClaimId(
                        claimId
                );

        if (items.isEmpty()) {

            System.out.println("No claim items found.");

        } else {

            System.out.println(
                    "Total claim items: " + items.size()
            );

            for (ClaimItem item : items) {
                System.out.println(item);
            }
        }
    }

    // =========================
    // SUBMIT CLAIM
    // =========================

    private void submitClaim() {

        System.out.println("========== SUBMIT EXPENSE CLAIM ==========");

        System.out.println("Enter Claim ID:");

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

            System.out.println(
                    "Claim not found. Claim ID=" + claimId
            );

            return;
        }

        if (claim.getEmployeeId() != employeeId) {

            System.out.println(
                    "You can only submit your own claims."
            );

            return;
        }

        boolean result =
                expenseClaimService.submitClaim(
                        claimId
                );

        if (result) {

            System.out.println(
                    "Claim submitted successfully. Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Claim submission failed. Claim ID="
                            + claimId
            );
        }
    }
}