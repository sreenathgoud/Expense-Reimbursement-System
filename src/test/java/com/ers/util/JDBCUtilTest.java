package com.ers.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCUtilTest {
    private static final Logger logger =
            LoggerFactory.getLogger(JDBCUtilTest.class);

    @Test
    void connectionIsOpen() throws SQLException {

        logger.info("Running connectionIsOpen test case");

        // Arrange
        JDBCUtil jdbcUtil = new JDBCUtil();

        // Act
        Connection actualResult = jdbcUtil.getConnection();

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertFalse(actualResult.isClosed());

        logger.info("Database connection is open");

        // Cleanup
        actualResult.close();

        logger.info("Database connection closed successfully");
    }

    @Test
    void testConnection() {

        logger.info("Running testConnection test case");

        // Arrange
        JDBCUtil jdbcUtil = new JDBCUtil();

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> jdbcUtil.testConnection());

        logger.info("testConnection executed successfully");
    }
}


