package com.ers.service;

import com.ers.dao.IClaimItemDao;
import com.ers.model.ClaimItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class ClaimItemServiceImplTest {

    @Test
    void addClaimItemTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        ClaimItem claimItem =
                new ClaimItem(
                        1,
                        2,
                        "Travel expense",
                        1500.00,
                        LocalDate.now()
                );

        Mockito.when(
                claimItemDaoMock.addClaimItem(
                        claimItem
                )
        ).thenReturn(claimItem);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        ClaimItem actualResult =
                claimItemService.addClaimItem(
                        claimItem
                );

        // Assert
        Assertions.assertEquals(
                claimItem,
                actualResult
        );
    }


    @Test
    void updateClaimItemTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        ClaimItem claimItem =
                new ClaimItem(
                        1,
                        2,
                        "Updated travel expense",
                        2000.00,
                        LocalDate.now()
                );

        claimItem.setItemId(1);

        Mockito.when(
                claimItemDaoMock.updateClaimItem(
                        claimItem
                )
        ).thenReturn(true);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        boolean actualResult =
                claimItemService.updateClaimItem(
                        claimItem
                );

        // Assert
        Assertions.assertTrue(
                actualResult
        );
    }


    @Test
    void getClaimItemByIdTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        ClaimItem claimItem =
                new ClaimItem(
                        1,
                        2,
                        "Travel expense",
                        1500.00,
                        LocalDate.now()
                );

        claimItem.setItemId(1);

        Mockito.when(
                claimItemDaoMock.getClaimItemById(1)
        ).thenReturn(claimItem);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        ClaimItem actualResult =
                claimItemService.getClaimItemById(1);

        // Assert
        Assertions.assertEquals(
                claimItem,
                actualResult
        );
    }


    @Test
    void getAllClaimItemsTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        List<ClaimItem> claimItems =
                List.of(
                        new ClaimItem(
                                1,
                                2,
                                "Travel expense",
                                1500.00,
                                LocalDate.now()
                        ),
                        new ClaimItem(
                                1,
                                2,
                                "Food expense",
                                1000.00,
                                LocalDate.now()
                        )
                );

        Mockito.when(
                claimItemDaoMock.getAllClaimItems()
        ).thenReturn(claimItems);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        List<ClaimItem> actualResult =
                claimItemService.getAllClaimItems();

        // Assert
        Assertions.assertEquals(
                claimItems,
                actualResult
        );
    }


    @Test
    void deleteClaimItemByIdTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        Mockito.when(
                claimItemDaoMock.deleteClaimItemById(1)
        ).thenReturn(true);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        boolean actualResult =
                claimItemService.deleteClaimItemById(1);

        // Assert
        Assertions.assertTrue(
                actualResult
        );
    }


    @Test
    void getClaimItemsByClaimIdTest() {

        // Arrange
        IClaimItemDao claimItemDaoMock =
                Mockito.mock(IClaimItemDao.class);

        List<ClaimItem> claimItems =
                List.of(
                        new ClaimItem(
                                1,
                                2,
                                "Travel expense",
                                1500.00,
                                LocalDate.now()
                        )
                );

        Mockito.when(
                claimItemDaoMock.getClaimItemsByClaimId(1)
        ).thenReturn(claimItems);

        ClaimItemServiceImpl claimItemService =
                new ClaimItemServiceImpl(
                        claimItemDaoMock
                );

        // Act
        List<ClaimItem> actualResult =
                claimItemService.getClaimItemsByClaimId(1);

        // Assert
        Assertions.assertEquals(
                claimItems,
                actualResult
        );
    }
}