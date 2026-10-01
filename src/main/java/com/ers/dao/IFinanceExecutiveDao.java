package com.ers.dao;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;

import java.time.LocalDate;
import java.util.List;

public interface IFinanceExecutiveDao {
    //CRUD Operations
    FinanceExecutive addFinanceExecutive(FinanceExecutive financeExecutive);
    boolean updateFinanceExecutive(FinanceExecutive financeExecutive);
    FinanceExecutive getFinanceExecutiveById(int employeeId);
    List<FinanceExecutive> getAllFinanceExecutives();
    boolean deleteFinanceExecutiveById(int employeeId);
    //Expense Claim operations
    List<ExpenseClaim> getPendingClaims();
    List<ExpenseClaim> getPendingClaimsForFinanceExecutive(int financeExecutiveEmployeeId);
    ExpenseClaim getClaimById(int claimId);
    ExpenseClaim getApprovedClaimByIdForFinanceExecutive(int claimId, int financeExecutiveEmployeeId);
    //Reimbursement operations
    boolean processPayment(int claimId, int financeExecutiveId, String paymentMode);
    boolean processPayment(int claimId, int financeExecutiveId, double reimbursedAmount, String paymentMode, String transactionRef, LocalDate reimbursementDate);
    List<Reimbursement> getReimbursementHistory(int financeExecutiveId);
}
