package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.service.IFinanceExecutiveService;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class FinanceExecutiveController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(
                    FinanceExecutiveController.class
            );

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

            logger.info("======================================");
            logger.info("       FINANCE EXECUTIVE");
            logger.info("======================================");
            logger.info("1. Add Finance Executive");
            logger.info("2. Update Finance Executive");
            logger.info("3. Get Finance Executive By ID");
            logger.info("4. Get All Finance Executives");
            logger.info("5. Delete Finance Executive");
            logger.info("6. Get Pending Claims");
            logger.info("7. Get Claim By ID");
            logger.info("8. Process Payment");
            logger.info("9. Get Reimbursement History");
            logger.info("10. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addFinanceExecutiveFromInput();
                        break;

                    case "2":
                        updateFinanceExecutiveFromInput();
                        break;

                    case "3":
                        getFinanceExecutiveByIdFromInput();
                        break;

                    case "4":
                        displayAllFinanceExecutives();
                        break;

                    case "5":
                        deleteFinanceExecutiveFromInput();
                        break;

                    case "6":
                        displayPendingClaims();
                        break;

                    case "7":
                        getClaimByIdFromInput();
                        break;

                    case "8":
                        processPaymentFromInput();
                        break;

                    case "9":
                        displayReimbursementHistory();
                        break;

                    case "10":
                        running = false;
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid finance executive input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in FinanceExecutiveController.",
                        e
                );
            }
        }
    }

    private void addFinanceExecutiveFromInput() {

        logger.info("========== ADD FINANCE EXECUTIVE ==========");

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Full Name:");
        String fullName = scanner.nextLine();

        logger.info("Enter Email:");
        String email = scanner.nextLine();

        logger.info("Enter Department:");
        String department = scanner.nextLine();

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        department
                );

        FinanceExecutive result =
                addNewFinanceExecutive(financeExecutive);

        if (result != null) {

            logger.info(
                    "Finance executive added successfully. Employee ID={}",
                    result.getEmployeeId()
            );

        } else {

            logger.warn("Failed to add finance executive.");
        }
    }

    private void updateFinanceExecutiveFromInput() {

        logger.info("========== UPDATE FINANCE EXECUTIVE ==========");

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Full Name:");
        String fullName = scanner.nextLine();

        logger.info("Enter Email:");
        String email = scanner.nextLine();

        logger.info("Enter Department:");
        String department = scanner.nextLine();

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        department
                );

        boolean result =
                updateFinanceExecutive(financeExecutive);

        if (result) {

            logger.info(
                    "Finance executive updated successfully. Employee ID={}",
                    employeeId
            );

        } else {

            logger.warn(
                    "Finance executive update failed. Employee ID={}",
                    employeeId
            );
        }
    }

    private void getFinanceExecutiveByIdFromInput() {

        logger.info(
                "========== GET FINANCE EXECUTIVE BY ID =========="
        );

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        FinanceExecutive financeExecutive =
                getFinanceExecutiveById(employeeId);

        if (financeExecutive != null) {

            logger.info("Finance executive found:");
            logger.info("{}", financeExecutive);

        } else {

            logger.warn(
                    "No finance executive found with Employee ID={}",
                    employeeId
            );
        }
    }

    private void displayAllFinanceExecutives() {

        logger.info("========== ALL FINANCE EXECUTIVES ==========");

        List<FinanceExecutive> executives =
                getAllFinanceExecutives();

        if (executives.isEmpty()) {

            logger.info("No finance executives found.");

        } else {

            logger.info(
                    "Total finance executives found: {}",
                    executives.size()
            );

            for (FinanceExecutive executive : executives) {
                logger.info("{}", executive);
            }
        }
    }

    private void deleteFinanceExecutiveFromInput() {

        logger.info(
                "========== DELETE FINANCE EXECUTIVE =========="
        );

        logger.info("Enter Employee ID:");
        int employeeId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteFinanceExecutiveById(employeeId);

        if (result) {

            logger.info(
                    "Finance executive deleted successfully. Employee ID={}",
                    employeeId
            );

        } else {

            logger.warn(
                    "Finance executive deletion failed. Employee ID={}",
                    employeeId
            );
        }
    }

    private void displayPendingClaims() {

        logger.info("========== PENDING CLAIMS ==========");

        List<ExpenseClaim> claims =
                getPendingClaims();

        if (claims.isEmpty()) {

            logger.info("No pending claims found.");

        } else {

            logger.info(
                    "Total pending claims: {}",
                    claims.size()
            );

            for (ExpenseClaim claim : claims) {
                logger.info("{}", claim);
            }
        }
    }

    private void getClaimByIdFromInput() {

        logger.info("========== GET CLAIM BY ID ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        ExpenseClaim claim =
                getClaimById(claimId);

        if (claim != null) {

            logger.info("Claim found:");
            logger.info("{}", claim);

        } else {

            logger.warn(
                    "No claim found with ID={}",
                    claimId
            );
        }
    }

    private void processPaymentFromInput() {

        logger.info("========== PROCESS PAYMENT ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Finance Executive ID:");
        int financeExecutiveId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Payment Mode:");
        String paymentMode =
                scanner.nextLine();

        boolean result =
                processPayment(
                        claimId,
                        financeExecutiveId,
                        paymentMode
                );

        if (result) {

            logger.info(
                    "Payment processed successfully. Claim ID={}",
                    claimId
            );

        } else {

            logger.warn(
                    "Payment processing failed. Claim ID={}",
                    claimId
            );
        }
    }

    private void displayReimbursementHistory() {

        logger.info(
                "========== REIMBURSEMENT HISTORY =========="
        );

        logger.info("Enter Finance Executive ID:");
        int financeExecutiveId =
                Integer.parseInt(scanner.nextLine());

        List<Reimbursement> reimbursements =
                getReimbursementHistory(
                        financeExecutiveId
                );

        if (reimbursements.isEmpty()) {

            logger.info(
                    "No reimbursement history found."
            );

        } else {

            logger.info(
                    "Total reimbursements: {}",
                    reimbursements.size()
            );

            for (Reimbursement reimbursement :
                    reimbursements) {

                logger.info("{}", reimbursement);
            }
        }
    }

    // Service delegation methods

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

        return financeExecutiveService.getAllFinanceExecutives();
    }

    public boolean deleteFinanceExecutiveById(
            int financeExecutiveId) {

        return financeExecutiveService.deleteFinanceExecutiveById(
                financeExecutiveId
        );
    }

    public List<ExpenseClaim> getPendingClaims() {

        return financeExecutiveService.getPendingClaims();
    }

    public ExpenseClaim getClaimById(int claimId) {

        return financeExecutiveService.getClaimById(
                claimId
        );
    }

    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            String paymentMode) {

        return financeExecutiveService.processPayment(
                claimId,
                financeExecutiveId,
                paymentMode
        );
    }

    public List<Reimbursement> getReimbursementHistory(
            int financeExecutiveId) {

        return financeExecutiveService.getReimbursementHistory(
                financeExecutiveId
        );
    }
}