package com.ers.controller;

import com.ers.model.ClaimItem;
import com.ers.service.IClaimItemService;

import java.util.List;

public class ClaimItemController {

    private IClaimItemService claimItemService;

    public ClaimItemController(
            IClaimItemService claimItemService) {

        this.claimItemService = claimItemService;
    }

    public ClaimItem addClaimItem(
            ClaimItem claimItem) {

        return claimItemService.addClaimItem(
                claimItem
        );
    }

    public boolean updateClaimItem(
            ClaimItem claimItem) {

        return claimItemService.updateClaimItem(
                claimItem
        );
    }

    public ClaimItem getClaimItemById(
            int itemId) {

        return claimItemService.getClaimItemById(
                itemId
        );
    }

    public List<ClaimItem> getAllClaimItems() {

        return claimItemService.getAllClaimItems();
    }

    public boolean deleteClaimItemById(
            int itemId) {

        return claimItemService.deleteClaimItemById(
                itemId
        );
    }

    public List<ClaimItem> getClaimItemsByClaimId(
            int claimId) {

        return claimItemService.getClaimItemsByClaimId(
                claimId
        );
    }
}