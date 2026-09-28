package com.ers.controller;

import com.ers.model.ClaimItem;
import com.ers.service.IClaimItemService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ClaimItemController {

    private final IClaimItemService claimItemService;
    private final Scanner scanner;

    public ClaimItemController(
            IClaimItemService claimItemService) {

        this.claimItemService = claimItemService;
        this.scanner = new Scanner(System.in);
    }
    public void startManager() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("          CLAIM ITEM REVIEW");
            System.out.println("======================================");
            System.out.println("1. Get Claim Item By ID");
            System.out.println("2. Get All Claim Items");
            System.out.println("3. Get Claim Items By Claim ID");
            System.out.println("4. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        getClaimItemByIdFromInput();
                        break;

                    case "2":
                        displayAllClaimItems();
                        break;

                    case "3":
                        getClaimItemsByClaimIdFromInput();
                        break;

                    case "4":
                        running = false;
                        System.out.println(
                                "Returning to main menu."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid manager claim item menu choice: "
                                        + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid claim item input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error: "
                                + e.getMessage()
                );
            }
        }
    }
    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("======================================");
            System.out.println("          CLAIM ITEM MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Claim Item");
            System.out.println("2. Update Claim Item");
            System.out.println("3. Get Claim Item By ID");
            System.out.println("4. Get All Claim Items");
            System.out.println("5. Delete Claim Item");
            System.out.println("6. Get Claim Items By Claim ID");
            System.out.println("7. Back");
            System.out.println("======================================");
            System.out.println("Enter your choice:");

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
                        System.out.println("Returning to main menu.");
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice: " + choice
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid claim item input: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "Unexpected error in ClaimItemController."
                );
                e.printStackTrace();
            }
        }
    }

    // =========================
    // ADD CLAIM ITEM
    // =========================

    private void addClaimItemFromInput() {

        System.out.println(
                "========== ADD CLAIM ITEM =========="
        );

        System.out.println("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Description:");
        String description =
                scanner.nextLine();

        System.out.println("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        System.out.println("Enter Expense Date (YYYY-MM-DD):");
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

            System.out.println(
                    "Claim item added successfully. Item ID="
                            + result.getItemId()
            );

        } else {

            System.out.println(
                    "Failed to add claim item."
            );
        }
    }

    // =========================
    // UPDATE CLAIM ITEM
    // =========================

    private void updateClaimItemFromInput() {

        System.out.println(
                "========== UPDATE CLAIM ITEM =========="
        );

        System.out.println("Enter Item ID:");
        int itemId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Claim ID:");
        int claimId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Category ID:");
        int categoryId =
                Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Description:");
        String description =
                scanner.nextLine();

        System.out.println("Enter Amount:");
        double amount =
                Double.parseDouble(scanner.nextLine());

        System.out.println("Enter Expense Date (YYYY-MM-DD):");
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

            System.out.println(
                    "Claim item updated successfully. Item ID="
                            + itemId
            );

        } else {

            System.out.println(
                    "Claim item update failed. Item ID="
                            + itemId
            );
        }
    }

    // =========================
    // GET CLAIM ITEM BY ID
    // =========================

    private void getClaimItemByIdFromInput() {

        System.out.println(
                "========== GET CLAIM ITEM BY ID =========="
        );

        System.out.println("Enter Item ID:");

        int itemId =
                Integer.parseInt(scanner.nextLine());

        ClaimItem claimItem =
                getClaimItemById(itemId);

        if (claimItem != null) {

            System.out.println("Claim item found:");
            System.out.println(claimItem);

        } else {

            System.out.println(
                    "No claim item found with ID="
                            + itemId
            );
        }
    }

    // =========================
    // GET ALL CLAIM ITEMS
    // =========================

    private void displayAllClaimItems() {

        System.out.println(
                "========== ALL CLAIM ITEMS =========="
        );

        List<ClaimItem> claimItems =
                getAllClaimItems();

        if (claimItems.isEmpty()) {

            System.out.println(
                    "No claim items found."
            );

        } else {

            System.out.println(
                    "Total claim items found: "
                            + claimItems.size()
            );

            for (ClaimItem claimItem : claimItems) {
                System.out.println(claimItem);
            }
        }
    }

    // =========================
    // DELETE CLAIM ITEM
    // =========================

    private void deleteClaimItemFromInput() {

        System.out.println(
                "========== DELETE CLAIM ITEM =========="
        );

        System.out.println("Enter Item ID:");

        int itemId =
                Integer.parseInt(scanner.nextLine());

        boolean result =
                deleteClaimItemById(itemId);

        if (result) {

            System.out.println(
                    "Claim item deleted successfully. Item ID="
                            + itemId
            );

        } else {

            System.out.println(
                    "Claim item deletion failed. Item ID="
                            + itemId
            );
        }
    }

    // =========================
    // GET CLAIM ITEMS BY CLAIM ID
    // =========================

    private void getClaimItemsByClaimIdFromInput() {

        System.out.println(
                "========== CLAIM ITEMS BY CLAIM ID =========="
        );

        System.out.println("Enter Claim ID:");

        int claimId =
                Integer.parseInt(scanner.nextLine());

        List<ClaimItem> claimItems =
                getClaimItemsByClaimId(claimId);

        if (claimItems.isEmpty()) {

            System.out.println(
                    "No claim items found for Claim ID="
                            + claimId
            );

        } else {

            System.out.println(
                    "Claim items for Claim ID="
                            + claimId + ":"
            );

            for (ClaimItem claimItem : claimItems) {
                System.out.println(claimItem);
            }
        }
    }

    // =========================
    // SERVICE DELEGATION METHODS
    // =========================

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