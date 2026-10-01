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

    private final String updateDraftClaimForEmployeeQuery =
            "UPDATE expense_claims SET claim_description = ?, claim_amount = ?, " +
                    "claim_date = ?, document_path = ? " +
                    "WHERE claim_id = ? AND employee_id = ? AND status = 'DRAFT'";

    private final String getQuery =
            "SELECT * FROM expense_claims WHERE claim_id = ?";

    private final String getAllQuery =
            "SELECT * FROM expense_claims";

    private final String deleteQuery =
            "DELETE FROM expense_claims WHERE claim_id = ?";

    private final String deleteDraftClaimForEmployeeQuery =
            "DELETE FROM expense_claims " +
                    "WHERE claim_id = ? AND employee_id = ? AND status = 'DRAFT'";

    private final String getByEmployeeQuery =
            "SELECT * FROM expense_claims WHERE employee_id = ?";

    private final String submitQuery =
            "UPDATE expense_claims SET status = 'SUBMITTED' " +
                    "WHERE claim_id = ? AND status = 'DRAFT'";

    private final String approveQuery =
            "UPDATE expense_claims SET status = ? " +
                    "WHERE claim_id = ? AND status = 'SUBMITTED'";

    private final String getByManagerQuery =
            "SELECT ec.* FROM expense_claims ec " +
                    "JOIN employees e ON e.employee_id = ec.employee_id " +
                    "JOIN departments d ON d.department_id = e.department_id " +
                    "WHERE ec.claim_id = ? AND d.manager_id = ? AND ec.status = 'SUBMITTED'";

    private final String getClaimsForManagerQuery =
            "SELECT ec.* FROM expense_claims ec " +
                    "JOIN employees e ON e.employee_id = ec.employee_id " +
                    "JOIN departments d ON d.department_id = e.department_id " +
                    "WHERE d.manager_id = ?";

    private final String approveForManagerQuery =
            "UPDATE expense_claims ec SET status = 'APPROVED', review_remarks = ? " +
                    "WHERE ec.claim_id = ? AND ec.status = 'SUBMITTED' " +
                    "AND EXISTS (SELECT 1 FROM employees e JOIN departments d ON d.department_id = e.department_id " +
                    "WHERE e.employee_id = ec.employee_id AND d.manager_id = ?)";

    private final String rejectQuery =
            "UPDATE expense_claims SET status = 'REJECTED', review_remarks = ? " +
                    "WHERE claim_id = ? AND status = 'SUBMITTED'";

    private final String rejectForManagerQuery =
            "UPDATE expense_claims ec SET status = 'REJECTED', review_remarks = ? " +
                    "WHERE ec.claim_id = ? AND ec.status = 'SUBMITTED' " +
                    "AND EXISTS (SELECT 1 FROM employees e JOIN departments d ON d.department_id = e.department_id " +
                    "WHERE e.employee_id = ec.employee_id AND d.manager_id = ?)";

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
    public boolean updateDraftClaimForEmployee(ExpenseClaim expenseClaim, int employeeId) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(updateDraftClaimForEmployeeQuery)
        ) {
            ps.setString(1, expenseClaim.getClaimDesc());
            ps.setDouble(2, expenseClaim.getClaimAmount());
            ps.setDate(3, Date.valueOf(expenseClaim.getClaimDate()));
            ps.setString(4, expenseClaim.getDocumentPath());
            ps.setInt(5, expenseClaim.getClaimId());
            ps.setInt(6, employeeId);
            return ps.executeUpdate() > 0;
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
    public boolean deleteDraftClaimForEmployee(int claimId, int employeeId) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(deleteDraftClaimForEmployeeQuery)
        ) {
            ps.setInt(1, claimId);
            ps.setInt(2, employeeId);
            return ps.executeUpdate() > 0;
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
    public List<ExpenseClaim> getClaimsForManager(int managerId) {
        List<ExpenseClaim> claims = new ArrayList<>();
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(getClaimsForManagerQuery)
        ) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExpenseClaim claim = new ExpenseClaim(
                            rs.getInt("employee_id"),
                            rs.getString("claim_description"),
                            rs.getDouble("claim_amount"),
                            rs.getDate("claim_date").toLocalDate(),
                            rs.getString("status"),
                            rs.getString("document_path")
                    );
                    claim.setClaimId(rs.getInt("claim_id"));
                    claim.setReviewRemarks(rs.getString("review_remarks"));
                    claims.add(claim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
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
    public boolean approveClaim(int claimId,String status) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(approveQuery)
        ) {

            ps.setString(1,status);
            ps.setInt(2, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ExpenseClaim getExpenseClaimWithItemsForManager(int claimId, int managerId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(getByManagerQuery)
        ) {

            ps.setInt(1, claimId);
            ps.setInt(2, managerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ExpenseClaim expenseClaim = new ExpenseClaim(
                            rs.getInt("employee_id"),
                            rs.getString("claim_description"),
                            rs.getDouble("claim_amount"),
                            rs.getDate("claim_date").toLocalDate(),
                            rs.getString("status"),
                            rs.getString("document_path")
                    );
                    expenseClaim.setClaimId(rs.getInt("claim_id"));
                    return expenseClaim;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean approveClaimForManager(int claimId, int managerId) {
        return approveClaimForManager(claimId, managerId, null);
    }

    @Override
    public boolean approveClaimForManager(int claimId, int managerId, String remarks) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(approveForManagerQuery)
        ) {

            ps.setString(1, remarks);
            ps.setInt(2, claimId);
            ps.setInt(3, managerId);

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

            ps.setString(1, reason);
            ps.setInt(2, claimId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean rejectClaimForManager(int claimId, int managerId, String reason) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(rejectForManagerQuery)
        ) {
            ps.setString(1, reason);
            ps.setInt(2, claimId);
            ps.setInt(3, managerId);
            return ps.executeUpdate() > 0;
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