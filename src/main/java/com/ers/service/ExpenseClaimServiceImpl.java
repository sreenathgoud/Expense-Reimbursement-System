package com.ers.service;

import com.ers.dao.IExpenseClaimDao;
import com.ers.model.ExpenseClaim;

import java.util.List;

public class ExpenseClaimServiceImpl implements IExpenseClaimService {

    private IExpenseClaimDao expenseClaimDao;

    public ExpenseClaimServiceImpl(
            IExpenseClaimDao expenseClaimDao) {

        this.expenseClaimDao = expenseClaimDao;
    }

    @Override
    public ExpenseClaim addExpenseClaim(
            ExpenseClaim expenseClaim) {

        // Business validation
        if (expenseClaim == null) {
            return null;
        }

        if (expenseClaim.getEmployeeId() <= 0) {
            return null;
        }

        if (expenseClaim.getClaimAmount() <= 0) {
            return null;
        }

        if (expenseClaim.getClaimDate() == null) {
            return null;
        }

        if (expenseClaim.getStatus() == null ||
                expenseClaim.getStatus().isBlank()) {
            return null;
        }

        return expenseClaimDao.addExpenseClaim(
                expenseClaim
        );
    }

    @Override
    public boolean updateExpenseClaim(
            ExpenseClaim expenseClaim) {

        // Business validation
        if (expenseClaim == null) {
            return false;
        }

        if (expenseClaim.getClaimId() <= 0) {
            return false;
        }

        if (expenseClaim.getEmployeeId() <= 0) {
            return false;
        }

        if (expenseClaim.getClaimAmount() <= 0) {
            return false;
        }

        if (expenseClaim.getClaimDate() == null) {
            return false;
        }

        return expenseClaimDao.updateExpenseClaim(
                expenseClaim
        );
    }

    @Override
    public ExpenseClaim getExpenseClaimById(
            int claimId) {

        if (claimId <= 0) {
            return null;
        }

        return expenseClaimDao.getExpenseClaimById(
                claimId
        );
    }

    @Override
    public List<ExpenseClaim> getAllExpenseClaims() {

        return expenseClaimDao.getAllExpenseClaims();
    }

    @Override
    public boolean deleteExpenseClaimById(
            int claimId) {

        if (claimId <= 0) {
            return false;
        }

        return expenseClaimDao.deleteExpenseClaimById(
                claimId
        );
    }

    @Override
    public List<ExpenseClaim> getClaimsByEmployeeId(
            int employeeId) {

        if (employeeId <= 0) {
            return List.of();
        }

        return expenseClaimDao.getClaimsByEmployeeId(
                employeeId
        );
    }

    @Override
    public boolean submitClaim(int claimId) {

        if (claimId <= 0) {
            return false;
        }

        return expenseClaimDao.submitClaim(
                claimId
        );
    }

    @Override
    public boolean approveClaim(int claimId) {

        if (claimId <= 0) {
            return false;
        }

        return expenseClaimDao.approveClaim(
                claimId
        );
    }

    @Override
    public boolean rejectClaim(
            int claimId,
            String reason) {

        if (claimId <= 0) {
            return false;
        }

        if (reason == null || reason.isBlank()) {
            return false;
        }

        return expenseClaimDao.rejectClaim(
                claimId,
                reason
        );
    }

    @Override
    public List<ExpenseClaim> getClaimsByStatus(
            String status) {

        if (status == null || status.isBlank()) {
            return List.of();
        }

        return expenseClaimDao.getClaimsByStatus(
                status
        );
    }
}