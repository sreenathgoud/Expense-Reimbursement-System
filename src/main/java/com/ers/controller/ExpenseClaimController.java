package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.ExpenseClaim;
import com.ers.service.IExpenseClaimService;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseClaimController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ExpenseClaimController.class);

    private final IExpenseClaimService expenseClaimService;
    private final Scanner scanner;

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService) {

        this.expenseClaimService = expenseClaimService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            logger.info("======================================");
            logger.info("       EXPENSE CLAIM MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Expense Claim");
            logger.info("2. Update Expense Claim");
            logger.info("3. Get Expense Claim By ID");
            logger.info("4. Get All Expense Claims");
            logger.info("5. Delete Expense Claim");
            logger.info("6. Get Claims By Employee ID");
            logger.info("7. Submit Claim");
            logger.info("8. Approve Claim");
            logger.info("9. Reject Claim");
            logger.info("10. Get Claims By Status");
            logger.info("11. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

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
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid expense claim input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in ExpenseClaimController.",
                        e
                );
            }
        }
    }

    private void addExpenseClaimFromInput() {

        logger.info("========== ADD EXPENSE CLAIM ==========");

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Claim Description:");
        String claimDesc = scanner.nextLine();

        logger.info("Enter Claim Amount:");
        double claimAmount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Claim Date (YYYY-MM-DD):");
        LocalDate claimDate =
                LocalDate.parse(scanner.nextLine());

        logger.info("Enter Status:");
        String status = scanner.nextLine();

        logger.info("Enter Document Path:");
        String documentPath = scanner.nextLine();

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

            logger.info(
                    "Expense claim added successfully. Claim ID={}",
                    result.getClaimId()
            );

        } else {

            logger.warn("Failed to add expense claim.");
        }
    }

    private void updateExpenseClaimFromInput() {

        logger.info("========== UPDATE EXPENSE CLAIM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Claim Description:");
        String claimDesc = scanner.nextLine();

        logger.info("Enter Claim Amount:");
        double claimAmount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Claim Date (YYYY-MM-DD):");
        LocalDate claimDate =
                LocalDate.parse(scanner.nextLine());

        logger.info("Enter Status:");
        String status = scanner.nextLine();

        logger.info("Enter Document Path:");
        String documentPath = scanner.nextLine();

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

            logger.info(
                    "Expense claim updated successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Expense claim update failed. Claim ID={}",
                    claimId
            );
        }
    }

    private void getExpenseClaimByIdFromInput() {

        logger.info(
                "========== GET EXPENSE CLAIM BY ID =========="
        );

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        ExpenseClaim expenseClaim =
                getExpenseClaimById(claimId);

        if (expenseClaim != null) {

            logger.info("Expense claim found:");
            logger.info("{}", expenseClaim);

        } else {

            logger.warn(
                    "No expense claim found with ID={}",
                    claimId
            );
        }
    }

    private void displayAllExpenseClaims() {

        logger.info("========== ALL EXPENSE CLAIMS ==========");

        List<ExpenseClaim> claims =
                getAllExpenseClaims();

        if (claims.isEmpty()) {

            logger.info("No expense claims found.");

        } else {

            logger.info(
                    "Total expense claims found: {}",
                    claims.size()
            );

            for (ExpenseClaim claim : claims) {
                logger.info("{}", claim);
            }
        }
    }

    private void deleteExpenseClaimFromInput() {

        logger.info("========== DELETE EXPENSE CLAIM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteExpenseClaimById(claimId);

        if (result) {

            logger.info(
                    "Expense claim deleted successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Expense claim deletion failed. Claim ID={}",
                    claimId
            );
        }
    }

    private void getClaimsByEmployeeIdFromInput() {

        logger.info(
                "========== CLAIMS BY EMPLOYEE ID =========="
        );

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        List<ExpenseClaim> claims =
                getClaimsByEmployeeId(employeeId);

        if (claims.isEmpty()) {

            logger.info(
                    "No claims found for Employee ID={}",
                    employeeId
            );

        } else {

            logger.info(
                    "Claims for Employee ID={}:",
                    employeeId
            );

            for (ExpenseClaim claim : claims) {
                logger.info("{}", claim);
            }
        }
    }

    private void submitClaimFromInput() {

        logger.info("========== SUBMIT CLAIM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                submitClaim(claimId);

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

    private void approveClaimFromInput() {

        logger.info("========== APPROVE CLAIM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                approveClaim(claimId);

        if (result) {

            logger.info(
                    "Claim approved successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Claim approval failed. Claim ID={}",
                    claimId
            );
        }
    }

    private void rejectClaimFromInput() {

        logger.info("========== REJECT CLAIM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter rejection reason:");
        String reason = scanner.nextLine();

        boolean result =
                rejectClaim(claimId, reason);

        if (result) {

            logger.info(
                    "Claim rejected successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Claim rejection failed. Claim ID={}",
                    claimId
            );
        }
    }

    private void getClaimsByStatusFromInput() {

        logger.info("========== CLAIMS BY STATUS ==========");

        logger.info("Enter Status:");
        String status = scanner.nextLine();

        List<ExpenseClaim> claims =
                getClaimsByStatus(status);

        if (claims.isEmpty()) {

            logger.info(
                    "No claims found with status={}",
                    status
            );

        } else {

            logger.info(
                    "Claims with status={}:",
                    status
            );

            for (ExpenseClaim claim : claims) {
                logger.info("{}", claim);
            }
        }
    }

    // Service delegation methods

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

        return expenseClaimService.submitClaim(claimId);
    }

    public boolean approveClaim(int claimId) {

        return expenseClaimService.approveClaim(claimId);
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

        return expenseClaimService.getClaimsByStatus(status);
    }
}