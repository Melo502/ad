package com.example.advtotal1_0.dao.user;


import com.example.advtotal1_0.dao.common.BaseDao;
import com.example.advtotal1_0.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                user.setWebSiteName(rs.getString("webSiteName"));
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

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, new Object[]{});
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                user.setWebSiteName(rs.getString("webSiteName"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取所有用户失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return users;
    }

    @Override
    public User getUserById(int id) throws Exception {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {id};
            rs = BaseDao.executeQuery(conn, pstm, rs, sql, params);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                user.setWebSiteName(rs.getString("webSiteName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("根据ID获取用户失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, rs);
        }
        return user;
    }

    @Override
    public int addUser1(User user) throws Exception {
        String sql = "INSERT INTO users ( username, password, user_type, webSiteName) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getUserType());
            if ("网站长".equals(user.getUserType())) {
                pstm.setString(4, user.getWebSiteName());
            } else {
                pstm.setNull(4, Types.VARCHAR);
            }
            return pstm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("添加用户失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public int updateUser(User user) throws Exception {
        String sql = "UPDATE users SET username = ?, password = ?, user_type = ?, webSiteName = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getUserType());
            if ("网站长".equals(user.getUserType())) {
                pstm.setString(4, user.getWebSiteName());
            } else {
                pstm.setNull(4, Types.VARCHAR);
            }
            pstm.setInt(5, user.getId());
            return pstm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("更新用户失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
    }

    @Override
    public int deleteUser(int id) throws Exception {
        int result = 0;
        String sql = "DELETE FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = BaseDao.getConnection();
            Object[] params = {id};
            result = BaseDao.execute(conn, pstm, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("删除用户失败", e);
        } finally {
            BaseDao.closeResource(conn, pstm, null);
        }
        return result;
    }
}
