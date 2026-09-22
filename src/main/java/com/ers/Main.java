package com.ers;

import com.ers.dao.IUserDao;
import com.ers.dao.UserDaoImpl;
import com.ers.model.User;
import com.ers.util.JDBCUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {

                System.out.println("Expense Reimbursement System");

                // Run JDBC connection test
                JDBCUtil.testConnection();

        IUserDao userDao = new UserDaoImpl();

        User user = new User(
                "sreenath",
                "sreenath123",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        User result = userDao.addUser(user);

        if (result != null) {
            System.out.println("User inserted successfully");
            System.out.println("User ID: " + result.getUserId());
        } else {
            System.out.println("User insertion failed");
        }

    }
}
