package com.ers.service;

import com.ers.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    User addUser(User user) throws SQLException;
    boolean updateUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
    boolean deleteUserById(int userId);
    User getUserByUsername(String username);
    boolean updateUserStatus(int userId, boolean active);
}
