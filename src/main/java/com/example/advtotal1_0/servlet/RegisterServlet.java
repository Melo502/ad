package com.example.advtotal1_0.servlet;

import com.example.dao.UserDao;
import com.example.advserv.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        // 初始化数据库连接
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载 MySQL JDBC 驱动
            Connection connection = DriverManager.getConnection("jdbc:mysql://115.120.224.202:3306/adv", "web9", "123456");
            userDao = new UserDao(connection);  // 创建 UserDao 实例
        } catch (SQLException e) {
            throw new ServletException("数据库连接失败", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 注册类型不能为空
        if (userType == null || userType.isEmpty()) {
            request.setAttribute("error", "请选择注册类型！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 用户名不能为空
        if (username == null || username.isEmpty()) {
            request.setAttribute("error", "用户名不能为空！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 密码不能为空
        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "密码不能为空！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 检查用户名是否已存在
        try {
            User existingUser = userDao.findUserByUsername(username);
            if (existingUser != null) {
                request.setAttribute("error", "用户名已存在！");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // 创建新的用户对象
            User newUser = new User(0, username, password, userType);  // ID 为 0，数据库会自动生成
            int result = userDao.insertUser(newUser);

            // 如果插入成功
            if (result > 0) {
                response.sendRedirect("login.jsp");  // 跳转到登录页面
            } else {
                // 注册失败
                request.setAttribute("error", "注册失败，请重试！");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
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
