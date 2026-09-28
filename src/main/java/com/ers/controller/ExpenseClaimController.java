package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.service.IExpenseClaimService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseClaimController {

    private final IExpenseClaimService expenseClaimService;
    private final Scanner scanner;

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService) {

        this.expenseClaimService = expenseClaimService;
        this.scanner = new Scanner(System.in);
    }

    // ==========================================
    // MANAGER MENU
    // ==========================================

    public void startManager() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("       EXPENSE CLAIM REVIEW");
            System.out.println("======================================");
            System.out.println("1. Get Expense Claim By ID");
            System.out.println("2. Get All Expense Claims");
            System.out.println("3. Get Claims By Employee ID");
            System.out.println("4. Approve Claim");
            System.out.println("5. Reject Claim");
            System.out.println("6. Get Claims By Status");
            System.out.println("7. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        getExpenseClaimByIdFromInput();
                        break;

                    case "2":
                        displayAllExpenseClaims();
                        break;

                    case "3":
                        getClaimsByEmployeeIdFromInput();
                        break;

                    case "4":
                        approveClaimFromInput();
                        break;

                    case "5":
                        rejectClaimFromInput();
                        break;

                    case "6":
                        getClaimsByStatusFromInput();
                        break;

                    case "7":
                        running = false;
                        System.out.println(
                                "Returning to main menu."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid manager claim menu choice: "
                                        + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid expense claim input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in Manager Expense Claim Controller: "
                                + e.getMessage()
                );
            }
        }
    }

    // ==========================================
    // MAIN EXPENSE CLAIM MANAGEMENT MENU
    // ==========================================

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("       EXPENSE CLAIM MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Expense Claim");
            System.out.println("2. Update Expense Claim");
            System.out.println("3. Get Expense Claim By ID");
            System.out.println("4. Get All Expense Claims");
            System.out.println("5. Delete Expense Claim");
            System.out.println("6. Get Claims By Employee ID");
            System.out.println("7. Submit Claim");
            System.out.println("8. Approve Claim");
            System.out.println("9. Reject Claim");
            System.out.println("10. Get Claims By Status");
            System.out.println("11. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addExpenseClaimFromInput();
                        break;

                    case "2":
                        updateExpenseClaimFromInput();
                        break;

                    case "3":
                        getExpenseClaimByIdFromInput();
                        break;

                    case "4":
                        displayAllExpenseClaims();
                        break;

                    case "5":
                        deleteExpenseClaimFromInput();
                        break;

                    case "6":
                        getClaimsByEmployeeIdFromInput();
                        break;

                    case "7":
                        submitClaimFromInput();
                        break;

                    case "8":
                        approveClaimFromInput();
                        break;

                    case "9":
                        rejectClaimFromInput();
                        break;

                    case "10":
                        getClaimsByStatusFromInput();
                        break;

                    case "11":
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
                        "Invalid expense claim input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in ExpenseClaimController: "
                                + e.getMessage()
                );
            }
        }
    }

    // ==========================================
    // ADD EXPENSE CLAIM
    // ==========================================

    private void addExpenseClaimFromInput() {

        System.out.println(
                "========== ADD EXPENSE CLAIM =========="
        );

        System.out.println("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println("Enter Claim Description:");

        String claimDesc =
                scanner.nextLine();

        System.out.println("Enter Claim Amount:");

        double claimAmount =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Claim Date (YYYY-MM-DD):"
        );

        LocalDate claimDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.println("Enter Status:");

        String status =
                scanner.nextLine();

        System.out.println("Enter Document Path:");

        String documentPath =
                scanner.nextLine();

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        employeeId,
                        claimDesc,
                        claimAmount,
                        claimDate,
                        status,
                        documentPath
                );

        ExpenseClaim result =
                addExpenseClaim(expenseClaim);

        if (result != null) {

            System.out.println(
                    "Expense claim added successfully. "
                            + "Claim ID="
                            + result.getClaimId()
            );

        } else {

            System.out.println(
                    "Failed to add expense claim."
            );
        }
    }

    // ==========================================
    // UPDATE EXPENSE CLAIM
    // ==========================================

    private void updateExpenseClaimFromInput() {

        System.out.println(
                "========== UPDATE EXPENSE CLAIM =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println("Enter Claim Description:");

        String claimDesc =
                scanner.nextLine();

        System.out.println("Enter Claim Amount:");

        double claimAmount =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Claim Date (YYYY-MM-DD):"
        );

        LocalDate claimDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.println("Enter Status:");

        String status =
                scanner.nextLine();

        System.out.println("Enter Document Path:");

        String documentPath =
                scanner.nextLine();

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        employeeId,
                        claimDesc,
                        claimAmount,
                        claimDate,
                        status,
                        documentPath
                );

        expenseClaim.setClaimId(claimId);

        boolean result =
                updateExpenseClaim(expenseClaim);

        if (result) {

            System.out.println(
                    "Expense claim updated successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Expense claim update failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // GET EXPENSE CLAIM BY ID
    // ==========================================

    private void getExpenseClaimByIdFromInput() {

        System.out.println(
                "========== GET EXPENSE CLAIM BY ID =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        ExpenseClaim expenseClaim =
                getExpenseClaimById(claimId);

        if (expenseClaim != null) {

            System.out.println(
                    "Expense claim found:"
            );

            System.out.println(expenseClaim);

        } else {

            System.out.println(
                    "No expense claim found with ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // GET ALL EXPENSE CLAIMS
    // ==========================================

    private void displayAllExpenseClaims() {

        System.out.println(
                "========== ALL EXPENSE CLAIMS =========="
        );

        List<ExpenseClaim> claims =
                getAllExpenseClaims();

        if (claims.isEmpty()) {

            System.out.println(
                    "No expense claims found."
            );

        } else {

            System.out.println(
                    "Total expense claims found: "
                            + claims.size()
            );

            for (ExpenseClaim claim : claims) {

                System.out.println(claim);
            }
        }
    }

    // ==========================================
    // DELETE EXPENSE CLAIM
    // ==========================================

    private void deleteExpenseClaimFromInput() {

        System.out.println(
                "========== DELETE EXPENSE CLAIM =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                deleteExpenseClaimById(claimId);

        if (result) {

            System.out.println(
                    "Expense claim deleted successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Expense claim deletion failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // GET CLAIMS BY EMPLOYEE ID
    // ==========================================

    private void getClaimsByEmployeeIdFromInput() {

        System.out.println(
                "========== CLAIMS BY EMPLOYEE ID =========="
        );

        System.out.println("Enter Employee ID:");

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        List<ExpenseClaim> claims =
                getClaimsByEmployeeId(employeeId);

        if (claims.isEmpty()) {

            System.out.println(
                    "No claims found for Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Claims for Employee ID="
                            + employeeId
            );

            for (ExpenseClaim claim : claims) {

                System.out.println(claim);
            }
        }
    }

    // ==========================================
    // SUBMIT CLAIM
    // ==========================================

    private void submitClaimFromInput() {

        System.out.println(
                "========== SUBMIT CLAIM =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                submitClaim(claimId);

        if (result) {

            System.out.println(
                    "Claim submitted successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Claim submission failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // APPROVE CLAIM
    // ==========================================

    private void approveClaimFromInput() {

        System.out.println(
                "========== APPROVE CLAIM =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                approveClaim(claimId);

        if (result) {

            System.out.println(
                    "Claim approved successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Claim approval failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // REJECT CLAIM
    // ==========================================

    private void rejectClaimFromInput() {

        System.out.println(
                "========== REJECT CLAIM =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter rejection reason:"
        );

        String reason =
                scanner.nextLine();

        boolean result =
                rejectClaim(
                        claimId,
                        reason
                );

        if (result) {

            System.out.println(
                    "Claim rejected successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Claim rejection failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // GET CLAIMS BY STATUS
    // ==========================================

    private void getClaimsByStatusFromInput() {

        System.out.println(
                "========== CLAIMS BY STATUS =========="
        );

        System.out.println("Enter Status:");

        String status =
                scanner.nextLine();

        List<ExpenseClaim> claims =
                getClaimsByStatus(status);

        if (claims.isEmpty()) {

            System.out.println(
                    "No claims found with status="
                            + status
            );

        } else {

            System.out.println(
                    "Claims with status="
                            + status
            );

            for (ExpenseClaim claim : claims) {

                System.out.println(claim);
            }
        }
    }

    // ==========================================
    // SERVICE DELEGATION METHODS
    // ==========================================

    public ExpenseClaim addExpenseClaim(
            ExpenseClaim expenseClaim) {

        return expenseClaimService.addExpenseClaim(
                expenseClaim
        );
    }

    public boolean updateExpenseClaim(
            ExpenseClaim expenseClaim) {

        return expenseClaimService.updateExpenseClaim(
                expenseClaim
        );
    }

    public ExpenseClaim getExpenseClaimById(
            int claimId) {

        return expenseClaimService.getExpenseClaimById(
                claimId
        );
    }

    public List<ExpenseClaim> getAllExpenseClaims() {

        return expenseClaimService.getAllExpenseClaims();
    }

    public boolean deleteExpenseClaimById(
            int claimId) {

        return expenseClaimService.deleteExpenseClaimById(
                claimId
        );
    }

    public List<ExpenseClaim> getClaimsByEmployeeId(
            int employeeId) {

        return expenseClaimService.getClaimsByEmployeeId(
                employeeId
        );
    }

    public boolean submitClaim(int claimId) {

        return expenseClaimService.submitClaim(
                claimId
        );
    }

    public boolean approveClaim(int claimId) {

        return expenseClaimService.approveClaim(
                claimId
        );
    }

    public boolean rejectClaim(
            int claimId,
            String reason) {

        return expenseClaimService.rejectClaim(
                claimId,
                reason
        );
    }

    public List<ExpenseClaim> getClaimsByStatus(
            String status) {

        return expenseClaimService.getClaimsByStatus(
                status
        );
    }
}