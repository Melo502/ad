package com.example.advserv.dao;


import com.example.advserv.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends GenericDao<User> {

    public UserDao(Connection connection) {
        super(connection);
    }

    // 插入用户
    public int insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, userType) VALUES (?, ?, ?)";
        return insert(sql, user.getUsername(), user.getPassword(), user.getUserType());
    }

    // 查找用户（根据用户名）
    public User findUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        return findOne(sql, new RowMapper<User>() {
            @Override
            public User mapRow(java.sql.ResultSet resultSet) throws SQLException {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("userType"));
            }
        }, username);
    }

    // 查找所有用户
    public List<User> findAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        return findAll(sql, new RowMapper<User>() {
            @Override
            public User mapRow(java.sql.ResultSet resultSet) throws SQLException {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("userType"));
            }
        });
    }

    // 更新用户密码
    public int updateUserPassword(int id, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        return update(sql, newPassword, id);
    }

    // 删除用户
    public int deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        return delete(sql, id);
    }
}

