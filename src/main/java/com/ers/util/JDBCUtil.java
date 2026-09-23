package com.ers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/expense_reimbursement_system";

    private static final String DB_USERNAME =System.getenv("DB_USERNAME");
//System.getenv("DB_USERNAME")
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
//System.getenv("DB_PASSWORD")
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                DB_URL,
                DB_USERNAME,
                DB_PASSWORD
        );
    }

    public static void testConnection() {

        try (Connection con = getConnection()) {

            System.out.println("Database connected successfully!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }





}