package com.ers.controller;

import com.ers.model.Reimbursement;
import com.ers.service.IReimbursementService;

import java.util.List;

public class ReimbursementController {

    private IReimbursementService reimbursementService;

    public ReimbursementController(
            IReimbursementService reimbursementService) {

        this.reimbursementService =
                reimbursementService;
    }

    public Reimbursement addReimbursement(
            Reimbursement reimbursement) {

        return reimbursementService.addReimbursement(
                reimbursement
        );
    }

    public boolean updateReimbursement(
            Reimbursement reimbursement) {

        return reimbursementService.updateReimbursement(
                reimbursement
        );
    }

    public Reimbursement getReimbursementById(
            int reimbursementId) {

        return reimbursementService.getReimbursementById(
                reimbursementId
        );
    }

    public List<Reimbursement> getAllReimbursements() {

        return reimbursementService.getAllReimbursements();
    }

    public boolean deleteReimbursementById(
            int reimbursementId) {

        return reimbursementService.deleteReimbursementById(
                reimbursementId
        );
    }

    public Reimbursement getReimbursementByClaimId(
            int claimId) {

        return reimbursementService.getReimbursementByClaimId(
                claimId
        );
    }

    public List<Reimbursement> getReimbursementsByEmployeeId(
            int employeeId) {

        return reimbursementService.getReimbursementsByEmployeeId(
                employeeId
        );
    }

    public List<Reimbursement> getReimbursementsByStatus(
            String status) {

        return reimbursementService.getReimbursementsByStatus(
                status
        );
    }
}