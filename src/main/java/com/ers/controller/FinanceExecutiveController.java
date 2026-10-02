package com.ers.controller;

import com.ers.dao.DepartmentDaoImpl;
import com.ers.model.Department;
import com.ers.model.Employee;
import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.service.IFinanceExecutiveService;
import com.ers.service.IEmployeeService;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class FinanceExecutiveController {

    private final IFinanceExecutiveService financeExecutiveService;
    private final Scanner scanner;
    private final Integer currentFinanceExecutiveEmployeeId;
    private final IEmployeeService employeeService;
        private final String currentRole;

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService) {

        this(financeExecutiveService, null);
    }

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService,
            Integer currentFinanceExecutiveEmployeeId) {

        this(financeExecutiveService, currentFinanceExecutiveEmployeeId, null);
    }

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService,
            Integer currentFinanceExecutiveEmployeeId,
            IEmployeeService employeeService) {

        this(financeExecutiveService, currentFinanceExecutiveEmployeeId, employeeService, null);
    }

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService,
            Integer currentFinanceExecutiveEmployeeId,
            IEmployeeService employeeService,
            String currentRole) {

        this.financeExecutiveService =
                financeExecutiveService;

        this.scanner = new Scanner(System.in);
        this.currentFinanceExecutiveEmployeeId = currentFinanceExecutiveEmployeeId;
        this.employeeService = employeeService;
        this.currentRole = currentRole;
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
            System.out.println("5. Finance Dashboard");
            System.out.println("6. Back");
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

                        displayFinanceDashboard();
                        break;

                    case "6":

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

        public boolean addFinanceExecutiveForAdmin() {
                if (!"ADMIN".equalsIgnoreCase(currentRole)) {
                        System.out.println("Access denied: only Admin can add finance executives.");
                        return false;
                }

                addFinanceExecutiveFromInput();
                return true;
        }

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

        List<Department> departments = new DepartmentDaoImpl().getAllDepartments();
        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getDepartmentName());
        }

        int departmentChoice = Integer.parseInt(scanner.nextLine());
        Department selectedDepartment = departments.get(departmentChoice - 1);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        selectedDepartment.getDepartmentId()
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

        List<Department> departments = new DepartmentDaoImpl().getAllDepartments();
        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getDepartmentName());
        }

        int departmentChoice = Integer.parseInt(scanner.nextLine());
        Department selectedDepartment = departments.get(departmentChoice - 1);

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        employeeId,
                        fullName,
                        email,
                        selectedDepartment.getDepartmentId()
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

        int financeExecutiveEmployeeId = resolveFinanceExecutiveEmployeeId();

        List<ExpenseClaim> claims =
                getPendingClaimsForFinanceExecutive(financeExecutiveEmployeeId);

        if (claims.isEmpty()) {

            System.out.println(
                    "No approved claims are available for this finance executive department."
            );

        } else {

            System.out.println(
                    "Total pending approved claims: "
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

        int financeExecutiveId = resolveFinanceExecutiveEmployeeId();
        ExpenseClaim claim = financeExecutiveService.getApprovedClaimByIdForFinanceExecutive(
                claimId, financeExecutiveId
        );

        if (claim != null) {

            System.out.println(
                    "Claim found:"
            );

            System.out.println(
                    claim
            );
            if (employeeService != null) {
                Employee employee = employeeService.getEmployeeById(claim.getEmployeeId());
                if (employee != null) {
                    System.out.println("Employee Name: " + employee.getFullName());
                    System.out.println("Employee Email: " + employee.getEmail());
                    System.out.println("Department ID: " + employee.getDepartmentId());
                }
            }

        } else {

            System.out.println(
                    "No approved claim found in your department with ID="
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

        int financeExecutiveId = resolveFinanceExecutiveEmployeeId();

        System.out.println("Enter Reimbursed Amount:");
        double reimbursedAmount = Double.parseDouble(scanner.nextLine());

        System.out.println(
                "Payment Mode\n1. BANK_TRANSFER\n2. CASH\n3. UPI\n4. CHEQUE"
        );

        String paymentMode =
                scanner.nextLine();

        switch (paymentMode) {
            case "1":
                paymentMode = "BANK_TRANSFER";
                break;
            case "2":
                paymentMode = "CASH";
                break;
            case "3":
                paymentMode = "UPI";
                break;
            case "4":
                paymentMode = "CHEQUE";
                break;
            default:
                paymentMode = paymentMode.trim().toUpperCase();
        }

        System.out.println("Enter Transaction/Reference Number:");
        String transactionRef = scanner.nextLine();
        System.out.println("Enter Reimbursement Date (YYYY-MM-DD):");
        LocalDate reimbursementDate = LocalDate.parse(scanner.nextLine());

        boolean result =
                processPayment(
                        claimId,
                        financeExecutiveId,
                        reimbursedAmount,
                        paymentMode,
                        transactionRef,
                        reimbursementDate
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

        int financeExecutiveId = resolveFinanceExecutiveEmployeeId();

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

    private void displayFinanceDashboard() {
        int financeExecutiveId = resolveFinanceExecutiveEmployeeId();
        List<ExpenseClaim> approvedClaims =
                getPendingClaimsForFinanceExecutive(financeExecutiveId);
        List<Reimbursement> reimbursements =
                getReimbursementHistory(financeExecutiveId);
        double amountAwaitingPayment = approvedClaims.stream()
                .mapToDouble(ExpenseClaim::getClaimAmount)
                .sum();
        double amountReimbursed = reimbursements.stream()
                .filter(item -> "PROCESSED".equalsIgnoreCase(item.getStatus()))
                .mapToDouble(Reimbursement::getReimbursedAmount)
                .sum();
        long claimsProcessed = reimbursements.stream()
                .filter(item -> "PROCESSED".equalsIgnoreCase(item.getStatus()))
                .count();

        System.out.println("========== FINANCE DASHBOARD ==========");
        System.out.println("Approved claims awaiting reimbursement: " + approvedClaims.size());
        System.out.println("Claims processed: " + claimsProcessed);
        System.out.println("Total amount to reimburse: " + amountAwaitingPayment);
        System.out.println("Total amount reimbursed: " + amountReimbursed);
    }

    // ==========================================
    // SERVICE DELEGATION METHODS
    // ==========================================

    private int resolveFinanceExecutiveEmployeeId() {
        if (currentFinanceExecutiveEmployeeId != null && currentFinanceExecutiveEmployeeId > 0) {
            return currentFinanceExecutiveEmployeeId;
        }

        System.out.println("Enter Finance Executive Employee ID:");
        return Integer.parseInt(scanner.nextLine());
    }

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

    public List<ExpenseClaim> getPendingClaimsForFinanceExecutive(int financeExecutiveEmployeeId) {
        return financeExecutiveService.getPendingClaimsForFinanceExecutive(financeExecutiveEmployeeId);
    }

    public ExpenseClaim getClaimById(
            int claimId) {

        return financeExecutiveService
                .getClaimById(
                        claimId
                );
    }

    public ExpenseClaim getApprovedClaimByIdForFinanceExecutive(int claimId, int financeExecutiveId) {
        return financeExecutiveService.getApprovedClaimByIdForFinanceExecutive(claimId, financeExecutiveId);
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

    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            double reimbursedAmount,
            String paymentMode,
            String transactionRef,
            LocalDate reimbursementDate) {
        return financeExecutiveService.processPayment(
                claimId,
                financeExecutiveId,
                reimbursedAmount,
                paymentMode,
                transactionRef,
                reimbursementDate
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
