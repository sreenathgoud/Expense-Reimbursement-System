package com.ers.service;

import com.ers.dao.IClaimItemDao;
import com.ers.model.ClaimItem;
import com.ers.service.IExpenseCategoryService;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class ClaimItemServiceImpl
        implements IClaimItemService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ClaimItemServiceImpl.class);

    private final IClaimItemDao claimItemDao;
    private final IExpenseCategoryService expenseCategoryService;

    public ClaimItemServiceImpl(
            IClaimItemDao claimItemDao) {

        this(claimItemDao, null);
    }

    public ClaimItemServiceImpl(
            IClaimItemDao claimItemDao,
            IExpenseCategoryService expenseCategoryService) {

        this.claimItemDao = claimItemDao;
        this.expenseCategoryService = expenseCategoryService;
    }

    @Override
    public ClaimItem addClaimItem(
            ClaimItem claimItem) {

        // Validation
        if (claimItem == null) {
            throw new IllegalArgumentException(
                    "Claim item cannot be null."
            );
        }

        if (claimItem.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (claimItem.getCategoryId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid category ID."
            );
        }

        if (expenseCategoryService != null
                && expenseCategoryService.getExpenseCategoryById(claimItem.getCategoryId()) == null) {
            throw new IllegalArgumentException("Expense category is invalid or inactive.");
        }

        if (claimItem.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Claim item amount must be greater than zero."
            );
        }

        if (claimItem.getDescription() == null || claimItem.getDescription().isBlank()) {
            throw new IllegalArgumentException("Claim item description is required.");
        }

        if (claimItem.getExpenseDate() == null) {
            throw new IllegalArgumentException(
                    "Expense date is required."
            );
        }

        if (claimItem.getExpenseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be in the future.");
        }

        ClaimItem result =
                claimItemDao.addClaimItem(
                        claimItem
                );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add claim item."
            );
        }

        logger.info(
                "Claim item added successfully: ID="
                        + result.getItemId()
        );

        return result;
    }

    @Override
    public boolean updateClaimItem(
            ClaimItem claimItem) {

        if (claimItem == null) {
            throw new IllegalArgumentException(
                    "Claim item cannot be null."
            );
        }

        if (claimItem.getItemId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid item ID."
            );
        }

        if (claimItem.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        if (claimItem.getCategoryId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid category ID."
            );
        }

        if (expenseCategoryService != null
                && expenseCategoryService.getExpenseCategoryById(claimItem.getCategoryId()) == null) {
            throw new IllegalArgumentException("Expense category is invalid or inactive.");
        }

        if (claimItem.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Claim item amount must be greater than zero."
            );
        }

        if (claimItem.getDescription() == null || claimItem.getDescription().isBlank()) {
            throw new IllegalArgumentException("Claim item description is required.");
        }

        if (claimItem.getExpenseDate() == null) {
            throw new IllegalArgumentException(
                    "Expense date is required."
            );
        }

        if (claimItem.getExpenseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be in the future.");
        }

        boolean result =
                claimItemDao.updateClaimItem(
                        claimItem
                );

        if (result) {
            logger.info(
                    "Claim item updated successfully: ID="
                            + claimItem.getItemId()
            );
        }

        return result;
    }

    @Override
    public ClaimItem getClaimItemById(
            int itemId) {

        if (itemId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid item ID."
            );
        }

        ClaimItem claimItem =
                claimItemDao.getClaimItemById(
                        itemId
                );

        if (claimItem == null) {
            logger.warn(
                    "No claim item found with ID="
                            + itemId
            );
        }

        return claimItem;
    }

    @Override
    public List<ClaimItem> getAllClaimItems() {

        return claimItemDao.getAllClaimItems();
    }

    @Override
    public boolean deleteClaimItemById(
            int itemId) {

        if (itemId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid item ID."
            );
        }

        boolean result =
                claimItemDao.deleteClaimItemById(
                        itemId
                );

        if (result) {
            logger.info(
                    "Claim item deleted successfully: ID="
                            + itemId
            );
        }

        return result;
    }

    @Override
    public List<ClaimItem> getClaimItemsByClaimId(
            int claimId) {

        if (claimId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid claim ID."
            );
        }

        return claimItemDao.getClaimItemsByClaimId(
                claimId
        );
    }
}