package com.ers.service;

import com.ers.dao.IUserDao;
import com.ers.model.User;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    private final IUserDao userDao;

    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User user) throws SQLException {

        // Validation
        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null."
            );
        }

        if (user.getUserName() == null ||
                user.getUserName().isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required."
            );
        }

        if (user.getPassword() == null ||
                user.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required."
            );
        }

        if (!isValidRole(user.getRole())) {
            throw new IllegalArgumentException(
                    "Invalid user role."
            );
        }

        User result = userDao.addUser(user);

        if (result == null) {
            throw new IllegalArgumentException(
                    "Failed to add user."
            );
        }

        logger.info(
                "User added successfully: "
                        + user.getUserName()
        );

        return result;
    }

    @Override
    public boolean updateUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null."
            );
        }

        if (user.getUserId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        if (user.getUserName() == null ||
                user.getUserName().isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required."
            );
        }

        if (user.getPassword() == null ||
                user.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required."
            );
        }

        if (!isValidRole(user.getRole())) {
            throw new IllegalArgumentException(
                    "Invalid user role."
            );
        }

        boolean result = userDao.updateUser(user);

        if (result) {
            logger.info(
                    "User updated successfully: ID="
                            + user.getUserId()
            );
        }

        return result;
    }

    @Override
    public User getUserById(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        User user = userDao.getUserById(userId);

        if (user == null) {
            logger.warn(
                    "No user found with ID=" + userId
            );
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        return userDao.getAllUsers();
    }

    @Override
    public boolean deleteUserById(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        boolean result =
                userDao.deleteUserById(userId);

        if (result) {
            logger.info(
                    "User deleted successfully: ID="
                            + userId
            );
        }

        return result;
    }

    @Override
    public User getUserByUsername(String username) {

        if (username == null ||
                username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required."
            );
        }

        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean updateUserStatus(
            int userId,
            boolean active) {

        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        boolean result =
                userDao.updateUserStatus(
                        userId,
                        active
                );

        if (result) {
            logger.info(
                    "User status updated successfully: ID="
                            + userId
            );
        }

        return result;
    }

    private boolean isValidRole(String role) {

        return "EMPLOYEE".equals(role)
                || "MANAGER".equals(role)
                || "FINANCE_EXECUTIVE".equals(role)
                || "ADMIN".equals(role);
    }
}