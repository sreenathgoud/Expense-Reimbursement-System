package com.ers.dao;

import com.ers.model.Reimbursement;
import com.ers.util.JDBCUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class ReimbursementDaoImplTest {

    private final IReimbursementDao reimbursementDao =
            new ReimbursementDaoImpl(new JDBCUtil());


    @Test
    void addReimbursementTest() {

        // Arrange
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

        // Act
        Reimbursement actualResult =
                reimbursementDao.addReimbursement(
                        reimbursement
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(
                actualResult.getReimbursementId() > 0
        );
    }


    @Test
    void updateReimbursementTest() {

        // Arrange
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

        Reimbursement added =
                reimbursementDao.addReimbursement(
                        reimbursement
                );

        Assertions.assertNotNull(added);

        added.setPaymentMode("UPI");
        added.setTransactionRef("TXN12345");

        // Act
        boolean actualResult =
                reimbursementDao.updateReimbursement(
                        added
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getReimbursementByIdTest() {

        // Arrange
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

        Reimbursement added =
                reimbursementDao.addReimbursement(
                        reimbursement
                );

        Assertions.assertNotNull(added);

        int reimbursementId =
                added.getReimbursementId();

        // Act
        Reimbursement actualResult =
                reimbursementDao.getReimbursementById(
                        reimbursementId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                reimbursementId,
                actualResult.getReimbursementId()
        );
    }


    @Test
    void getAllReimbursementsTest() {

        // Arrange
        // Act
        List<Reimbursement> actualResult =
                reimbursementDao.getAllReimbursements();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isEmpty());
    }


    @Test
    void deleteReimbursementByIdTest() {

        // Arrange
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

        Reimbursement added =
                reimbursementDao.addReimbursement(
                        reimbursement
                );

        Assertions.assertNotNull(added);

        int reimbursementId =
                added.getReimbursementId();

        // Act
        boolean actualResult =
                reimbursementDao.deleteReimbursementById(
                        reimbursementId
                );

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getReimbursementByClaimIdTest() {

        // Arrange
        int claimId = 3;

        // Act
        Reimbursement actualResult =
                reimbursementDao.getReimbursementByClaimId(
                        claimId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(
                claimId,
                actualResult.getClaimId()
        );
    }


    @Test
    void getReimbursementsByEmployeeIdTest() {

        // Arrange
        int employeeId = 20;

        // Act
        List<Reimbursement> actualResult =
                reimbursementDao.getReimbursementsByEmployeeId(
                        employeeId
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isEmpty());
        Assertions.assertEquals(
                employeeId,
                actualResult.get(0).getProcessedBy()
        );
    }


    @Test
    void getReimbursementsByStatusTest() {

        // Arrange
        String status = "PROCESSED";

        // Act
        List<Reimbursement> actualResult =
                reimbursementDao.getReimbursementsByStatus(
                        status
                );

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isEmpty());
        Assertions.assertEquals(
                status,
                actualResult.get(0).getStatus()
        );
    }
}