package com.ers.service;

import com.ers.dao.IExpenseClaimDao;
import com.ers.model.ExpenseClaim;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExpenseClaimServiceImpl
        implements IExpenseClaimService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ExpenseClaimServiceImpl.class);

    private final IExpenseClaimDao expenseClaimDao;

    public ExpenseClaimServiceImpl(
            IExpenseClaimDao expenseClaimDao) {

        this.expenseClaimDao = expenseClaimDao;
    }

    @Override
    public ExpenseClaim addExpenseClaim(
            ExpenseClaim expenseClaim) {

        // Validation
        if (expenseClaim == null) {
            throw new IllegalArgumentException(
                    "Expense claim cannot be null."
            );
        }

        if (expenseClaim.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        if (expenseClaim.getClaimDesc() == null || expenseClaim.getClaimDesc().isBlank()) {
            throw new IllegalArgumentException("Claim description is required.");
        }

        if (expenseClaim.getClaimAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Claim amount must be greater than zero."
            );
        }

        if (expenseClaim.getClaimDate() == null) {
            throw new IllegalArgumentException(
                    "Claim date is required."
            );
        }

        if (expenseClaim.getClaimDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Claim date cannot be in the future.");
        }

        if (expenseClaim.getStatus() == null ||
                expenseClaim.getStatus().isBlank()) {
            throw new IllegalArgumentException(
                    "Claim status is required."
            );
        }

        ExpenseClaim result =
                expenseClaimDao.addExpenseClaim(
                        expenseClaim
                );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add expense claim."
            );
        }

        logger.info(
                "Expense claim added successfully: "
                        + result.getClaimId()
        );

        return result;
    }

    @Override
    public boolean updateExpenseClaim(
            ExpenseClaim expenseClaim) {

        if (expenseClaim == null) {
            throw new IllegalArgumentException(
                    "Expense claim cannot be null."
            );
        }

        if (expenseClaim.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (expenseClaim.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        if (expenseClaim.getClaimDesc() == null || expenseClaim.getClaimDesc().isBlank()) {
            throw new IllegalArgumentException("Claim description is required.");
        }

        if (expenseClaim.getClaimAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Claim amount must be greater than zero."
            );
        }

        if (expenseClaim.getClaimDate() == null) {
            throw new IllegalArgumentException(
                    "Claim date is required."
            );
        }

        if (expenseClaim.getClaimDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Claim date cannot be in the future.");
        }

        boolean result =
                expenseClaimDao.updateExpenseClaim(
                        expenseClaim
                );

        if (result) {
            logger.info(
                    "Expense claim updated successfully: ID="
                            + expenseClaim.getClaimId()
            );
        }

        return result;
    }

    @Override
    public boolean updateDraftClaimForEmployee(ExpenseClaim expenseClaim, int employeeId) {
        if (expenseClaim == null) {
            throw new IllegalArgumentException("Expense claim cannot be null.");
        }
        if (expenseClaim.getClaimId() <= 0 || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid claim or employee ID.");
        }
        if (expenseClaim.getClaimAmount() <= 0) {
            throw new IllegalArgumentException("Claim amount must be greater than zero.");
        }
        if (expenseClaim.getClaimDate() == null) {
            throw new IllegalArgumentException("Claim date is required.");
        }
        return expenseClaimDao.updateDraftClaimForEmployee(expenseClaim, employeeId);
    }

    @Override
    public ExpenseClaim getExpenseClaimById(
            int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        ExpenseClaim claim =
                expenseClaimDao.getExpenseClaimById(
                        claimId
                );

        if (claim == null) {
            logger.warn(
                    "No expense claim found with ID="
                            + claimId
            );
        }

        return claim;
    }

    @Override
    public List<ExpenseClaim> getAllExpenseClaims() {

        return expenseClaimDao.getAllExpenseClaims();
    }

    @Override
    public boolean deleteExpenseClaimById(
            int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        boolean result =
                expenseClaimDao.deleteExpenseClaimById(
                        claimId
                );

        if (result) {
            logger.info(
                    "Expense claim deleted successfully: ID="
                            + claimId
            );
        }

        return result;
    }

    @Override
    public boolean deleteDraftClaimForEmployee(int claimId, int employeeId) {
        if (claimId <= 0 || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid claim or employee ID.");
        }
        return expenseClaimDao.deleteDraftClaimForEmployee(claimId, employeeId);
    }

    @Override
    public List<ExpenseClaim> getClaimsByEmployeeId(
            int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        return expenseClaimDao.getClaimsByEmployeeId(
                employeeId
        );
    }

    @Override
    public List<ExpenseClaim> getClaimsForManager(int managerId) {
        if (managerId <= 0) {
            throw new IllegalArgumentException("Invalid manager ID.");
        }
        return expenseClaimDao.getClaimsForManager(managerId);
    }

    @Override
    public boolean submitClaim(int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        boolean result =
                expenseClaimDao.submitClaim(
                        claimId
                );

        if (result) {
            logger.info(
                    "Expense claim submitted successfully: ID="
                            + claimId
            );
        }

        return result;
    }

    @Override
    public boolean approveClaim(int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        boolean result =
                expenseClaimDao.approveClaim(
                        claimId, "APPROVED"
                );

        if (result) {
            logger.info(
                    "Expense claim approved successfully: ID="
                            + claimId
            );
        }

        return result;
    }

    @Override
    public ExpenseClaim getExpenseClaimWithItemsForManager(int claimId, int managerId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException("Invalid claim ID.");
        }

        if (managerId <= 0) {
            throw new IllegalArgumentException("Invalid manager ID.");
        }

        ExpenseClaim claim =
                expenseClaimDao.getExpenseClaimWithItemsForManager(claimId, managerId);

        if (claim == null) {
            logger.warn("No claim found for manager review. Claim ID=" + claimId + ", Manager ID=" + managerId);
        }

        return claim;
    }

    @Override
    public boolean approveClaimForManager(int claimId, int managerId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException("Invalid claim ID.");
        }

        if (managerId <= 0) {
            throw new IllegalArgumentException("Invalid manager ID.");
        }

        boolean result = expenseClaimDao.approveClaimForManager(claimId, managerId);

        if (result) {
            logger.info("Manager approved claim successfully: ID=" + claimId + ", Manager ID=" + managerId);
        }

        return result;
    }

    @Override
    public boolean approveClaimForManager(int claimId, int managerId, String remarks) {
        if (claimId <= 0 || managerId <= 0) {
            throw new IllegalArgumentException("Invalid claim or manager ID.");
        }
        if (remarks == null || remarks.isBlank()) {
            throw new IllegalArgumentException("Approval remarks are required.");
        }
        return expenseClaimDao.approveClaimForManager(claimId, managerId, remarks.trim());
    }

    @Override
    public boolean rejectClaim(
            int claimId,
            String reason) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException(
                    "Rejection reason is required."
            );
        }

        boolean result =
                expenseClaimDao.rejectClaim(
                        claimId,
                        reason
                );

        if (result) {
            logger.info(
                    "Expense claim rejected: ID="
                            + claimId
            );
        }

        return result;
    }

    @Override
    public boolean rejectClaimForManager(int claimId, int managerId, String reason) {
        if (claimId <= 0 || managerId <= 0) {
            throw new IllegalArgumentException("Invalid claim or manager ID.");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Rejection remarks are required.");
        }
        return expenseClaimDao.rejectClaimForManager(claimId, managerId, reason.trim());
    }

    @Override
    public List<ExpenseClaim> getClaimsByStatus(
            String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Claim status is required."
            );
        }

        return expenseClaimDao.getClaimsByStatus(
                status
        );
    }
}