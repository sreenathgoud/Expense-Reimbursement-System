package com.ers.service;

import com.ers.dao.IFinanceExecutiveDao;
import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

// Business Logic
public class FinanceExecutiveServiceImpl
        implements IFinanceExecutiveService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(FinanceExecutiveServiceImpl.class);

    private final IFinanceExecutiveDao financeExecutiveDao;

    public FinanceExecutiveServiceImpl(
            IFinanceExecutiveDao financeExecutiveDao) {

        this.financeExecutiveDao = financeExecutiveDao;
    }

    @Override
    public FinanceExecutive addFinanceExecutive(
            FinanceExecutive financeExecutive) {

        // Validation
        if (financeExecutive == null) {
            throw new IllegalArgumentException(
                    "Finance executive cannot be null."
            );
        }

        if (financeExecutive.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        if (financeExecutive.getFullName() == null ||
                financeExecutive.getFullName().isBlank()) {
            throw new IllegalArgumentException(
                    "Full name is required."
            );
        }

        if (financeExecutive.getEmail() == null ||
                financeExecutive.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required."
            );
        }

        if (!financeExecutive.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "Invalid email address."
            );
        }

        if (financeExecutive.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        FinanceExecutive result =
                financeExecutiveDao.addFinanceExecutive(
                        financeExecutive
                );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add finance executive."
            );
        }

        logger.info(
                "Finance executive added successfully: Employee ID="
                        + financeExecutive.getEmployeeId()
        );

        return result;
    }

    @Override
    public boolean updateFinanceExecutive(
            FinanceExecutive financeExecutive) {

        if (financeExecutive == null) {
            throw new IllegalArgumentException(
                    "Finance executive cannot be null."
            );
        }

        if (financeExecutive.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        if (financeExecutive.getFullName() == null ||
                financeExecutive.getFullName().isBlank()) {
            throw new IllegalArgumentException(
                    "Full name is required."
            );
        }

        if (financeExecutive.getEmail() == null ||
                financeExecutive.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required."
            );
        }

        if (!financeExecutive.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "Invalid email address."
            );
        }

        if (financeExecutive.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid department ID."
            );
        }

        boolean result =
                financeExecutiveDao.updateFinanceExecutive(
                        financeExecutive
                );

        if (result) {
            logger.info(
                    "Finance executive updated successfully: Employee ID="
                            + financeExecutive.getEmployeeId()
            );
        }

        return result;
    }

    @Override
    public FinanceExecutive getFinanceExecutiveById(
            int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        FinanceExecutive result =
                financeExecutiveDao.getFinanceExecutiveById(
                        employeeId
                );

        if (result == null) {
            logger.warn(
                    "No finance executive found with Employee ID="
                            + employeeId
            );
        }

        return result;
    }

    @Override
    public List<FinanceExecutive> getAllFinanceExecutives() {

        return financeExecutiveDao.getAllFinanceExecutives();
    }

    @Override
    public boolean deleteFinanceExecutiveById(
            int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid employee ID."
            );
        }

        boolean result =
                financeExecutiveDao.deleteFinanceExecutiveById(
                        employeeId
                );

        if (result) {
            logger.info(
                    "Finance executive deleted successfully: Employee ID="
                            + employeeId
            );
        }

        return result;
    }

    @Override
    public List<ExpenseClaim> getPendingClaims() {

        return financeExecutiveDao.getPendingClaims();
    }

    @Override
    public List<ExpenseClaim> getPendingClaimsForFinanceExecutive(int financeExecutiveEmployeeId) {

        if (financeExecutiveEmployeeId <= 0) {
            throw new IllegalArgumentException("Invalid finance executive employee ID.");
        }

        return financeExecutiveDao.getPendingClaimsForFinanceExecutive(financeExecutiveEmployeeId);
    }

    @Override
    public ExpenseClaim getClaimById(int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        ExpenseClaim result =
                financeExecutiveDao.getClaimById(claimId);

        if (result == null) {
            logger.warn(
                    "No claim found with ID=" + claimId
            );
        }

        return result;
    }

    @Override
    public ExpenseClaim getApprovedClaimByIdForFinanceExecutive(
            int claimId,
            int financeExecutiveEmployeeId) {
        if (claimId <= 0 || financeExecutiveEmployeeId <= 0) {
            throw new IllegalArgumentException("Invalid claim or finance executive ID.");
        }
        return financeExecutiveDao.getApprovedClaimByIdForFinanceExecutive(
                claimId,
                financeExecutiveEmployeeId
        );
    }

    @Override
    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            String paymentMode) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (financeExecutiveId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid finance executive ID."
            );
        }

        if (paymentMode == null ||
                paymentMode.isBlank()) {
            throw new IllegalArgumentException(
                    "Payment mode is required."
            );
        }

        boolean result =
                financeExecutiveDao.processPayment(
                        claimId,
                        financeExecutiveId,
                        paymentMode
                );

        if (result) {
            logger.info(
                    "Payment processed successfully: Claim ID="
                            + claimId
            );
        }

        return result;
    }

    @Override
    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            double reimbursedAmount,
            String paymentMode,
            String transactionRef,
            LocalDate reimbursementDate) {
        if (claimId <= 0 || financeExecutiveId <= 0) {
            throw new IllegalArgumentException("Invalid claim or finance executive ID.");
        }
        if (reimbursedAmount <= 0) {
            throw new IllegalArgumentException("Reimbursed amount must be greater than zero.");
        }
        if (paymentMode == null || paymentMode.isBlank()) {
            throw new IllegalArgumentException("Payment mode is required.");
        }
        if (transactionRef == null || transactionRef.isBlank()) {
            throw new IllegalArgumentException("Transaction/reference number is required.");
        }
        if (reimbursementDate == null || reimbursementDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Reimbursement date is required and cannot be in the future.");
        }

        boolean result = financeExecutiveDao.processPayment(
                claimId,
                financeExecutiveId,
                reimbursedAmount,
                paymentMode,
                transactionRef,
                reimbursementDate
        );
        if (result) {
            logger.info("Payment processed successfully: Claim ID=" + claimId);
        }
        return result;
    }

    @Override
    public List<Reimbursement> getReimbursementHistory(
            int financeExecutiveId) {

        if (financeExecutiveId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid finance executive ID."
            );
        }

        return financeExecutiveDao.getReimbursementHistory(
                financeExecutiveId
        );
    }
}