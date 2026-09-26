package com.ers.dao;

import com.ers.model.User;
import com.ers.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements IUserDao{
    private static final Logger logger =
            LoggerFactory.getLogger(UserDaoImpl.class);
    private final String insertQuery =
            "INSERT INTO users (username, password, role, is_active) " +
                    "VALUES (?, ?, ?, ?)";
    private final String updateUserQuery =
            "UPDATE users SET username = ?, password = ?, role = ?, is_active = ? " +
                    "WHERE user_id = ?";
    private final String selectuser = "SELECT * FROM users";
    private final String removeUserQuery = "DELETE FROM users WHERE user_id = ?";
   private final String sql = "UPDATE users SET is_active = ? WHERE user_id = ?";

    @Override
    public User addUser(User user) throws SQLException {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)

        ) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.isActive());
            logger.trace("Executing insert...");
//            System.out.println("Executing insert...");
            int count = ps.executeUpdate();

            if (count > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                logger.info("User added successfully");
//                System.out.println("User added successfully.");
                return user;
            }

        } catch (SQLException e) {
            logger.error("Insert failed!");
//            System.out.println("Insert failed!");
            e.printStackTrace();
        }

        return null;
        }

    @Override
    public User addUser(User user, Connection con) throws SQLException {
        try (
                PreparedStatement ps =
                        con.prepareStatement(
                                insertQuery,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.isActive());

            logger.trace("Executing transaction insert...");

            int count = ps.executeUpdate();

            if (count > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }

                logger.info("User added successfully through transaction");

                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(updateUserQuery)
        ) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.isActive());
            ps.setInt(5, user.getUserId());

            int count = ps.executeUpdate();

            if (count > 0) {
                logger.info("Database connection is open");
//                System.out.println("User updated successfully");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(selectuser);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                user.setUserId(rs.getInt("user_id"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean deleteUserById(int userId) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(removeUserQuery)
        ) {

            ps.setInt(1, userId);
            int count = ps.executeUpdate();
            if (count > 0) {
                logger.info("User deleted successfully");
//                System.out.println("User deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean updateUserStatus(int userId, boolean active) {

        try (
                Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setBoolean(1, active);
            ps.setInt(2, userId);

            int count = ps.executeUpdate();

            if (count > 0) {
                logger.info("User status updated successfully");
//                System.out.println("User status updated successfully");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) throws SQLException {
        UserDaoImpl userDao=new UserDaoImpl();
        User user=new User("akshitha","akshitha3","ADMIN",true, LocalDateTime.now());
        User result = userDao.addUser(user);

        if (result != null) {
            logger.info("Add operation completed.");
//            System.out.println("Add operation completed.");
        } else {
            logger.warn("Add operation failed.");
//            System.out.println("Add operation failed.");
        }

        List<User> users = userDao.getAllUsers();
        logger.info("===== USERS =====");
//        System.out.println("\n===== USERS =====");

        for (User u : users) {
            System.out.println(u);
        }
    }


}




