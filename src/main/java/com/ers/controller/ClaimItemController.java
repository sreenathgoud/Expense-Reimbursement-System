package com.ers.controller;

import ch.qos.logback.classic.Logger;
import com.ers.model.ClaimItem;
import com.ers.service.IClaimItemService;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ClaimItemController {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ClaimItemController.class);

    private final IClaimItemService claimItemService;
    private final Scanner scanner;

    public ClaimItemController(
            IClaimItemService claimItemService) {

        this.claimItemService = claimItemService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            logger.info("======================================");
            logger.info("          CLAIM ITEM MANAGEMENT");
            logger.info("======================================");
            logger.info("1. Add Claim Item");
            logger.info("2. Update Claim Item");
            logger.info("3. Get Claim Item By ID");
            logger.info("4. Get All Claim Items");
            logger.info("5. Delete Claim Item");
            logger.info("6. Get Claim Items By Claim ID");
            logger.info("7. Back");
            logger.info("======================================");
            logger.info("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        addClaimItemFromInput();
                        break;

                    case "2":
                        updateClaimItemFromInput();
                        break;

                    case "3":
                        getClaimItemByIdFromInput();
                        break;

                    case "4":
                        displayAllClaimItems();
                        break;

                    case "5":
                        deleteClaimItemFromInput();
                        break;

                    case "6":
                        getClaimItemsByClaimIdFromInput();
                        break;

                    case "7":
                        running = false;
                        logger.info("Returning to main menu.");
                        break;

                    default:
                        logger.warn("Invalid menu choice: {}", choice);
                }

            } catch (IllegalArgumentException e) {

                logger.warn(
                        "Invalid claim item input: {}",
                        e.getMessage()
                );

            } catch (Exception e) {

                logger.error(
                        "Unexpected error in ClaimItemController.",
                        e
                );
            }
        }
    }

    private void addClaimItemFromInput() {

        logger.info("========== ADD CLAIM ITEM ==========");

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Description:");
        String description = scanner.nextLine();

        logger.info("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Expense Date (YYYY-MM-DD):");
        LocalDate expenseDate =
                LocalDate.parse(scanner.nextLine());

        ClaimItem claimItem =
                new ClaimItem(
                        claimId,
                        categoryId,
                        description,
                        amount,
                        expenseDate
                );

        ClaimItem result =
                addClaimItem(claimItem);

        if (result != null) {

            logger.info(
                    "Claim item added successfully. Item ID={}",
                    result.getItemId()
            );

        } else {

            logger.warn("Failed to add claim item.");
        }
    }

    private void updateClaimItemFromInput() {

        logger.info("========== UPDATE CLAIM ITEM ==========");

        logger.info("Enter Item ID:");
        int itemId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        logger.info("Enter Description:");
        String description = scanner.nextLine();

        logger.info("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        logger.info("Enter Expense Date (YYYY-MM-DD):");
        LocalDate expenseDate =
                LocalDate.parse(scanner.nextLine());

        ClaimItem claimItem =
                new ClaimItem(
                        claimId,
                        categoryId,
                        description,
                        amount,
                        expenseDate
                );

        claimItem.setItemId(itemId);

        boolean result =
                updateClaimItem(claimItem);

        if (result) {

            logger.info(
                    "Claim item updated successfully. Item ID={}",
                    itemId
            );

        } else {

            logger.warn(
                    "Claim item update failed. Item ID={}",
                    itemId
            );
        }
    }

    private void getClaimItemByIdFromInput() {

        logger.info(
                "========== GET CLAIM ITEM BY ID =========="
        );

        logger.info("Enter Item ID:");
        int itemId =
                Integer.parseInt(scanner.nextLine());

        ClaimItem claimItem =
                getClaimItemById(itemId);

        if (claimItem != null) {

            logger.info("Claim item found:");
            logger.info("{}", claimItem);

        } else {

            logger.warn(
                    "No claim item found with ID={}",
                    itemId
            );
        }
    }

    private void displayAllClaimItems() {

        logger.info("========== ALL CLAIM ITEMS ==========");

        List<ClaimItem> claimItems =
                getAllClaimItems();

        if (claimItems.isEmpty()) {

            logger.info("No claim items found.");

        } else {

            logger.info(
                    "Total claim items found: {}",
                    claimItems.size()
            );

            for (ClaimItem claimItem : claimItems) {
                logger.info("{}", claimItem);
            }
        }
    }

    private void deleteClaimItemFromInput() {

        logger.info("========== DELETE CLAIM ITEM ==========");

        logger.info("Enter Item ID:");
        int itemId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteClaimItemById(itemId);

        if (result) {

            logger.info(
                    "Claim item deleted successfully. Item ID={}",
                    itemId
            );

        } else {

            logger.warn(
                    "Claim item deletion failed. Item ID={}",
                    itemId
            );
        }
    }

    private void getClaimItemsByClaimIdFromInput() {

        logger.info(
                "========== CLAIM ITEMS BY CLAIM ID =========="
        );

        logger.info("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        List<ClaimItem> claimItems =
                getClaimItemsByClaimId(claimId);

        if (claimItems.isEmpty()) {

            logger.info(
                    "No claim items found for Claim ID={}",
                    claimId
            );

        } else {

            logger.info(
                    "Claim items for Claim ID={}:",
                    claimId
            );

            for (ClaimItem claimItem : claimItems) {
                logger.info("{}", claimItem);
            }
        }
    }

    // Service delegation methods

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