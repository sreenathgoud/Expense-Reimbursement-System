package com.ers.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static final Logger logger;

    static {
        LoggerContext context =
                (LoggerContext) LoggerFactory.getILoggerFactory();

        logger = context.getLogger(JDBCUtil.class.getName());
    }

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/expense_reimbursement_system";

    private static final String DB_USERNAME =System.getenv("DB_USERNAME");
            //System.getenv("DB_USERNAME");

    private static final String DB_PASSWORD =System.getenv("DB_PASSWORD");
            //System.getenv("DB_PASSWORD");

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

        } catch (SQLException e) {

            logger.error("Database connection failed!", e);
        }
    }
}