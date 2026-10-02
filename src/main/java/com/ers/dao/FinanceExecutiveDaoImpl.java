package com.ers.dao;

import com.ers.model.ExpenseClaim;
import com.ers.model.FinanceExecutive;
import com.ers.model.Reimbursement;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceExecutiveDaoImpl implements IFinanceExecutiveDao {

    private JDBCUtil jdbcUtil;

    public FinanceExecutiveDaoImpl(JDBCUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    // -----------------------------
    // SQL Queries
    // -----------------------------

    private final String addQuery =
            "INSERT INTO finance_executives " +
                    "(employee_id, full_name, email, department_id) " +
                    "VALUES (?, ?, ?, ?)";

    private final String updateQuery =
            "UPDATE finance_executives SET " +
                    "full_name = ?, email = ?, department_id = ? " +
                    "WHERE employee_id = ?";

    private final String getQuery =
            "SELECT * FROM finance_executives " +
                    "WHERE employee_id = ?";

    private final String getAllQuery =
            "SELECT * FROM finance_executives";

    private final String deleteQuery =
            "DELETE FROM finance_executives " +
                    "WHERE employee_id = ?";

    private final String getPendingClaimsQuery =
            "SELECT * FROM expense_claims " +
                    "WHERE status = 'APPROVED'";

    private final String getPendingClaimsForFinanceExecutiveQuery =
            "SELECT ec.* FROM expense_claims ec " +
                    "JOIN employees e ON e.employee_id = ec.employee_id " +
                    "JOIN finance_executives fe ON fe.department_id = e.department_id " +
                    "WHERE ec.status = 'APPROVED' AND fe.employee_id = ?";

    private final String getClaimByIdQuery =
            "SELECT * FROM expense_claims " +
                    "WHERE claim_id = ?";

    private final String getApprovedClaimByIdForFinanceExecutiveQuery =
            "SELECT ec.* FROM expense_claims ec " +
                    "JOIN employees e ON e.employee_id = ec.employee_id " +
                    "JOIN finance_executives fe ON fe.employee_id = ? " +
                    "WHERE ec.claim_id = ? AND ec.status = 'APPROVED' " +
                    "AND e.department_id = fe.department_id";
    private final String getClaimAmountQuery =
            "SELECT ec.claim_amount, e.department_id as employee_department_id " +
                    "FROM expense_claims ec " +
                    "JOIN employees e ON e.employee_id = ec.employee_id " +
                    "JOIN finance_executives fe ON fe.employee_id = ? " +
                    "WHERE ec.claim_id = ? AND ec.status = 'APPROVED' AND e.department_id = fe.department_id";

    private final String insertReimbursementQuery =
            "INSERT INTO reimbursements " +
                    "(claim_id, reimbursed_amount, payment_mode, " +
                    "transaction_ref, reimbursement_date, processed_by, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, 'PROCESSED')";

    private final String getReimbursementHistoryQuery =
            "SELECT * FROM reimbursements " +
                    "WHERE processed_by = ?";

    private final String updateClaimStatusQuery =
            "UPDATE expense_claims SET status = 'PAID' " +
                    "WHERE claim_id = ?";

    // -----------------------------
    // CRUD Operations
    // -----------------------------

    @Override
    public FinanceExecutive addFinanceExecutive(
            FinanceExecutive financeExecutive) {

        try (
                Connection con = JDBCUtil.getConnection();
        ) {
            return addFinanceExecutive(financeExecutive, con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public FinanceExecutive addFinanceExecutive(
            FinanceExecutive financeExecutive,
            Connection connection) {

        try (PreparedStatement ps = connection.prepareStatement(addQuery)) {
            ps.setInt(1, financeExecutive.getEmployeeId());
            ps.setString(2, financeExecutive.getFullName());
            ps.setString(3, financeExecutive.getEmail());
            ps.setInt(4, financeExecutive.getDepartmentId());

            return ps.executeUpdate() > 0 ? financeExecutive : null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean updateFinanceExecutive(
            FinanceExecutive financeExecutive) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(updateQuery)
        ) {

            ps.setString(1, financeExecutive.getFullName());
            ps.setString(2, financeExecutive.getEmail());
            ps.setInt(3, financeExecutive.getDepartmentId());
            ps.setInt(4, financeExecutive.getEmployeeId());

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public FinanceExecutive getFinanceExecutiveById(
            int employeeId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapFinanceExecutive(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<FinanceExecutive> getAllFinanceExecutives() {

        List<FinanceExecutive> financeExecutives =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                financeExecutives.add(
                        mapFinanceExecutive(rs)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return financeExecutives;
    }


    @Override
    public boolean deleteFinanceExecutiveById(
            int employeeId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(1, employeeId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // -----------------------------
    // Expense Claim Operations
    // -----------------------------

    @Override
    public List<ExpenseClaim> getPendingClaims() {

        List<ExpenseClaim> claims =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getPendingClaimsQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                claims.add(mapExpenseClaim(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }


    @Override
    public List<ExpenseClaim> getPendingClaimsForFinanceExecutive(int financeExecutiveEmployeeId) {

        List<ExpenseClaim> claims = new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(getPendingClaimsForFinanceExecutiveQuery)
        ) {

            ps.setInt(1, financeExecutiveEmployeeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    claims.add(mapExpenseClaim(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }

    @Override
    public ExpenseClaim getClaimById(int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getClaimByIdQuery)
        ) {

            ps.setInt(1, claimId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapExpenseClaim(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ExpenseClaim getApprovedClaimByIdForFinanceExecutive(
            int claimId,
            int financeExecutiveEmployeeId) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(getApprovedClaimByIdForFinanceExecutiveQuery)
        ) {
            ps.setInt(1, financeExecutiveEmployeeId);
            ps.setInt(2, claimId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapExpenseClaim(rs) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // -----------------------------
    // Reimbursement Operations
    // -----------------------------


    @Override
    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            String paymentMode) {

        return processPayment(claimId, financeExecutiveId, null, paymentMode, null, java.time.LocalDate.now());
    }

    @Override
    public boolean processPayment(
            int claimId,
            int financeExecutiveId,
            double reimbursedAmount,
            String paymentMode,
            String transactionRef,
            java.time.LocalDate reimbursementDate) {

        return processPayment(claimId, financeExecutiveId, Double.valueOf(reimbursedAmount), paymentMode, transactionRef, reimbursementDate);
    }

    private boolean processPayment(
            int claimId,
            int financeExecutiveId,
            Double requestedAmount,
            String paymentMode,
            String transactionRef,
            java.time.LocalDate reimbursementDate) {

        try (
                Connection con = JDBCUtil.getConnection()
        ) {

            con.setAutoCommit(false);

            try (
                    PreparedStatement claimPs =
                            con.prepareStatement(getClaimAmountQuery)
            ) {

                claimPs.setInt(1, financeExecutiveId);
                claimPs.setInt(2, claimId);

                try (ResultSet rs = claimPs.executeQuery()) {

                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }

                    double claimAmount =
                            rs.getDouble("claim_amount");
                    double paymentAmount = requestedAmount == null ? claimAmount : requestedAmount;
                    if (paymentAmount <= 0 || paymentAmount > claimAmount) {
                        con.rollback();
                        return false;
                    }

                    try (
                            PreparedStatement reimbursementPs =
                                    con.prepareStatement(
                                            insertReimbursementQuery)
                    ) {

                        reimbursementPs.setInt(1, claimId);
                        reimbursementPs.setDouble(2, paymentAmount);
                        reimbursementPs.setString(3, paymentMode);
                        reimbursementPs.setString(4, transactionRef);
                        reimbursementPs.setDate(
                                5,
                                Date.valueOf(
                                        reimbursementDate
                                )
                        );
                        reimbursementPs.setInt(
                                6,
                                financeExecutiveId
                        );

                        int reimbursementCount =
                                reimbursementPs.executeUpdate();

                        if (reimbursementCount == 0) {
                            con.rollback();
                            return false;
                        }
                    }

                    try (
                            PreparedStatement claimStatusPs =
                                    con.prepareStatement(
                                            updateClaimStatusQuery)
                    ) {

                        claimStatusPs.setInt(1, claimId);

                        int claimCount =
                                claimStatusPs.executeUpdate();

                        if (claimCount == 0) {
                            con.rollback();
                            return false;
                        }
                    }

                    con.commit();
                    return true;
                }

            } catch (SQLException e) {

                con.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Reimbursement> getReimbursementHistory(
            int financeExecutiveId) {

        List<Reimbursement> reimbursements =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                getReimbursementHistoryQuery)
        ) {

            ps.setInt(1, financeExecutiveId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Reimbursement reimbursement =
                            new Reimbursement(
                                    rs.getInt("claim_id"),
                                    rs.getDouble("reimbursed_amount"),
                                    rs.getString("payment_mode"),
                                    rs.getString("transaction_ref"),
                                    rs.getDate("reimbursement_date")
                                            .toLocalDate(),
                                    rs.getInt("processed_by"),
                                    rs.getString("status")
                            );

                    reimbursement.setReimbursementId(
                            rs.getInt("reimbursement_id")
                    );

                    reimbursements.add(reimbursement);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }


    // -----------------------------
    // Mapping Methods
    // -----------------------------

    private FinanceExecutive mapFinanceExecutive(
            ResultSet rs) throws SQLException {

        FinanceExecutive financeExecutive =
                new FinanceExecutive(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("department_id")
                );

        return financeExecutive;
    }


    private ExpenseClaim mapExpenseClaim(
            ResultSet rs) throws SQLException {

        ExpenseClaim expenseClaim =
                new ExpenseClaim(
                        rs.getInt("employee_id"),
                        rs.getString("claim_description"),
                        rs.getDouble("claim_amount"),
                        rs.getDate("claim_date").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("document_path")
                );

        expenseClaim.setClaimId(
                rs.getInt("claim_id")
        );

        return expenseClaim;
    }
}