package com.ers.controller;

import com.ers.model.User;
import com.ers.service.IUserService;

import java.util.List;

public class UserController {
    private IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public User addUser(User user) {
        return null;
    }
    public boolean updateUser(User user) {
        return false;
    }
    public User getUserById(int userId) {
        return null;
    }
    public List<User> getAllUsers() {
        return null;
    }
    public boolean deleteUserById(int userId) {
        return false;
    }
    public User getUserByUsername(String username) {
        return null;
    }
    public boolean updateUserStatus(int userId, boolean active) {
        return false;
    }
}
