package com.ers.service;

import com.ers.dao.IUserDao;
import com.ers.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService{
    private IUserDao userDao;
    public UserServiceImpl(IUserDao userDao){
        this.userDao=userDao;
    }
    @Override
    public User addUser(User user) throws SQLException {
        return userDao.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {

        return userDao.updateUser(user);
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean deleteUserById(int userId) {
        return userDao.deleteUserById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean updateUserStatus(int userId, boolean active) {
        return userDao.updateUserStatus(userId, active);
    }
}
