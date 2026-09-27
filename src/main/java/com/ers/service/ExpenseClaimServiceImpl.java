package com.ers.service;

import com.ers.dao.IExpenseClaimDao;
import com.ers.model.ExpenseClaim;

import java.util.List;
import java.util.logging.Logger;

public class ExpenseClaimServiceImpl
        implements IExpenseClaimService {

    private static final Logger logger =
            Logger.getLogger(
                    ExpenseClaimServiceImpl.class.getName()
            );

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
            logger.warning(
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
                        claimId
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