package com.ers.dao;

import com.ers.model.ClaimItem;
import com.ers.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimItemDaoImpl implements IClaimItemDao {

    // JDBCUtil object
    private JDBCUtil jdbcUtil;

    // Constructor
    public ClaimItemDaoImpl(JDBCUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    // SQL queries
    private final String addQuery =
            "INSERT INTO claim_items " +
                    "(claim_id, category_id, description, amount, expense_date) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final String updateQuery =
            "UPDATE claim_items SET " +
                    "claim_id = ?, category_id = ?, description = ?, " +
                    "amount = ?, expense_date = ? " +
                    "WHERE item_id = ?";

    private final String getQuery =
            "SELECT * FROM claim_items WHERE item_id = ?";

    private final String getAllQuery =
            "SELECT * FROM claim_items";

    private final String deleteQuery =
            "DELETE FROM claim_items WHERE item_id = ?";

    private final String getByClaimQuery =
            "SELECT * FROM claim_items WHERE claim_id = ?";


    @Override
    public ClaimItem addClaimItem(ClaimItem claimItem) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                addQuery,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setInt(1, claimItem.getClaimId());
            ps.setInt(2, claimItem.getCategoryId());
            ps.setString(3, claimItem.getDescription());
            ps.setDouble(4, claimItem.getAmount());
            ps.setDate(
                    5,
                    Date.valueOf(claimItem.getExpenseDate())
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        claimItem.setItemId(
                                rs.getInt(1)
                        );
                    }
                }

                return claimItem;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean updateClaimItem(
            ClaimItem claimItem) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(updateQuery)
        ) {

            ps.setInt(1, claimItem.getClaimId());
            ps.setInt(2, claimItem.getCategoryId());
            ps.setString(3, claimItem.getDescription());
            ps.setDouble(4, claimItem.getAmount());
            ps.setDate(
                    5,
                    Date.valueOf(
                            claimItem.getExpenseDate()
                    )
            );
            ps.setInt(6, claimItem.getItemId());

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ClaimItem getClaimItemById(int itemId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapClaimItem(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<ClaimItem> getAllClaimItems() {

        List<ClaimItem> claimItems =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                claimItems.add(
                        mapClaimItem(rs)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claimItems;
    }


    @Override
    public boolean deleteClaimItemById(int itemId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(1, itemId);

            int count = ps.executeUpdate();

            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public List<ClaimItem> getClaimItemsByClaimId(
            int claimId) {

        List<ClaimItem> claimItems =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getByClaimQuery)
        ) {

            ps.setInt(1, claimId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    claimItems.add(
                            mapClaimItem(rs)
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claimItems;
    }


    private ClaimItem mapClaimItem(
            ResultSet rs) throws SQLException {

        ClaimItem claimItem =
                new ClaimItem(
                        rs.getInt("claim_id"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getDate("expense_date")
                                .toLocalDate()
                );

        claimItem.setItemId(
                rs.getInt("item_id")
        );

        return claimItem;
    }
}