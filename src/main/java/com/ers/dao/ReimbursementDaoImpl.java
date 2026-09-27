package com.ers.dao;

import ch.qos.logback.classic.Logger;
import com.ers.model.Reimbursement;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// CRUD Operations
public class ReimbursementDaoImpl implements IReimbursementDao {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(
                    ReimbursementDaoImpl.class
            );

    private JDBCUtil jdbcUtil;

    public ReimbursementDaoImpl(JDBCUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    // =========================================================
    // SQL QUERIES
    // =========================================================

    private final String addQuery =
            "INSERT INTO reimbursements " +
                    "(claim_id, reimbursed_amount, payment_mode, " +
                    "transaction_ref, reimbursement_date, processed_by, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String updateQuery =
            "UPDATE reimbursements SET " +
                    "claim_id = ?, reimbursed_amount = ?, payment_mode = ?, " +
                    "transaction_ref = ?, reimbursement_date = ?, " +
                    "processed_by = ?, status = ? " +
                    "WHERE reimbursement_id = ?";

    private final String getQuery =
            "SELECT * FROM reimbursements " +
                    "WHERE reimbursement_id = ?";

    private final String getAllQuery =
            "SELECT * FROM reimbursements";

    private final String deleteQuery =
            "DELETE FROM reimbursements " +
                    "WHERE reimbursement_id = ?";

    private final String getByClaimQuery =
            "SELECT * FROM reimbursements " +
                    "WHERE claim_id = ?";

    private final String getByEmployeeQuery =
            "SELECT * FROM reimbursements " +
                    "WHERE processed_by = ?";

    private final String getByStatusQuery =
            "SELECT * FROM reimbursements " +
                    "WHERE status = ?";

    private final String updateClaimStatusQuery =
            "UPDATE expense_claims " +
                    "SET status = 'PAID' " +
                    "WHERE claim_id = ? " +
                    "AND status = 'APPROVED'";


    // =========================================================
    // ADD REIMBURSEMENT
    // =========================================================

    @Override
    public Reimbursement addReimbursement(
            Reimbursement reimbursement) {

        Connection con = null;

        try {

            con = JDBCUtil.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            // -------------------------------------------------
            // STEP 1: INSERT REIMBURSEMENT
            // -------------------------------------------------

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    addQuery,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                ps.setInt(
                        1,
                        reimbursement.getClaimId()
                );

                ps.setDouble(
                        2,
                        reimbursement.getReimbursedAmount()
                );

                ps.setString(
                        3,
                        reimbursement.getPaymentMode()
                );

                ps.setString(
                        4,
                        reimbursement.getTransactionRef()
                );

                if (reimbursement.getReimbursementDate() != null) {

                    ps.setDate(
                            5,
                            Date.valueOf(
                                    reimbursement.getReimbursementDate()
                            )
                    );

                } else {

                    ps.setDate(
                            5,
                            null
                    );
                }

                ps.setInt(
                        6,
                        reimbursement.getProcessedBy()
                );

                ps.setString(
                        7,
                        reimbursement.getStatus()
                );

                int count = ps.executeUpdate();

                if (count == 0) {

                    con.rollback();

                    logger.warn(
                            "Reimbursement insertion failed."
                    );

                    return null;
                }

                // Get generated reimbursement ID
                try (
                        ResultSet rs =
                                ps.getGeneratedKeys()
                ) {

                    if (rs.next()) {

                        reimbursement.setReimbursementId(
                                rs.getInt(1)
                        );
                    }
                }
            }

            // -------------------------------------------------
            // STEP 2: IF PROCESSED, MARK CLAIM AS PAID
            // -------------------------------------------------

            if ("PROCESSED".equalsIgnoreCase(
                    reimbursement.getStatus())) {

                try (
                        PreparedStatement ps =
                                con.prepareStatement(
                                        updateClaimStatusQuery
                                )
                ) {

                    // Query contains only ONE ?
                    // Therefore only claimId is required.
                    ps.setInt(
                            1,
                            reimbursement.getClaimId()
                    );

                    int count = ps.executeUpdate();

                    if (count == 0) {

                        con.rollback();

                        logger.warn(
                                "Claim is not APPROVED or claim " +
                                        "does not exist. Claim ID={}",
                                reimbursement.getClaimId()
                        );

                        return null;
                    }
                }

                logger.info(
                        "Expense claim marked as PAID. Claim ID={}",
                        reimbursement.getClaimId()
                );
            }

            // -------------------------------------------------
            // STEP 3: COMMIT TRANSACTION
            // -------------------------------------------------

            con.commit();

            logger.info(
                    "Reimbursement added successfully. " +
                            "Reimbursement ID={}, Claim ID={}",
                    reimbursement.getReimbursementId(),
                    reimbursement.getClaimId()
            );

            return reimbursement;

        } catch (SQLException e) {

            // -------------------------------------------------
            // ROLLBACK TRANSACTION
            // -------------------------------------------------

            if (con != null) {

                try {

                    con.rollback();

                    logger.warn(
                            "Transaction rolled back."
                    );

                } catch (SQLException rollbackException) {

                    logger.error(
                            "Rollback failed.",
                            rollbackException
                    );
                }
            }

            logger.error(
                    "Error while adding reimbursement.",
                    e
            );

            return null;

        } finally {

            // -------------------------------------------------
            // CLOSE CONNECTION
            // -------------------------------------------------

            if (con != null) {

                try {

                    con.setAutoCommit(true);
                    con.close();

                } catch (SQLException e) {

                    logger.error(
                            "Error while closing database connection.",
                            e
                    );
                }
            }
        }
    }


