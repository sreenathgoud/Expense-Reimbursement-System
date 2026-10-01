package com.ers.controller;

import com.ers.model.ClaimItem;
import com.ers.model.Employee;
import com.ers.model.ExpenseCategory;
import com.ers.model.ExpenseClaim;
import com.ers.service.IClaimItemService;
import com.ers.service.IEmployeeService;
import com.ers.service.IExpenseCategoryService;
import com.ers.service.IExpenseClaimService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseClaimController {

    private final IExpenseClaimService expenseClaimService;
    private final IClaimItemService claimItemService;
    private final IEmployeeService employeeService;
    private final IExpenseCategoryService expenseCategoryService;
    private final Scanner scanner;
    private final Integer currentManagerEmployeeId;

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService) {

        this(expenseClaimService, null, null);
    }

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService,
            IClaimItemService claimItemService) {

        this(expenseClaimService, claimItemService, null);
    }

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService,
            IClaimItemService claimItemService,
            Integer currentManagerEmployeeId) {

        this(expenseClaimService, claimItemService, null, null, currentManagerEmployeeId);
    }

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService,
            IClaimItemService claimItemService,
            IEmployeeService employeeService,
            IExpenseCategoryService expenseCategoryService,
            Integer currentManagerEmployeeId) {

        this.expenseClaimService = expenseClaimService;
        this.claimItemService = claimItemService;
        this.employeeService = employeeService;
        this.expenseCategoryService = expenseCategoryService;
        this.scanner = new Scanner(System.in);
        this.currentManagerEmployeeId = currentManagerEmployeeId;
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
            System.out.println("1. Review Claim + Items");
            System.out.println("2. Get All Expense Claims");
            System.out.println("3. Get Claims By Employee ID");
            System.out.println("4. Approve Claim");
            System.out.println("5. Reject Claim");
            System.out.println("6. Get Claims By Status");
            System.out.println("7. Manager Dashboard");
            System.out.println("8. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        reviewClaimForManagerFromInput();
                        break;

                    case "2":
                        displayManagerExpenseClaims();
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
                        displayManagerDashboard();
                        break;

                    case "8":
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
            System.out.println("7. Get Claims By Status");
            System.out.println("8. Back");
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
                        getClaimsByStatusFromInput();
                        break;

                    case "8":
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

    private void reviewClaimForManagerFromInput() {

        System.out.println("========== MANAGER CLAIM REVIEW ==========");
        int managerId = resolveManagerEmployeeId();
        System.out.println("Enter Claim ID:");
        int claimId = Integer.parseInt(scanner.nextLine());

        ExpenseClaim expenseClaim =
                getExpenseClaimWithItemsForManager(claimId, managerId);

        if (expenseClaim == null) {
            System.out.println("Claim not found for this manager. Claim ID=" + claimId);
            return;
        }

        System.out.println("Claim ID       : " + expenseClaim.getClaimId());
        System.out.println("Employee ID    : " + expenseClaim.getEmployeeId());
        if (employeeService != null) {
            Employee employee = employeeService.getEmployeeById(expenseClaim.getEmployeeId());
            if (employee != null) {
                System.out.println("Employee Name  : " + employee.getFullName());
                System.out.println("Employee Email : " + employee.getEmail());
                System.out.println("Department ID  : " + employee.getDepartmentId());
            }
        }
        System.out.println("Description    : " + expenseClaim.getClaimDesc());
        System.out.println("Claim Amount   : " + expenseClaim.getClaimAmount());
        System.out.println("Claim Date     : " + expenseClaim.getClaimDate());
        System.out.println("Status         : " + expenseClaim.getStatus());
        System.out.println("Review Remarks : " + expenseClaim.getReviewRemarks());
        System.out.println();
        System.out.println("Claim Items");
        System.out.println("--------------------------------");

        List<ClaimItem> items = getClaimItemsByClaimId(claimId);
        double totalItemsAmount = 0;

        if (items.isEmpty()) {
            System.out.println("No claim items found for this claim.");
        } else {
            for (ClaimItem item : items) {
                System.out.println("Item ID        : " + item.getItemId());
                System.out.println("Category ID    : " + item.getCategoryId());
                if (expenseCategoryService != null) {
                    ExpenseCategory category = expenseCategoryService.getExpenseCategoryById(item.getCategoryId());
                    if (category != null) {
                        System.out.println("Category Name  : " + category.getCategory_name());
                    }
                }
                System.out.println("Description    : " + item.getDescription());
                System.out.println("Amount         : " + item.getAmount());
                totalItemsAmount += item.getAmount();
                System.out.println("--------------------------------");
            }
        }

        System.out.println("Total Items Amount: " + totalItemsAmount);
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.println("3. Back");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.println("Enter approval remarks:");
                String approvalRemarks = scanner.nextLine();
                boolean approved = expenseClaimService.approveClaimForManager(
                        claimId, managerId, approvalRemarks
                );
                System.out.println(approved ? "Claim approved successfully." : "Claim approval failed.");
                break;
            case "2":
                System.out.println("Enter rejection reason:");
                String reason = scanner.nextLine();
                boolean rejected = expenseClaimService.rejectClaimForManager(
                        claimId, managerId, reason
                );
                System.out.println(rejected ? "Claim rejected successfully." : "Claim rejection failed.");
                break;
            default:
                System.out.println("Returning to manager menu.");
        }
    }

    // ==========================================
    // GET ALL EXPENSE CLAIMS
    // ==========================================

    private void displayAllExpenseClaims() {
        System.out.println("========== ALL EXPENSE CLAIMS ==========");
        List<ExpenseClaim> claims = getAllExpenseClaims();
        if (claims.isEmpty()) {
            System.out.println("No expense claims found.");
            return;
        }
        System.out.println("Total expense claims found: " + claims.size());
        claims.forEach(System.out::println);
    }

    private void displayManagerExpenseClaims() {
        List<ExpenseClaim> claims = expenseClaimService.getClaimsForManager(resolveManagerEmployeeId());
        if (claims.isEmpty()) {
            System.out.println("No claims found for your department.");
            return;
        }
        claims.forEach(System.out::println);
    }

    private void displayManagerDashboard() {
        List<ExpenseClaim> claims = expenseClaimService.getClaimsForManager(resolveManagerEmployeeId());
        long submitted = claims.stream()
                .filter(claim -> "SUBMITTED".equalsIgnoreCase(claim.getStatus()))
                .count();
        long approved = claims.stream()
                .filter(claim -> "APPROVED".equalsIgnoreCase(claim.getStatus()))
                .count();
        long rejected = claims.stream()
                .filter(claim -> "REJECTED".equalsIgnoreCase(claim.getStatus()))
                .count();
        double approvedAmount = claims.stream()
                .filter(claim -> "APPROVED".equalsIgnoreCase(claim.getStatus()))
                .mapToDouble(ExpenseClaim::getClaimAmount)
                .sum();

        System.out.println("========== MANAGER DASHBOARD ==========");
        System.out.println("Total claims: " + claims.size());
        System.out.println("Submitted/pending: " + submitted);
        System.out.println("Approved: " + approved);
        System.out.println("Rejected: " + rejected);
        System.out.println("Total approved amount: " + approvedAmount);
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

        List<ExpenseClaim> claims = currentManagerEmployeeId == null
                ? getClaimsByEmployeeId(employeeId)
                : expenseClaimService.getClaimsForManager(currentManagerEmployeeId).stream()
                .filter(claim -> claim.getEmployeeId() == employeeId)
                .toList();

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

        int managerId = resolveManagerEmployeeId();
        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.println("Enter approval remarks:");
        String remarks = scanner.nextLine();
        boolean result = expenseClaimService.approveClaimForManager(
                claimId, managerId, remarks
        );

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

        boolean result = currentManagerEmployeeId == null
                ? rejectClaim(claimId, reason)
                : expenseClaimService.rejectClaimForManager(
                claimId, currentManagerEmployeeId, reason
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

        List<ExpenseClaim> claims = currentManagerEmployeeId == null
                ? getClaimsByStatus(status)
                : expenseClaimService.getClaimsForManager(currentManagerEmployeeId).stream()
                .filter(claim -> status.equalsIgnoreCase(claim.getStatus()))
                .toList();

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

    private int resolveManagerEmployeeId() {
        if (currentManagerEmployeeId != null && currentManagerEmployeeId > 0) {
            return currentManagerEmployeeId;
        }

        System.out.println("Enter Manager Employee ID:");
        return Integer.parseInt(scanner.nextLine());
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

    public ExpenseClaim getExpenseClaimWithItemsForManager(int claimId, int managerId) {
        return expenseClaimService.getExpenseClaimWithItemsForManager(claimId, managerId);
    }

    public boolean approveClaimForManager(int claimId, int managerId) {
        return expenseClaimService.approveClaimForManager(claimId, managerId);
    }

    public List<ClaimItem> getClaimItemsByClaimId(int claimId) {
        if (claimItemService == null) {
            return List.of();
        }
        return claimItemService.getClaimItemsByClaimId(claimId);
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