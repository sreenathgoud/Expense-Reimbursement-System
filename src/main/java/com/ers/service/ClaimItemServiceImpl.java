package com.ers.service;

import com.ers.dao.IClaimItemDao;
import com.ers.model.ClaimItem;

import java.util.List;

public class ClaimItemServiceImpl
        implements IClaimItemService {

    private IClaimItemDao claimItemDao;

    public ClaimItemServiceImpl(
            IClaimItemDao claimItemDao) {

        this.claimItemDao = claimItemDao;
    }

    @Override
    public ClaimItem addClaimItem(
            ClaimItem claimItem) {

        if (claimItem == null) {
            return null;
        }

        if (claimItem.getClaimId() <= 0) {
            return null;
        }

        if (claimItem.getCategoryId() <= 0) {
            return null;
        }

        if (claimItem.getAmount() <= 0) {
            return null;
        }

        if (claimItem.getExpenseDate() == null) {
            return null;
        }

        return claimItemDao.addClaimItem(
                claimItem
        );
    }


    @Override
    public boolean updateClaimItem(
            ClaimItem claimItem) {

        if (claimItem == null) {
            return false;
        }

        if (claimItem.getItemId() <= 0) {
            return false;
        }

        if (claimItem.getClaimId() <= 0) {
            return false;
        }

        if (claimItem.getCategoryId() <= 0) {
            return false;
        }

        if (claimItem.getAmount() <= 0) {
            return false;
        }

        if (claimItem.getExpenseDate() == null) {
            return false;
        }

        return claimItemDao.updateClaimItem(
                claimItem
        );
    }


    @Override
    public ClaimItem getClaimItemById(
            int itemId) {

        if (itemId <= 0) {
            return null;
        }

        return claimItemDao.getClaimItemById(
                itemId
        );
    }


    @Override
    public List<ClaimItem> getAllClaimItems() {

        return claimItemDao.getAllClaimItems();
    }


    @Override
    public boolean deleteClaimItemById(
            int itemId) {

        if (itemId <= 0) {
            return false;
        }

        return claimItemDao.deleteClaimItemById(
                itemId
        );
    }


    @Override
    public List<ClaimItem> getClaimItemsByClaimId(
            int claimId) {

        if (claimId <= 0) {
            return List.of();
        }

        return claimItemDao.getClaimItemsByClaimId(
                claimId
        );
    }
}