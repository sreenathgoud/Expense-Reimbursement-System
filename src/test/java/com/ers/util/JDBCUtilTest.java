package com.ers.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCUtilTest {

    @Test

        void connectionIsNotNull() throws SQLException {

            System.out.println("running connectionIsNotNull test case");

            // Arrange
            JDBCUtil jdbcUtil = new JDBCUtil();

            // Act
            Connection actualResult = jdbcUtil.getConnection();

            // Assert
            Assertions.assertNotNull(actualResult);
        }
}


