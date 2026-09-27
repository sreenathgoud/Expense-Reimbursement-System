package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.Reimbursement;
import com.ers.service.IReimbursementService;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReimbursementController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(
                    ReimbursementController.class
            );

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

            logger.info("======================================");
            logger.info("          REIMBURSEMENT MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Reimbursement");
            logger.info("2. Update Reimbursement");
            logger.info("3. Get Reimbursement By ID");
            logger.info("4. Get All Reimbursements");
            logger.info("5. Delete Reimbursement");
            logger.info("6. Get Reimbursement By Claim ID");
            logger.info("7. Get Reimbursements By Employee ID");
            logger.info("8. Get Reimbursements By Status");
            logger.info("9. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

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
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid reimbursement input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in ReimbursementController.",
                        e
                );
            }
        }
    }

    private void addReimbursementFromInput() {

        logger.info("========== ADD REIMBURSEMENT ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Reimbursed Amount:");
        double reimbursedAmount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Payment Mode:");
        String paymentMode = scanner.nextLine();

        logger.info("Enter Transaction Reference:");
        String transactionRef = scanner.nextLine();

        logger.info("Enter Reimbursement Date (YYYY-MM-DD):");
        LocalDate reimbursementDate =
                LocalDate.parse(scanner.nextLine());

        logger.info("Enter Finance Executive ID:");
        int processedBy =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Status:");
        String status = scanner.nextLine();

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

            logger.info(
                    "Reimbursement added successfully. ID={}",
                    result.getReimbursementId()
            );

        } else {

            logger.warn("Failed to add reimbursement.");
        }
    }

    private void updateReimbursementFromInput() {

        logger.info("========== UPDATE REIMBURSEMENT ==========");

        logger.info("Enter Reimbursement ID:");
        int reimbursementId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Reimbursed Amount:");
        double reimbursedAmount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Payment Mode:");
        String paymentMode = scanner.nextLine();

        logger.info("Enter Transaction Reference:");
        String transactionRef = scanner.nextLine();

        logger.info("Enter Reimbursement Date (YYYY-MM-DD):");
        LocalDate reimbursementDate =
                LocalDate.parse(scanner.nextLine());

        logger.info("Enter Finance Executive ID:");
        int processedBy =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Status:");
        String status = scanner.nextLine();

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
                updateReimbursement(reimbursement);

        if (result) {

            logger.info(
                    "Reimbursement updated successfully. ID={}",
                    reimbursementId
            );

        } else {

            logger.warn(
                    "Reimbursement update failed. ID={}",
                    reimbursementId
            );
        }
    }

    private void getReimbursementByIdFromInput() {

        logger.info(
                "========== GET REIMBURSEMENT BY ID =========="
        );

        logger.info("Enter Reimbursement ID:");
        int reimbursementId =
                Integer.parseInt(scanner.nextLine());

        Reimbursement reimbursement =
                getReimbursementById(reimbursementId);

        if (reimbursement != null) {

            logger.info("Reimbursement found:");
            logger.info("{}", reimbursement);

        } else {

            logger.warn(
                    "No reimbursement found with ID={}",
                    reimbursementId
            );
        }
    }

    private void displayAllReimbursements() {

        logger.info("========== ALL REIMBURSEMENTS ==========");

        List<Reimbursement> reimbursements =
                getAllReimbursements();

        if (reimbursements.isEmpty()) {

            logger.info("No reimbursements found.");

        } else {

            logger.info(
                    "Total reimbursements found: {}",
                    reimbursements.size()
            );

            for (Reimbursement reimbursement :
                    reimbursements) {

                logger.info("{}", reimbursement);
            }
        }
    }

    private void deleteReimbursementFromInput() {

        logger.info(
                "========== DELETE REIMBURSEMENT =========="
        );

        logger.info("Enter Reimbursement ID:");
        int reimbursementId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteReimbursementById(reimbursementId);

        if (result) {

            logger.info(
                    "Reimbursement deleted successfully. ID={}",
                    reimbursementId
            );

        } else {

            logger.warn(
                    "Reimbursement deletion failed. ID={}",
                    reimbursementId
            );
        }
    }

    private void getReimbursementByClaimIdFromInput() {

        logger.info(
                "========== REIMBURSEMENT BY CLAIM ID =========="
        );

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        Reimbursement reimbursement =
                getReimbursementByClaimId(claimId);

        if (reimbursement != null) {

            logger.info("Reimbursement found:");
            logger.info("{}", reimbursement);

        } else {

            logger.warn(
                    "No reimbursement found for Claim ID={}",
                    claimId
            );
        }
    }

    private void getReimbursementsByEmployeeIdFromInput() {

        logger.info(
                "========== REIMBURSEMENTS BY EMPLOYEE ID =========="
        );

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        List<Reimbursement> reimbursements =
                getReimbursementsByEmployeeId(employeeId);

        if (reimbursements.isEmpty()) {

            logger.info(
                    "No reimbursements found for Employee ID={}",
                    employeeId
            );

        } else {

            logger.info(
                    "Reimbursements for Employee ID={}:",
                    employeeId
            );

            for (Reimbursement reimbursement :
                    reimbursements) {

                logger.info("{}", reimbursement);
            }
        }
    }

    private void getReimbursementsByStatusFromInput() {

        logger.info(
                "========== REIMBURSEMENTS BY STATUS =========="
        );

        logger.info("Enter Status:");
        String status = scanner.nextLine();

        List<Reimbursement> reimbursements =
                getReimbursementsByStatus(status);

        if (reimbursements.isEmpty()) {

            logger.info(
                    "No reimbursements found with status={}",
                    status
            );

        } else {

            logger.info(
                    "Reimbursements with status={}:",
                    status
            );

            for (Reimbursement reimbursement :
                    reimbursements) {

                logger.info("{}", reimbursement);
            }
        }
    }

    // Service delegation methods

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