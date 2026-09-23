package com.ers.controller;

import com.ers.model.User;
import com.ers.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void addUser() throws SQLException {
        // Arrange
        IUserService userServiceMock =
                Mockito.mock(IUserService.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        Mockito.when(userServiceMock.addUser(user))
                .thenReturn(user);

        UserController userController =
                new UserController(userServiceMock);

        // Act
        User actualResult =
                userController.addUser(user);

        // Assert
        Assertions.assertEquals(user, actualResult);
    }


    @Test
    void updateUser() {

        // Arrange
        IUserService userServiceMock =
                Mockito.mock(IUserService.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        Mockito.when(userServiceMock.updateUser(user))
                .thenReturn(true);

        UserController userController =
                new UserController(userServiceMock);

        // Act
        boolean actualResult =
                userController.updateUser(user);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void getAllUsers() {
        // Arrange
        IUserService userServiceMock =
                Mockito.mock(IUserService.class);

        User user = new User(
                "sreenath",
                "password",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        List<User> users = List.of(user);

        Mockito.when(userServiceMock.getAllUsers())
                .thenReturn(users);

        UserController userController =
                new UserController(userServiceMock);

        // Act
        List<User> actualResult =
                userController.getAllUsers();

        // Assert
        Assertions.assertEquals(users, actualResult);
    }

    @Test
    void deleteUserById() {
        // Arrange
        IUserService userServiceMock =
                Mockito.mock(IUserService.class);

        int userId = 4;

        Mockito.when(userServiceMock.deleteUserById(userId))
                .thenReturn(true);

        UserController userController =
                new UserController(userServiceMock);

        // Act
        boolean actualResult =
                userController.deleteUserById(userId);

        // Assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void updateUserStatus() {
        // Arrange
        IUserService userServiceMock =
                Mockito.mock(IUserService.class);

        int userId = 4;
        boolean active = false;

        Mockito.when(userServiceMock.updateUserStatus(userId, active))
                .thenReturn(true);

        UserController userController =
                new UserController(userServiceMock);

        // Act
        boolean actualResult =
                userController.updateUserStatus(userId, active);

        // Assert
        Assertions.assertTrue(actualResult);
    }
}