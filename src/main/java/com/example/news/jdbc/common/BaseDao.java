package com.example.news.jdbc.common;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class BaseDao {

    // 数据库连接信息
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static String dbDriverClass;

    static {
        try {
            // 从配置文件加载数据库连接信息
            InputStream inputStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            dbUrl = properties.getProperty("db.url");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
            dbDriverClass = properties.getProperty("db.driverClass");

            // 加载数据库驱动
            Class.forName(dbDriverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    // 释放资源
    protected void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 执行查询操作
    protected ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        // 设置查询参数
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        return ps.executeQuery();
    }

    // 执行更新操作（例如 insert, update, delete）
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        // 设置更新参数
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        return ps.executeUpdate();
    }
}

