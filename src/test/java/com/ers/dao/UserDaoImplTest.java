package com.ers.dao;

import com.ers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    @Test
    void addUser() throws SQLException {


            // Arrange
            IUserDao userDao = new UserDaoImpl();

            User user = new User(
                    "karan",
                    "karan@",
                    "ADMIN",
                    true,
                    LocalDateTime.now()
            );

            // Act
            User actualResult = userDao.addUser(user);

            // Assert
            Assertions.assertNotNull(actualResult);
        Assertions.assertTrue(actualResult.getUserId() > 0);
        }


    @Test
    void updateUser() {
        // Arrange
        IUserDao userDao = new UserDaoImpl();

        User user = new User(
                "updateduser",
                "updated123",
                "MANAGER",
                true,
                LocalDateTime.now()
        );

        user.setUserId(1);   // existing user_id

        // Act
        boolean actualResult = userDao.updateUser(user);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getAllUsers() {
        // Arrange
        IUserDao userDao = new UserDaoImpl();

        // Act
        List<User> actualResult = userDao.getAllUsers();

        // Assert
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void deleteUserById() {
        // Arrange
        IUserDao userDao = new UserDaoImpl();

        int userId = 1;   // existing user_id

        // Act
        boolean actualResult = userDao.deleteUserById(userId);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}