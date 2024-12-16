package com.example.advserv.controller;

import com.example.advserv.dao.UserDao;
import com.example.advserv.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        // 初始化数据库连接
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://115.120.224.202:3306/adv", "web9", "123456");
            userDao = new UserDao(connection);  // 创建 UserDao 实例
        } catch (SQLException e) {
            throw new ServletException("数据库连接失败", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 登录类型不能为空
        if (userType == null || userType.isEmpty()) {
            request.setAttribute("error", "请选择登录类型！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 验证用户账号密码和用户类型
        try {
            // 查询数据库中对应用户的信息
            User user = userDao.findUserByUsername(username);

            if (user != null && user.getUserType().equals(userType) && user.getPassword().equals(password)) {
                // 登录成功，跳转到不同页面
                if ("ADVERTISER".equals(userType)) {
                    response.sendRedirect("advertiserDashboard.jsp");
                } else if ("WEBMASTER".equals(userType)) {
                    response.sendRedirect("webmasterDashboard.jsp");
                } else if ("ADMIN".equals(userType)) {
                    response.sendRedirect("adminDashboard.jsp");
                }
            } else {
                // 用户验证失败
                request.setAttribute("error", "账号、密码或用户类型错误！");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("数据库操作错误", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (userDao != null) {
                userDao.getConnection().close();  // 关闭数据库连接
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
