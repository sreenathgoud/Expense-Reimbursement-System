package com.ers.dao;

import com.ers.model.ExpenseClaim;

import java.util.List;

public interface IExpenseClaimDao {
    ExpenseClaim addExpenseClaim(ExpenseClaim expenseClaim);
    boolean updateExpenseClaim(ExpenseClaim expenseClaim);
    boolean updateDraftClaimForEmployee(ExpenseClaim expenseClaim, int employeeId);
    ExpenseClaim getExpenseClaimById(int claimId);
    List<ExpenseClaim> getAllExpenseClaims();
    boolean deleteExpenseClaimById(int claimId);
    boolean deleteDraftClaimForEmployee(int claimId, int employeeId);
    List<ExpenseClaim> getClaimsByEmployeeId(int employeeId);
    List<ExpenseClaim> getClaimsForManager(int managerId);
    boolean submitClaim(int claimId);
    boolean approveClaim(int claimId, String approved);
    ExpenseClaim getExpenseClaimWithItemsForManager(int claimId, int managerId);
    boolean approveClaimForManager(int claimId, int managerId);
    boolean approveClaimForManager(int claimId, int managerId, String remarks);
    boolean rejectClaim(int claimId, String reason);
    boolean rejectClaimForManager(int claimId, int managerId, String reason);
    List<ExpenseClaim> getClaimsByStatus(String status);
}
