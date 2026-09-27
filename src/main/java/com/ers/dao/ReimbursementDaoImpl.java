package com.ers.dao;

import com.ers.model.Reimbursement;
import com.ers.util.JDBCUtil;

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

    private JDBCUtil jdbcUtil;

    public ReimbursementDaoImpl(JDBCUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

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


    @Override
    public Reimbursement addReimbursement(
            Reimbursement reimbursement) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                addQuery,
                                Statement.RETURN_GENERATED_KEYS)
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

            ps.setDate(
                    5,
                    reimbursement.getReimbursementDate() != null
                            ? Date.valueOf(
                            reimbursement.getReimbursementDate())
                            : null
            );

            ps.setInt(
                    6,
                    reimbursement.getProcessedBy()
            );

            ps.setString(
                    7,
                    reimbursement.getStatus()
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs =
                             ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        reimbursement.setReimbursementId(
                                rs.getInt(1)
                        );
                    }
                }

                return reimbursement;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


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

            ps.setDate(
                    5,
                    reimbursement.getReimbursementDate() != null
                            ? Date.valueOf(
                            reimbursement.getReimbursementDate())
                            : null
            );

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

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Reimbursement getReimbursementById(
            int reimbursementId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(1, reimbursementId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {
                    return mapReimbursement(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<Reimbursement> getAllReimbursements() {

        List<Reimbursement> reimbursements =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                reimbursements.add(
                        mapReimbursement(rs)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }


    @Override
    public boolean deleteReimbursementById(
            int reimbursementId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(1, reimbursementId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Reimbursement getReimbursementByClaimId(
            int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getByClaimQuery)
        ) {

            ps.setInt(1, claimId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {
                    return mapReimbursement(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


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

            ps.setInt(1, employeeId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    reimbursements.add(
                            mapReimbursement(rs)
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }


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

            ps.setString(1, status);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    reimbursements.add(
                            mapReimbursement(rs)
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }


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