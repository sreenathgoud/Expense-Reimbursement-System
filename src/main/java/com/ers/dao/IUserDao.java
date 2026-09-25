package com.ers.dao;

import com.ers.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    User addUser(User user) throws SQLException;
    User addUser(User user, Connection con) throws SQLException;
    boolean updateUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
    boolean deleteUserById(int userId);
    User getUserByUsername(String username);
    boolean updateUserStatus(int userId, boolean active);
}
