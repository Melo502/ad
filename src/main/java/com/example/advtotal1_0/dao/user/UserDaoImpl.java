package com.example.advtotal1_0.dao.user;


import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    // 根据用户名查找用户
    public User findUserByUsername(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            connection = BaseDao.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            Object[] params = {username};

            rs = BaseDao.executeQuery(connection, pstm, rs, sql, params);
            if (rs.next()) {
                // 从结果集中获取数据并封装为User对象
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, pstm, rs);
        }
        return user;
    }

    // 增加用户
    public boolean addUser(User user) {
        Connection connection = null;
        PreparedStatement pstm = null;
        boolean result = false;

        try {
            connection = BaseDao.getConnection();
            String sql = "INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)";
            Object[] params = {user.getUsername(), user.getPassword(), user.getUserType()};

            int rows = BaseDao.execute(connection, pstm, sql, params);
            result = rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, pstm, null);
        }
        return result;
    }
}
