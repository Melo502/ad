package com.example.advserv.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        try {
            // 连接数据库的URL、用户名、密码
            String url = "jdbc:mysql://115.120.224.202:3306/adv";
            String username = "web9";
            String password = "123456";

            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("数据库连接失败", e);
        }
    }
}

