package com.ers.service;

import com.ers.dao.IFinanceExecutiveDao;
import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;

import java.util.List;

// Business Logic

public class FinanceExecutiveServiceImpl
        implements IFinanceExecutiveService {

    private IFinanceExecutiveDao financeExecutiveDao;

    public FinanceExecutiveServiceImpl(
            IFinanceExecutiveDao financeExecutiveDao) {

        this.financeExecutiveDao = financeExecutiveDao;
    }

    @Override
    public FinanceExecutive addFinanceExecutive(
            FinanceExecutive financeExecutive) {

        if (financeExecutive == null) {
            return null;
        }

        if (financeExecutive.getEmployeeId() <= 0) {
            return null;
        }

        if (financeExecutive.getFullName() == null ||
                financeExecutive.getFullName().isBlank()) {
            return null;
        }

        if (financeExecutive.getEmail() == null ||
                financeExecutive.getEmail().isBlank()) {
            return null;
        }

        if (!financeExecutive.getEmail().contains("@")) {
            return null;
        }

        if (financeExecutive.getDepartment() == null ||
                financeExecutive.getDepartment().isBlank()) {
            return null;
        }

        return financeExecutiveDao.addFinanceExecutive(
                financeExecutive
        );
    }

    @Override
    public boolean updateFinanceExecutive(
            FinanceExecutive financeExecutive) {

        if (financeExecutive == null) {
            return false;
        }

        if (financeExecutive.getEmployeeId() <= 0) {
            return false;
        }

        if (financeExecutive.getFullName() == null ||
                financeExecutive.getFullName().isBlank()) {
            return false;
        }

        if (financeExecutive.getEmail() == null ||
                financeExecutive.getEmail().isBlank()) {
            return false;
        }

        if (!financeExecutive.getEmail().contains("@")) {
            return false;
        }

        if (financeExecutive.getDepartment() == null ||
                financeExecutive.getDepartment().isBlank()) {
            return false;
        }

        return financeExecutiveDao.updateFinanceExecutive(
                financeExecutive
        );
    }

    @Override
    public FinanceExecutive getFinanceExecutiveById(
            int employeeId) {

        if (employeeId <= 0) {
            return null;
        }

        return financeExecutiveDao.getFinanceExecutiveById(
                employeeId
        );
    }

    @Override
    public List<FinanceExecutive> getAllFinanceExecutives() {

        return financeExecutiveDao.getAllFinanceExecutives();
    }

    @Override
    public boolean deleteFinanceExecutiveById(
            int employeeId) {

        if (employeeId <= 0) {
            return false;
        }

        return financeExecutiveDao.deleteFinanceExecutiveById(
                employeeId
        );
    }

    @Override
    public List<ExpenseClaim> getPendingClaims() {

        return financeExecutiveDao.getPendingClaims();
    }

    @Override
    public ExpenseClaim getClaimById(int claimId) {

        if (claimId <= 0) {
            return null;
        }

        return financeExecutiveDao.getClaimById(claimId);
    }

    @Override
    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            String paymentMode) {

        if (claimId <= 0) {
            return false;
        }

        if (financeExecutiveId <= 0) {
            return false;
        }

        if (paymentMode == null ||
                paymentMode.isBlank()) {
            return false;
        }

        return financeExecutiveDao.processPayment(
                claimId,
                financeExecutiveId,
                paymentMode
        );
    }

    @Override
    public List<Reimbursement> getReimbursementHistory(
            int financeExecutiveId) {

        if (financeExecutiveId <= 0) {
            return List.of();
        }

        return financeExecutiveDao.getReimbursementHistory(
                financeExecutiveId
        );
    }
}