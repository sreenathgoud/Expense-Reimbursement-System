package com.ers.controller;
import com.ers.model.Reimbursement;
import com.ers.service.IReimbursementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReimbursementController {

    private final IReimbursementService reimbursementService;
    private final Scanner scanner;

    public ReimbursementController(
            IReimbursementService reimbursementService) {

        this.reimbursementService =
                reimbursementService;

        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "          REIMBURSEMENT MANAGEMENT"
            );

            System.out.println(
                    "======================================"
            );

            System.out.println("1. Add Reimbursement");
            System.out.println("2. Update Reimbursement");
            System.out.println("3. Get Reimbursement By ID");
            System.out.println("4. Get All Reimbursements");
            System.out.println("5. Delete Reimbursement");
            System.out.println("6. Get Reimbursement By Claim ID");
            System.out.println("7. Get Reimbursements By Employee ID");
            System.out.println("8. Get Reimbursements By Status");
            System.out.println("9. Back");

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Enter your choice:"
            );

            String choice =
                    scanner.nextLine();

            try {

                switch (choice) {

                    case "1":

                        addReimbursementFromInput();

                        break;

                    case "2":

                        updateReimbursementFromInput();

                        break;

                    case "3":

                        getReimbursementByIdFromInput();

                        break;

                    case "4":

                        displayAllReimbursements();

                        break;

                    case "5":

                        deleteReimbursementFromInput();

                        break;

                    case "6":

                        getReimbursementByClaimIdFromInput();

                        break;

                    case "7":

                        getReimbursementsByEmployeeIdFromInput();

                        break;

                    case "8":

                        getReimbursementsByStatusFromInput();

                        break;

                    case "9":

                        running = false;

                        System.out.println(
                                "Returning to main menu."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid menu choice: "
                                        + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid reimbursement input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in ReimbursementController: "
                                + e.getMessage()
                );

                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // ADD REIMBURSEMENT
    // ==========================================

    private void addReimbursementFromInput() {

        System.out.println(
                "========== ADD REIMBURSEMENT =========="
        );

        System.out.println(
                "Enter Claim ID:"
        );

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Reimbursed Amount:"
        );

        double reimbursedAmount =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Payment Mode:"
        );

        String paymentMode =
                scanner.nextLine();

        System.out.println(
                "Enter Transaction Reference:"
        );

        String transactionRef =
                scanner.nextLine();

        System.out.println(
                "Enter Reimbursement Date (YYYY-MM-DD):"
        );

        LocalDate reimbursementDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Finance Executive ID:"
        );

        int processedBy =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Status:"
        );

        String status =
                scanner.nextLine();

        Reimbursement reimbursement =
                new Reimbursement(
                        claimId,
                        reimbursedAmount,
                        paymentMode,
                        transactionRef,
                        reimbursementDate,
                        processedBy,
                        status
                );

        Reimbursement result =
                addReimbursement(reimbursement);

        if (result != null) {

            System.out.println(
                    "Reimbursement added successfully. ID="
                            + result.getReimbursementId()
            );

        } else {

            System.out.println(
                    "Failed to add reimbursement."
            );
        }
    }

    // ==========================================
    // UPDATE REIMBURSEMENT
    // ==========================================

    private void updateReimbursementFromInput() {

        System.out.println(
                "========== UPDATE REIMBURSEMENT =========="
        );

        System.out.println(
                "Enter Reimbursement ID:"
        );

        int reimbursementId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Claim ID:"
        );

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Reimbursed Amount:"
        );

        double reimbursedAmount =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Payment Mode:"
        );

        String paymentMode =
                scanner.nextLine();

        System.out.println(
                "Enter Transaction Reference:"
        );

        String transactionRef =
                scanner.nextLine();

        System.out.println(
                "Enter Reimbursement Date (YYYY-MM-DD):"
        );

        LocalDate reimbursementDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Finance Executive ID:"
        );

        int processedBy =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println(
                "Enter Status:"
        );

        String status =
                scanner.nextLine();

        Reimbursement reimbursement =
                new Reimbursement(
                        claimId,
                        reimbursedAmount,
                        paymentMode,
                        transactionRef,
                        reimbursementDate,
                        processedBy,
                        status
                );

        reimbursement.setReimbursementId(
                reimbursementId
        );

        boolean result =
                updateReimbursement(
                        reimbursement
                );

        if (result) {

            System.out.println(
                    "Reimbursement updated successfully. ID="
                            + reimbursementId
            );

        } else {

            System.out.println(
                    "Reimbursement update failed. ID="
                            + reimbursementId
            );
        }
    }

    // ==========================================
    // GET REIMBURSEMENT BY ID
    // ==========================================

    private void getReimbursementByIdFromInput() {

        System.out.println(
                "========== GET REIMBURSEMENT BY ID =========="
        );

        System.out.println(
                "Enter Reimbursement ID:"
        );

        int reimbursementId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        Reimbursement reimbursement =
                getReimbursementById(
                        reimbursementId
                );

        if (reimbursement != null) {

            System.out.println(
                    "Reimbursement found:"
            );

            System.out.println(
                    reimbursement
            );

        } else {

            System.out.println(
                    "No reimbursement found with ID="
                            + reimbursementId
            );
        }
    }

    // ==========================================
    // GET ALL REIMBURSEMENTS
    // ==========================================

    private void displayAllReimbursements() {

        System.out.println(
                "========== ALL REIMBURSEMENTS =========="
        );

        List<Reimbursement> reimbursements =
                getAllReimbursements();

        if (reimbursements.isEmpty()) {

            System.out.println(
                    "No reimbursements found."
            );

        } else {

            System.out.println(
                    "Total reimbursements found: "
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
    // DELETE REIMBURSEMENT
    // ==========================================

    private void deleteReimbursementFromInput() {

        System.out.println(
                "========== DELETE REIMBURSEMENT =========="
        );

        System.out.println(
                "Enter Reimbursement ID:"
        );

        int reimbursementId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        boolean result =
                deleteReimbursementById(
                        reimbursementId
                );

        if (result) {

            System.out.println(
                    "Reimbursement deleted successfully. ID="
                            + reimbursementId
            );

        } else {

            System.out.println(
                    "Reimbursement deletion failed. ID="
                            + reimbursementId
            );
        }
    }

    // ==========================================
    // GET REIMBURSEMENT BY CLAIM ID
    // ==========================================

    private void getReimbursementByClaimIdFromInput() {

        System.out.println(
                "========== REIMBURSEMENT BY CLAIM ID =========="
        );

        System.out.println(
                "Enter Claim ID:"
        );

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        Reimbursement reimbursement =
                getReimbursementByClaimId(
                        claimId
                );

        if (reimbursement != null) {

            System.out.println(
                    "Reimbursement found:"
            );

            System.out.println(
                    reimbursement
            );

        } else {

            System.out.println(
                    "No reimbursement found for Claim ID="
                            + claimId
            );
        }
    }

    // ==========================================
    // GET REIMBURSEMENTS BY EMPLOYEE ID
    // ==========================================

    private void getReimbursementsByEmployeeIdFromInput() {

        System.out.println(
                "========== REIMBURSEMENTS BY EMPLOYEE ID =========="
        );

        System.out.println(
                "Enter Employee ID:"
        );

        int employeeId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        List<Reimbursement> reimbursements =
                getReimbursementsByEmployeeId(
                        employeeId
                );

        if (reimbursements.isEmpty()) {

            System.out.println(
                    "No reimbursements found for Employee ID="
                            + employeeId
            );

        } else {

            System.out.println(
                    "Reimbursements for Employee ID="
                            + employeeId
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
    // GET REIMBURSEMENTS BY STATUS
    // ==========================================

    private void getReimbursementsByStatusFromInput() {

        System.out.println(
                "========== REIMBURSEMENTS BY STATUS =========="
        );

        System.out.println(
                "Enter Status:"
        );

        String status =
                scanner.nextLine();

        List<Reimbursement> reimbursements =
                getReimbursementsByStatus(
                        status
                );

        if (reimbursements.isEmpty()) {

            System.out.println(
                    "No reimbursements found with status="
                            + status
            );

        } else {

            System.out.println(
                    "Reimbursements with status="
                            + status
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

    public Reimbursement addReimbursement(
            Reimbursement reimbursement) {

        return reimbursementService.addReimbursement(
                reimbursement
        );
    }

    public boolean updateReimbursement(
            Reimbursement reimbursement) {

        return reimbursementService.updateReimbursement(
                reimbursement
        );
    }

    public Reimbursement getReimbursementById(
            int reimbursementId) {

        return reimbursementService.getReimbursementById(
                reimbursementId
        );
    }

    public List<Reimbursement> getAllReimbursements() {

        return reimbursementService.getAllReimbursements();
    }

    public boolean deleteReimbursementById(
            int reimbursementId) {

        return reimbursementService.deleteReimbursementById(
                reimbursementId
        );
    }

    public Reimbursement getReimbursementByClaimId(
            int claimId) {

        return reimbursementService.getReimbursementByClaimId(
                claimId
        );
    }

    public List<Reimbursement> getReimbursementsByEmployeeId(
            int employeeId) {

        return reimbursementService.getReimbursementsByEmployeeId(
                employeeId
        );
    }

    public List<Reimbursement> getReimbursementsByStatus(
            String status) {

        return reimbursementService.getReimbursementsByStatus(
                status
        );
    }
}
