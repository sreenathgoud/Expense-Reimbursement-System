package com.ers.controller;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.service.IFinanceExecutiveService;

import java.util.List;

public class FinanceExecutiveController {

    private IFinanceExecutiveService financeExecutiveService;

    public FinanceExecutiveController(
            IFinanceExecutiveService financeExecutiveService) {

        this.financeExecutiveService =
                financeExecutiveService;
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