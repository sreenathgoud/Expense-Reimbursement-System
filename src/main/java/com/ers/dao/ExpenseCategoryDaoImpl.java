package com.ers.dao;

import ch.qos.logback.classic.Logger;
import com.ers.model.ExpenseCategory;
import com.ers.util.JDBCUtil;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryDaoImpl implements IExpenseCategoryDao{


    private final JDBCUtil jdbcUtil;

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(ExpenseCategoryDaoImpl.class);

    private final String addQuery =
            "INSERT INTO expense_categories " +
                    "(category_name, description) VALUES (?, ?)";

    private final String updateQuery =
            "UPDATE expense_categories " +
                    "SET category_name = ?, description = ? " +
                    "WHERE category_id = ?";

    private final String getQuery =
            "SELECT * FROM expense_categories " +
                    "WHERE category_id = ?";

    private final String getAllQuery =
            "SELECT * FROM expense_categories";

    private final String deleteQuery =
            "DELETE FROM expense_categories " +
                    "WHERE category_id = ?";

 public ExpenseCategoryDaoImpl(JDBCUtil jdbcUtil){
        this.jdbcUtil=jdbcUtil;
    }
    @Override
    public ExpenseCategory addExpenseCategory(ExpenseCategory expenseCategory) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(
                                addQuery,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setString(
                    1,
                    expenseCategory.getCategory_name()
            );

            ps.setString(
                    2,
                    expenseCategory.getDescription()
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        expenseCategory.setCategory_id(
                                rs.getInt(1)
                        );
                    }
                }

                logger.info(
                        "Expense category added successfully"
                );

                return expenseCategory;
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while adding expense category",
                    e
            );
        }
        return null;
    }

    @Override
    public boolean updateExpenseCategory(ExpenseCategory expenseCategory) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(updateQuery)
        ) {

            ps.setString(
                    1,
                    expenseCategory.getCategory_name()
            );

            ps.setString(
                    2,
                    expenseCategory.getDescription()
            );

            ps.setInt(
                    3,
                    expenseCategory.getCategory_id()
            );

            int count = ps.executeUpdate();

            if (count > 0) {

                logger.info(
                        "Expense category updated successfully"
                );

                return true;
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while updating expense category",
                    e
            );
        }

        return false;
    }

    @Override
    public ExpenseCategory getExpenseCategoryById(int categoryId) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getQuery)
        ) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ExpenseCategory expenseCategory =
                            new ExpenseCategory(
                                    rs.getString("category_name"),
                                    rs.getString("description")
                            );

                    expenseCategory.setCategory_id(
                            rs.getInt("category_id")
                    );

                    return expenseCategory;
                }
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while getting expense category",
                    e
            );
        }

        return null;
    }

    @Override
    public List<ExpenseCategory> getAllExpenseCategories() {
        List<ExpenseCategory> expenseCategories =
                new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(getAllQuery);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                ExpenseCategory expenseCategory =
                        new ExpenseCategory(
                                rs.getString("category_name"),
                                rs.getString("description")
                        );

                expenseCategory.setCategory_id(
                        rs.getInt("category_id")
                );

                expenseCategories.add(expenseCategory);
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while getting all expense categories",
                    e
            );
        }

        return expenseCategories;
    }

    @Override
    public boolean deleteExpenseCategoryById(int categoryId) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(deleteQuery)
        ) {

            ps.setInt(1, categoryId);

            int count = ps.executeUpdate();

            if (count > 0) {

                logger.info(
                        "Expense category deleted successfully"
                );

                return true;
            }

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting expense category",
                    e
            );
        }
        return false;
    }
}
