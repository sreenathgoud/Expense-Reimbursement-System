package com.ers.dao;

import com.ers.model.ExpenseClaim;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseClaimDaoImpl implements IExpenseClaimDao {

    JDBCUtil jdbcUtil;

    public ExpenseClaimDaoImpl(JDBCUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    private final String addQuery =
            "INSERT INTO expense_claims " +
                    "(employee_id, claim_description, claim_amount, claim_date, status, document_path) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private final String updateQuery =
            "UPDATE expense_claims SET " +
                    "employee_id = ?, claim_description = ?, claim_amount = ?, " +
                    "claim_date = ?, status = ?, document_path = ? " +
                    "WHERE claim_id = ?";

    private final String getQuery =
            "SELECT * FROM expense_claims WHERE claim_id = ?";

    private final String getAllQuery =
            "SELECT * FROM expense_claims";

    private final String deleteQuery =
            "DELETE FROM expense_claims WHERE claim_id = ?";

    private final String getByEmployeeQuery =
            "SELECT * FROM expense_claims WHERE employee_id = ?";

    private final String submitQuery =
            "UPDATE expense_claims SET status = 'SUBMITTED' " +
                    "WHERE claim_id = ? AND status = 'DRAFT'";

    private final String approveQuery =
            "UPDATE expense_claims SET status = 'APPROVED' " +
                    "WHERE claim_id = ? AND status = 'SUBMITTED'";

    private final String rejectQuery =
            "UPDATE expense_claims SET status = 'REJECTED' " +
                    "WHERE claim_id = ? AND status = 'SUBMITTED'";

    private final String getByStatusQuery =
            "SELECT * FROM expense_claims WHERE status = ?";


    @Override
    public ExpenseClaim addExpenseClaim(
            ExpenseClaim expenseClaim) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                addQuery,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setInt(
                    1,
                    expenseClaim.getEmployeeId()
            );

            ps.setString(
                    2,
                    expenseClaim.getClaimDesc()
            );

            ps.setDouble(
                    3,
                    expenseClaim.getClaimAmount()
            );

            ps.setDate(
                    4,
                    Date.valueOf(expenseClaim.getClaimDate())
            );

            ps.setString(
                    5,
                    expenseClaim.getStatus()
            );

            ps.setString(
                    6,
                    expenseClaim.getDocumentPath()
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        expenseClaim.setClaimId(
                                rs.getInt(1)
                        );
                    }
                }

                return expenseClaim;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean updateExpenseClaim(
            ExpenseClaim expenseClaim) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(updateQuery)
        ) {

            ps.setInt(
                    1,
                    expenseClaim.getEmployeeId()
            );

            ps.setString(
                    2,
                    expenseClaim.getClaimDesc()
            );

            ps.setDouble(
                    3,
                    expenseClaim.getClaimAmount()
            );

            ps.setDate(
                    4,
                    Date.valueOf(expenseClaim.getClaimDate())
            );

            ps.setString(
                    5,
                    expenseClaim.getStatus()
            );

            ps.setString(
                    6,
                    expenseClaim.getDocumentPath()
            );

            ps.setInt(
                    7,
                    expenseClaim.getClaimId()
            );

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ExpenseClaim getExpenseClaimById(
            int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(1, claimId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<ExpenseClaim> getAllExpenseClaims() {

        List<ExpenseClaim> expenseClaims =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

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

                expenseClaims.add(expenseClaim);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseClaims;
    }


    @Override
    public boolean deleteExpenseClaimById(
            int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(1, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public List<ExpenseClaim> getClaimsByEmployeeId(
            int employeeId) {

        List<ExpenseClaim> expenseClaims =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getByEmployeeQuery)
        ) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

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

                    expenseClaims.add(expenseClaim);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseClaims;
    }


    @Override
    public boolean submitClaim(int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(submitQuery)
        ) {

            ps.setInt(1, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean approveClaim(int claimId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(approveQuery)
        ) {

            ps.setInt(1, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean rejectClaim(
            int claimId,
            String reason) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(rejectQuery)
        ) {

            ps.setInt(1, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public List<ExpenseClaim> getClaimsByStatus(
            String status) {

        List<ExpenseClaim> expenseClaims =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getByStatusQuery)
        ) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

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

                    expenseClaims.add(expenseClaim);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseClaims;
    }
}