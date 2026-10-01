package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.model.Reimbursement;
import com.ers.service.IExpenseClaimService;
import com.ers.service.IReimbursementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeClaimController {

    private final IExpenseClaimService expenseClaimService;
    private final IReimbursementService reimbursementService;
    private final Scanner scanner;
    private final int employeeId;

    public EmployeeClaimController(
            IExpenseClaimService expenseClaimService,
            IReimbursementService reimbursementService,
            Scanner scanner,
            int employeeId) {

        this.expenseClaimService = expenseClaimService;
        this.reimbursementService = reimbursementService;
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
            System.out.println("3. My Claim Items");
            System.out.println("4. Submit Claim");
            System.out.println("5. Edit Draft Claim");
            System.out.println("6. Delete Draft Claim");
            System.out.println("7. Search/Filter My Claims");
            System.out.println("8. Reimbursement History");
            System.out.println("9. Back");
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
                        getMyClaimItems();
                        break;

                    case "4":
                        submitClaim();
                        break;

                    case "5":
                        updateDraftClaim();
                        break;

                    case "6":
                        deleteDraftClaim();
                        break;

                    case "7":
                        searchMyExpenseClaims();
                        break;

                    case "8":
                        displayMyReimbursementHistory();
                        break;

                    case "9":
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
                Reimbursement reimbursement = reimbursementService.getReimbursementByClaimId(claim.getClaimId());
                if (reimbursement != null) {
                    System.out.println("  Reimbursement status: " + reimbursement.getStatus()
                            + " | date: " + reimbursement.getReimbursementDate()
                            + " | amount: " + reimbursement.getReimbursedAmount());
                } else {
                    System.out.println("  Reimbursement status: NOT_PROCESSED");
                }
            }
        }
    }

    // =========================
    // GET MY CLAIM ITEMS
    // =========================

    private void getMyClaimItems() {

        System.out.println("========== MY CLAIM ITEMS ==========");

        List<ExpenseClaim> claims =
                expenseClaimService.getClaimsByEmployeeId(employeeId);
        int paidClaims = 0;

        for (ExpenseClaim claim : claims) {
            if (!"PAID".equalsIgnoreCase(claim.getStatus())) {
                continue;
            }

            System.out.println(
                    "Claim ID: " + claim.getClaimId()
                            + " | " + claim.getClaimDesc()
                            + " | Claim amount: " + claim.getClaimAmount()
                            + " | Claim status: " + claim.getStatus()
            );

            Reimbursement reimbursement =
                    reimbursementService.getReimbursementByClaimId(
                            claim.getClaimId()
                    );
            if (reimbursement != null) {
                System.out.println(
                        "  Reimbursed: " + reimbursement.getReimbursedAmount()
                                + " | Payment date: " + reimbursement.getReimbursementDate()
                                + " | Reimbursement status: " + reimbursement.getStatus()
                );
            } else {
                System.out.println("  Reimbursement record not found.");
            }
            paidClaims++;
        }

        if (paidClaims == 0) {
            System.out.println("No paid expense claims found.");
        } else {
            System.out.println("Total paid claims: " + paidClaims);
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

        if (!"DRAFT".equalsIgnoreCase(claim.getStatus())) {
            System.out.println("Only DRAFT claims can be submitted.");
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

    private void updateDraftClaim() {
        System.out.println("========== EDIT DRAFT CLAIM ==========");
        System.out.println("Enter Claim ID:");
        int claimId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Claim Description:");
        String description = scanner.nextLine();
        System.out.println("Enter Claim Amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter Claim Date (YYYY-MM-DD):");
        LocalDate claimDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter Document Path:");
        String documentPath = scanner.nextLine();

        ExpenseClaim claim = new ExpenseClaim(
                employeeId, description, amount, claimDate, "DRAFT", documentPath
        );
        claim.setClaimId(claimId);

        boolean updated = expenseClaimService.updateDraftClaimForEmployee(claim, employeeId);
        System.out.println(updated
                ? "Draft claim updated successfully."
                : "Draft claim not updated. Verify it belongs to you and is still DRAFT.");
    }

    private void deleteDraftClaim() {
        System.out.println("========== DELETE DRAFT CLAIM ==========");
        System.out.println("Enter Claim ID:");
        int claimId = Integer.parseInt(scanner.nextLine());

        boolean deleted = expenseClaimService.deleteDraftClaimForEmployee(claimId, employeeId);
        System.out.println(deleted
                ? "Draft claim deleted successfully."
                : "Draft claim not deleted. Verify it belongs to you and is still DRAFT.");
    }

    private void searchMyExpenseClaims() {
        System.out.println("========== SEARCH/FILTER MY CLAIMS ==========");
        System.out.println("Status filter (blank for any status):");
        String statusFilter = scanner.nextLine().trim();
        System.out.println("Description search (blank for any description):");
        String descriptionFilter = scanner.nextLine().trim();

        List<ExpenseClaim> matches = expenseClaimService
                .getClaimsByEmployeeId(employeeId)
                .stream()
                .filter(claim -> statusFilter.isEmpty()
                        || statusFilter.equalsIgnoreCase(claim.getStatus()))
                .filter(claim -> descriptionFilter.isEmpty()
                        || (claim.getClaimDesc() != null
                        && claim.getClaimDesc().toLowerCase().contains(descriptionFilter.toLowerCase())))
                .toList();

        if (matches.isEmpty()) {
            System.out.println("No matching claims found.");
            return;
        }
        matches.forEach(System.out::println);
    }

    private void displayMyReimbursementHistory() {
        List<com.ers.model.Reimbursement> reimbursements =
                reimbursementService.getReimbursementsByEmployeeId(employeeId);
        if (reimbursements.isEmpty()) {
            System.out.println("No reimbursement history found.");
            return;
        }

        double totalReimbursed = 0;
        for (com.ers.model.Reimbursement reimbursement : reimbursements) {
            System.out.println(reimbursement);
            if ("PROCESSED".equalsIgnoreCase(reimbursement.getStatus())) {
                totalReimbursed += reimbursement.getReimbursedAmount();
            }
        }
        System.out.println("Total amount reimbursed: " + totalReimbursed);
    }
}