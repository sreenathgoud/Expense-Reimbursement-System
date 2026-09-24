package com.ers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.mysql.cj.conf.PropertyKey.logger;

public class JDBCUtil {
    private static final Logger logger =
            LoggerFactory.getLogger(JDBCUtil.class);
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/expense_reimbursement_system";

    private static final String DB_USERNAME =System.getenv("DB_USERNAME") ;
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
            logger.info("Database connected successfully!");
//            System.out.println("Database connected successfully!");

        } catch (SQLException e) {
            logger.error("Database connection failed!", e);
//            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }





}