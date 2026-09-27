package com.ers.dao;

import com.ers.model.ClaimItem;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class ClaimItemDaoImplTest {

    @Test
    void addClaimItemTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        ClaimItem claimItem =
                new ClaimItem(
                        3,
                        2,
                        "Travel expense",
                        1500.00,
                        LocalDate.now()
                );

        // Act
        ClaimItem actualResult =
                claimItemDao.addClaimItem(claimItem);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(
                actualResult.getItemId() > 0
        );
    }


    @Test
    void updateClaimItemTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        ClaimItem claimItem =
                new ClaimItem(
                        1,
                        2,
                        "Updated travel expense",
                        2000.00,
                        LocalDate.now()
                );

        claimItem.setItemId(1);

        // Act
        boolean actualResult =
                claimItemDao.updateClaimItem(
                        claimItem
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getClaimItemByIdTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        // Act
        ClaimItem actualResult =
                claimItemDao.getClaimItemById(1);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.getItemId()
        );
    }


    @Test
    void getAllClaimItemsTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        // Act
        List<ClaimItem> actualResult =
                claimItemDao.getAllClaimItems();

        // Assert
        Assertions.assertNotNull(actualResult);
    }


    @Test
    void deleteClaimItemByIdTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        ClaimItem claimItem =
                new ClaimItem(
                        1,
                        1,
                        "Temporary expense",
                        500.00,
                        LocalDate.now()
                );

        ClaimItem savedClaimItem =
                claimItemDao.addClaimItem(
                        claimItem
                );

        Assertions.assertNotNull(savedClaimItem);

        int itemId =
                savedClaimItem.getItemId();

        // Act
        boolean actualResult =
                claimItemDao.deleteClaimItemById(
                        itemId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getClaimItemsByClaimIdTest() {

        // Arrange
        IClaimItemDao claimItemDao =
                new ClaimItemDaoImpl(new JDBCUtil());

        // Act
        List<ClaimItem> actualResult =
                claimItemDao.getClaimItemsByClaimId(1);

        // Assert
        Assertions.assertNotNull(actualResult);
    }
}