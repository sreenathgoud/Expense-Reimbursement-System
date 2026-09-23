package com.ers.controller;

import com.ers.model.User;
import com.ers.service.IUserService;

import java.sql.SQLException;
import java.util.List;

public class UserController {
    private IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public User addUser(User user) throws SQLException {
        return userService.addUser(user);
    }
    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }
    public User getUserById(int userId) {
        return null;
    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    public boolean deleteUserById(int userId) {
        return userService.deleteUserById(userId);
    }
    public User getUserByUsername(String username) {
        return null;
    }
    public boolean updateUserStatus(int userId, boolean active) {
        return userService.updateUserStatus(userId, active);
    }
}
