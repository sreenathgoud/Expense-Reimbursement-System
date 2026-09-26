package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.service.IExpenseClaimService;

import java.util.List;

public class ExpenseClaimController {

    private IExpenseClaimService expenseClaimService;

    public ExpenseClaimController(
            IExpenseClaimService expenseClaimService) {

        this.expenseClaimService = expenseClaimService;
    }

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