    // =========================================================
    // UPDATE REIMBURSEMENT
    // =========================================================

    @Override
    public boolean updateReimbursement(
            Reimbursement reimbursement) {

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(updateQuery)
        ) {

            ps.setInt(
                    1,
                    reimbursement.getClaimId()
            );

            ps.setDouble(
                    2,
                    reimbursement.getReimbursedAmount()
            );

            ps.setString(
                    3,
                    reimbursement.getPaymentMode()
            );

            ps.setString(
                    4,
                    reimbursement.getTransactionRef()
            );

            if (reimbursement.getReimbursementDate() != null) {

                ps.setDate(
                        5,
                        Date.valueOf(
                                reimbursement.getReimbursementDate()
                        )
                );

            } else {

                ps.setDate(
                        5,
                        null
                );
            }

            ps.setInt(
                    6,
                    reimbursement.getProcessedBy()
            );

            ps.setString(
                    7,
                    reimbursement.getStatus()
            );

            ps.setInt(
                    8,
                    reimbursement.getReimbursementId()
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                logger.info(
                        "Reimbursement updated successfully. ID={}",
                        reimbursement.getReimbursementId()
                );

                return true;
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while updating reimbursement. ID={}",
                    reimbursement.getReimbursementId(),
                    e
            );
        }

        return false;
    }


    // =========================================================
    // GET REIMBURSEMENT BY ID
    // =========================================================

    @Override
    public Reimbursement getReimbursementById(
            int reimbursementId) {

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(
                    1,
                    reimbursementId
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapReimbursement(rs);
                }
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while finding reimbursement. ID={}",
                    reimbursementId,
                    e
            );
        }

        return null;
    }


    // =========================================================
    // GET ALL REIMBURSEMENTS
    // =========================================================

    @Override
    public List<Reimbursement> getAllReimbursements() {

        List<Reimbursement> reimbursements =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                reimbursements.add(
                        mapReimbursement(rs)
                );
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while retrieving all reimbursements.",
                    e
            );
        }

        return reimbursements;
    }


    // =========================================================
    // DELETE REIMBURSEMENT
    // =========================================================

    @Override
    public boolean deleteReimbursementById(
            int reimbursementId) {

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(
                    1,
                    reimbursementId
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                logger.info(
                        "Reimbursement deleted successfully. ID={}",
                        reimbursementId
                );

                return true;
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting reimbursement. ID={}",
                    reimbursementId,
                    e
            );
        }

        return false;
    }


    // =========================================================
    // GET REIMBURSEMENT BY CLAIM ID
    // =========================================================

    @Override
    public Reimbursement getReimbursementByClaimId(
            int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(getByClaimQuery)
        ) {

            ps.setInt(
                    1,
                    claimId
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapReimbursement(rs);
                }
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while finding reimbursement for claim ID={}",
                    claimId,
                    e
            );
        }

        return null;
    }


    // =========================================================
    // GET REIMBURSEMENTS BY EMPLOYEE ID
    // =========================================================

    @Override
    public List<Reimbursement> getReimbursementsByEmployeeId(
            int employeeId) {

        List<Reimbursement> reimbursements =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(getByEmployeeQuery)
        ) {

            ps.setInt(
                    1,
                    employeeId
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    reimbursements.add(
                            mapReimbursement(rs)
                    );
                }
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while finding reimbursements for " +
                            "employee ID={}",
                    employeeId,
                    e
            );
        }

        return reimbursements;
    }


    // =========================================================
    // GET REIMBURSEMENTS BY STATUS
    // =========================================================

    @Override
    public List<Reimbursement> getReimbursementsByStatus(
            String status) {

        List<Reimbursement> reimbursements =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(getByStatusQuery)
        ) {

            ps.setString(
                    1,
                    status
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    reimbursements.add(
                            mapReimbursement(rs)
                    );
                }
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while finding reimbursements by status={}",
                    status,
                    e
            );
        }

        return reimbursements;
    }


    // =========================================================
    // MAP RESULT SET TO REIMBURSEMENT
    // =========================================================

    private Reimbursement mapReimbursement(
            ResultSet rs) throws SQLException {

        Reimbursement reimbursement =
                new Reimbursement(
                        rs.getInt("claim_id"),
                        rs.getDouble("reimbursed_amount"),
                        rs.getString("payment_mode"),
                        rs.getString("transaction_ref"),
                        rs.getDate("reimbursement_date") != null
                                ? rs.getDate(
                                "reimbursement_date"
                        ).toLocalDate()
                                : null,
                        rs.getInt("processed_by"),
                        rs.getString("status")
                );

        reimbursement.setReimbursementId(
                rs.getInt("reimbursement_id")
        );

        return reimbursement;
    }
}