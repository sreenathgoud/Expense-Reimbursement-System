 package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.service.IFinanceExecutiveService;

import java.util.List;
import java.util.Scanner;

public class FinanceExecutiveController {

    private final IFinanceExecutiveService financeExecutiveService;
    private final Scanner scanner;

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService) {

        this.financeExecutiveService =
                financeExecutiveService;

        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("       FINANCE EXECUTIVE");
            System.out.println("======================================");
            System.out.println("1. Get Pending Claims");
            System.out.println("2. Get Claim By ID");
            System.out.println("3. Process Payment");
            System.out.println("4. Get Reimbursement History");
            System.out.println("5. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":

                        displayPendingClaims();
                        break;

                    case "2":

                        getClaimByIdFromInput();
                        break;

                    case "3":

                        processPaymentFromInput();
                        break;

                    case "4":

                        displayReimbursementHistory();
                        break;

                    case "5":

                        running = false;

                        System.out.println(
                                "Returning to main menu."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid finance executive menu choice: "
                                        + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid finance executive input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in FinanceExecutiveController: "
                                + e.getMessage()
                );
            }
        }
    }

    // ==========================================
    // ADD FINANCE EXECUTIVE
    // ==========================================

    private void addFinanceExecutiveFromInput() {

        System.out.println(
                "========== ADD FINANCE EXECUTIVE =========="
        );

        System.out.println(
                "Enter Employee ID:"
        );

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Full Name:"
        );

        String fullName =
                scanner.nextLine();

        System.out.println(
                "Enter Email:"
        );

        String email =
                scanner.nextLine();

        System.out.println(
                "Enter Department:"
        );

        String department =
                scanner.nextLine();

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        department
                );

        FinanceExecutive result =
                addNewFinanceExecutive(
                        financeExecutive
                );

        if (result != null) {

            System.out.println(
                    "Finance executive added successfully. "
                            + "Employee ID="
                            + result.getEmployeeId()
            );

        } else {

            System.out.println(
                    "Failed to add finance executive."
            );
        }
    }

    // ==========================================
    // UPDATE FINANCE EXECUTIVE
    // ==========================================

    private void updateFinanceExecutiveFromInput() {

        System.out.println(
                "========== UPDATE FINANCE EXECUTIVE =========="
        );

        System.out.println(
                "Enter Employee ID:"
        );

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Full Name:"
        );

        String fullName =
                scanner.nextLine();

        System.out.println(
                "Enter Email:"
        );

        String email =
                scanner.nextLine();

        System.out.println(
                "Enter Department:"
        );

        String department =
                scanner.nextLine();

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        department
                );

        boolean result =
                updateFinanceExecutive(
                        financeExecutive
                );

        if (result) {

            System.out.println(
                    "Finance executive updated successfully. "
                            + "Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Finance executive update failed. "
                            + "Employee ID="
                            + employeeId
            );
        }
    }

    // ==========================================
    // GET FINANCE EXECUTIVE BY ID
    // ==========================================

    private void getFinanceExecutiveByIdFromInput() {

        System.out.println(
                "========== GET FINANCE EXECUTIVE BY ID =========="
        );

        System.out.println(
                "Enter Employee ID:"
        );

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        FinanceExecutive financeExecutive =
                getFinanceExecutiveById(
                        employeeId
                );

        if (financeExecutive != null) {

            System.out.println(
                    "Finance executive found:"
            );

            System.out.println(
                    financeExecutive
            );

        } else {

            System.out.println(
                    "No finance executive found "
                            + "with Employee ID="
                            + employeeId
            );
        }
    }

    // ==========================================
    // GET ALL FINANCE EXECUTIVES
    // ==========================================

    private void displayAllFinanceExecutives() {

        System.out.println(
                "========== ALL FINANCE EXECUTIVES =========="
        );

        List<FinanceExecutive> executives =
                getAllFinanceExecutives();

        if (executives.isEmpty()) {

            System.out.println(
                    "No finance executives found."
            );

        } else {

            System.out.println(
                    "Total finance executives found: "
                            + executives.size()
            );

            for (FinanceExecutive executive :
                    executives) {

                System.out.println(
                        executive
                );
            }
        }
    }

    // ==========================================
    // DELETE FINANCE EXECUTIVE
    // ==========================================

    private void deleteFinanceExecutiveFromInput() {

        System.out.println(
                "========== DELETE FINANCE EXECUTIVE =========="
        );

        System.out.println(
                "Enter Employee ID:"
        );

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                deleteFinanceExecutiveById(
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Finance executive deleted successfully. "
                            + "Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Finance executive deletion failed. "
                            + "Employee ID="
                            + employeeId
            );
        }
    }

    // ==========================================
    // PENDING CLAIMS
    // ==========================================

    private void displayPendingClaims() {

        System.out.println(
                "========== PENDING CLAIMS =========="
        );

        List<ExpenseClaim> claims =
                getPendingClaims();

        if (claims.isEmpty()) {

            System.out.println(
                    "No pending claims found."
            );

        } else {

            System.out.println(
                    "Total pending claims: "
                            + claims.size()
            );

            for (ExpenseClaim claim :
                    claims) {

                System.out.println(
                        claim
                );
            }
        }
    }

    // ==========================================
    // GET CLAIM BY ID
    // ==========================================

    private void getClaimByIdFromInput() {

        System.out.println(
                "========== GET CLAIM BY ID =========="
        );

        System.out.println(
                "Enter Claim ID:"
        );

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        ExpenseClaim claim =
                getClaimById(
                        claimId
                );

        if (claim != null) {

            System.out.println(
                    "Claim found:"
            );

            System.out.println(
                    claim
            );

        } else {

            System.out.println(
                    "No claim found with ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // PROCESS PAYMENT
    // ==========================================

    private void processPaymentFromInput() {

        System.out.println(
                "========== PROCESS PAYMENT =========="
        );

        System.out.println(
                "Enter Claim ID:"
        );

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Finance Executive ID:"
        );

        int financeExecutiveId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Payment Mode:"
        );

        String paymentMode =
                scanner.nextLine();

        boolean result =
                processPayment(
                        claimId,
                        financeExecutiveId,
                        paymentMode
                );

        if (result) {

            System.out.println(
                    "Payment processed successfully. "
                            + "Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Payment processing failed. "
                            + "Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // REIMBURSEMENT HISTORY
    // ==========================================

    private void displayReimbursementHistory() {

        System.out.println(
                "========== REIMBURSEMENT HISTORY =========="
        );

        System.out.println(
                "Enter Finance Executive ID:"
        );

        int financeExecutiveId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        List<Reimbursement> reimbursements =
                getReimbursementHistory(
                        financeExecutiveId
                );

        if (reimbursements.isEmpty()) {

            System.out.println(
                    "No reimbursement history found."
            );

        } else {

            System.out.println(
                    "Total reimbursements: "
                            + reimbursements.size()
            );

            for (Reimbursement reimbursement :
                    reimbursements) {

                System.out.println(
                        reimbursement
                );
            }
        }
    }

    // ==========================================
    // SERVICE DELEGATION METHODS
    // ==========================================

    public FinanceExecutive addNewFinanceExecutive(
            FinanceExecutive financeExecutive) {

        return financeExecutiveService.addFinanceExecutive(
                financeExecutive
        );
    }

    public boolean updateFinanceExecutive(
            FinanceExecutive financeExecutive) {

        return financeExecutiveService.updateFinanceExecutive(
                financeExecutive
        );
    }

    public FinanceExecutive getFinanceExecutiveById(
            int employeeId) {

        return financeExecutiveService.getFinanceExecutiveById(
                employeeId
        );
    }

    public List<FinanceExecutive> getAllFinanceExecutives() {

        return financeExecutiveService
                .getAllFinanceExecutives();
    }

    public boolean deleteFinanceExecutiveById(
            int financeExecutiveId) {

        return financeExecutiveService
                .deleteFinanceExecutiveById(
                        financeExecutiveId
                );
    }

    public List<ExpenseClaim> getPendingClaims() {

        return financeExecutiveService
                .getPendingClaims();
    }

    public ExpenseClaim getClaimById(
            int claimId) {

        return financeExecutiveService
                .getClaimById(
                        claimId
                );
    }

    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            String paymentMode) {

        return financeExecutiveService
                .processPayment(
                        claimId,
                        financeExecutiveId,
                        paymentMode
                );
    }

    public List<Reimbursement> getReimbursementHistory(
            int financeExecutiveId) {

        return financeExecutiveService
                .getReimbursementHistory(
                        financeExecutiveId
                );
    }
}
