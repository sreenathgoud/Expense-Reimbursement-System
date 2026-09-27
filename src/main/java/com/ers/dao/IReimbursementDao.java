package com.ers.dao;

import com.ers.model.Reimbursement;

import java.util.List;

public interface IReimbursementDao {

    Reimbursement addReimbursement(
            Reimbursement reimbursement
    );

    boolean updateReimbursement(
            Reimbursement reimbursement
    );

    Reimbursement getReimbursementById(
            int reimbursementId
    );

    List<Reimbursement> getAllReimbursements();

    boolean deleteReimbursementById(
            int reimbursementId
    );

    Reimbursement getReimbursementByClaimId(
            int claimId
    );

    List<Reimbursement> getReimbursementsByEmployeeId(
            int employeeId
    );

    List<Reimbursement> getReimbursementsByStatus(
            String status
    );
}