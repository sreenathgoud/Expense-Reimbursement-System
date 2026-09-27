package com.ers.service;

import com.ers.dao.IReimbursementDao;
import com.ers.model.Reimbursement;

import java.util.List;
import java.util.logging.Logger;

public class ReimbursementServiceImpl
        implements IReimbursementService {

    private static final Logger logger =
            Logger.getLogger(
                    ReimbursementServiceImpl.class.getName()
            );

    private final IReimbursementDao reimbursementDao;

    public ReimbursementServiceImpl(
            IReimbursementDao reimbursementDao) {

        this.reimbursementDao = reimbursementDao;
    }

    @Override
    public Reimbursement addReimbursement(
            Reimbursement reimbursement) {

        // Validation
        if (reimbursement == null) {
            throw new IllegalArgumentException(
                    "Reimbursement cannot be null."
            );
        }

        if (reimbursement.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (reimbursement.getReimbursedAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Reimbursed amount must be greater than zero."
            );
        }

        if (reimbursement.getPaymentMode() == null ||
                reimbursement.getPaymentMode().isBlank()) {
            throw new IllegalArgumentException(
                    "Payment mode is required."
            );
        }

        if (reimbursement.getProcessedBy() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid finance executive ID."
            );
        }

        if (reimbursement.getStatus() == null ||
                reimbursement.getStatus().isBlank()) {
            throw new IllegalArgumentException(
                    "Reimbursement status is required."
            );
        }

        Reimbursement result =
                reimbursementDao.addReimbursement(
                        reimbursement
                );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add reimbursement."
            );
        }

        logger.info(
                "Reimbursement added successfully: ID="
                        + result.getReimbursementId()
        );

        return result;
    }

    @Override
    public boolean updateReimbursement(
            Reimbursement reimbursement) {

        if (reimbursement == null) {
            throw new IllegalArgumentException(
                    "Reimbursement cannot be null."
            );
        }

        if (reimbursement.getReimbursementId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid reimbursement ID."
            );
        }

        if (reimbursement.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (reimbursement.getReimbursedAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Reimbursed amount must be greater than zero."
            );
        }

        if (reimbursement.getPaymentMode() == null ||
                reimbursement.getPaymentMode().isBlank()) {
            throw new IllegalArgumentException(
                    "Payment mode is required."
            );
        }

        if (reimbursement.getProcessedBy() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid finance executive ID."
            );
        }

        if (reimbursement.getStatus() == null ||
                reimbursement.getStatus().isBlank()) {
            throw new IllegalArgumentException(
                    "Reimbursement status is required."
            );
        }

        boolean result =
                reimbursementDao.updateReimbursement(
                        reimbursement
                );

        if (result) {
            logger.info(
                    "Reimbursement updated successfully: ID="
                            + reimbursement.getReimbursementId()
            );
        }

        return result;
    }

    @Override
    public Reimbursement getReimbursementById(
            int reimbursementId) {

        if (reimbursementId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid reimbursement ID."
            );
        }

        Reimbursement reimbursement =
                reimbursementDao.getReimbursementById(
                        reimbursementId
                );

        if (reimbursement == null) {
            logger.warning(
                    "No reimbursement found with ID="
                            + reimbursementId
            );
        }

        return reimbursement;
    }

    @Override
    public List<Reimbursement> getAllReimbursements() {

        return reimbursementDao.getAllReimbursements();
    }

    @Override
    public boolean deleteReimbursementById(
            int reimbursementId) {

        if (reimbursementId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid reimbursement ID."
            );
        }

        boolean result =
                reimbursementDao.deleteReimbursementById(
                        reimbursementId
                );

        if (result) {
            logger.info(
                    "Reimbursement deleted successfully: ID="
                            + reimbursementId
            );
        }

        return result;
    }

    @Override
    public Reimbursement getReimbursementByClaimId(
            int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        Reimbursement reimbursement =
                reimbursementDao.getReimbursementByClaimId(
                        claimId
                );

        if (reimbursement == null) {
            logger.warning(
                    "No reimbursement found for claim ID="
                            + claimId
            );
        }

        return reimbursement;
    }

    @Override
    public List<Reimbursement> getReimbursementsByEmployeeId(
            int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        return reimbursementDao.getReimbursementsByEmployeeId(
                employeeId
        );
    }

    @Override
    public List<Reimbursement> getReimbursementsByStatus(
            String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Reimbursement status is required."
            );
        }

        return reimbursementDao.getReimbursementsByStatus(
                status
        );
    }
}