package com.ers.service;

import com.ers.dao.IUserDao;
import com.ers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Test
    void addUser() throws SQLException {
        // Arrange
        IUserDao userDaoMock = Mockito.mock(IUserDao.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        Mockito.when(userDaoMock.addUser(user)).thenReturn(user);

        UserServiceImpl userService = new UserServiceImpl(userDaoMock);

        // Act
        User actualResult = userService.addUser(user);

        // Assert
        Assertions.assertEquals(user, actualResult);
    }

    @Test
    void updateUser() {
        // Arrange
        IUserDao userDaoMock = Mockito.mock(IUserDao.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        Mockito.when(userDaoMock.updateUser(user))
                .thenReturn(true);

        UserServiceImpl userService =
                new UserServiceImpl(userDaoMock);

        // Act
        boolean actualResult = userService.updateUser(user);

        // Assert
        Assertions.assertTrue(actualResult);
    }


    @Test
    void getAllUsers() {
        // Arrange
        IUserDao userDaoMock = Mockito.mock(IUserDao.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        List<User> users = List.of(user);

        Mockito.when(userDaoMock.getAllUsers())
                .thenReturn(users);

        UserServiceImpl userService =
                new UserServiceImpl(userDaoMock);

        // Act
        List<User> actualResult = userService.getAllUsers();

        // Assert
        Assertions.assertEquals(users, actualResult);
    }

    @Test
    void deleteUserById() {
        // Arrange
        IUserDao userDaoMock = Mockito.mock(IUserDao.class);

        int userId = 4;

        Mockito.when(userDaoMock.deleteUserById(userId))
                .thenReturn(true);

        UserServiceImpl userService =
                new UserServiceImpl(userDaoMock);

        // Act
        boolean actualResult =
                userService.deleteUserById(userId);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void updateUserStatus() {
        // Arrange
        IUserDao userDaoMock = Mockito.mock(IUserDao.class);

        int userId = 4;
        boolean active = false;

        Mockito.when(userDaoMock.updateUserStatus(userId, active))
                .thenReturn(true);

        UserServiceImpl userService =
                new UserServiceImpl(userDaoMock);

        // Act
        boolean actualResult =
                userService.updateUserStatus(userId, active);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}