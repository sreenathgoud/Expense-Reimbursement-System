package com.ers.service;

import com.ers.dao.IReimbursementDao;
import com.ers.model.Reimbursement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

public class ReimbursementServiceImplTest {

    @Test
    void addReimbursementTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        Mockito.when(
                reimbursementDao.addReimbursement(reimbursement)
        ).thenReturn(reimbursement);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        Reimbursement actualResult =
                service.addReimbursement(reimbursement);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                3,
                actualResult.getClaimId()
        );
    }

    @Test
    void addReimbursementNullTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.addReimbursement(null)
        );
    }

    @Test
    void updateReimbursementTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        reimbursement.setReimbursementId(1);

        Mockito.when(
                reimbursementDao.updateReimbursement(
                        reimbursement
                )
        ).thenReturn(true);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        boolean actualResult =
                service.updateReimbursement(
                        reimbursement
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void updateReimbursementInvalidIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.updateReimbursement(reimbursement)
        );
    }

    @Test
    void getReimbursementByIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        reimbursement.setReimbursementId(1);

        Mockito.when(
                reimbursementDao.getReimbursementById(1)
        ).thenReturn(reimbursement);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        Reimbursement actualResult =
                service.getReimbursementById(1);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.getReimbursementId()
        );
    }

    @Test
    void getReimbursementByIdInvalidIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getReimbursementById(0)
        );
    }

    @Test
    void getAllReimbursementsTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        List<Reimbursement> reimbursements =
                List.of(reimbursement);

        Mockito.when(
                reimbursementDao.getAllReimbursements()
        ).thenReturn(reimbursements);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        List<Reimbursement> actualResult =
                service.getAllReimbursements();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
    }

    @Test
    void deleteReimbursementByIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Mockito.when(
                reimbursementDao.deleteReimbursementById(1)
        ).thenReturn(true);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        boolean actualResult =
                service.deleteReimbursementById(1);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void deleteReimbursementByIdInvalidIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteReimbursementById(0)
        );
    }

    @Test
    void getReimbursementByClaimIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        Mockito.when(
                reimbursementDao.getReimbursementByClaimId(3)
        ).thenReturn(reimbursement);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        Reimbursement actualResult =
                service.getReimbursementByClaimId(3);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                3,
                actualResult.getClaimId()
        );
    }

    @Test
    void getReimbursementByClaimIdInvalidIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getReimbursementByClaimId(0)
        );
    }

    @Test
    void getReimbursementsByEmployeeIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        List<Reimbursement> reimbursements =
                List.of(reimbursement);

        Mockito.when(
                reimbursementDao.getReimbursementsByEmployeeId(20)
        ).thenReturn(reimbursements);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        List<Reimbursement> actualResult =
                service.getReimbursementsByEmployeeId(20);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
        Assertions.assertEquals(
                20,
                actualResult.get(0).getProcessedBy()
        );
    }

    @Test
    void getReimbursementsByEmployeeIdInvalidIdTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getReimbursementsByEmployeeId(0)
        );
    }

    @Test
    void getReimbursementsByStatusTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        Reimbursement reimbursement =
                new Reimbursement(
                        3,
                        2000.00,
                        "BANK_TRANSFER",
                        null,
                        LocalDate.now(),
                        20,
                        "PROCESSED"
                );

        List<Reimbursement> reimbursements =
                List.of(reimbursement);

        Mockito.when(
                reimbursementDao.getReimbursementsByStatus("PROCESSED")
        ).thenReturn(reimbursements);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act
        List<Reimbursement> actualResult =
                service.getReimbursementsByStatus("PROCESSED");

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                1,
                actualResult.size()
        );
        Assertions.assertEquals(
                "PROCESSED",
                actualResult.get(0).getStatus()
        );
    }

    @Test
    void getReimbursementsByStatusInvalidStatusTest() {

        // Arrange
        IReimbursementDao reimbursementDao =
                Mockito.mock(IReimbursementDao.class);

        ReimbursementServiceImpl service =
                new ReimbursementServiceImpl(
                        reimbursementDao
                );

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getReimbursementsByStatus("")
        );
    }
}