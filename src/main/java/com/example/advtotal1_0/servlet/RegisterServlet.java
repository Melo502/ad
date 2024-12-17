package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.pojo.User;
import com.example.advtotal1_0.service.user.UserService;
import com.example.advtotal1_0.service.user.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register.do")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        // 初始化 UserService
        userService = new UserServiceImpl();
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
            request.getRequestDispatcher("jsp/other/register.jsp").forward(request, response);
            return;
        }

        // 用户名不能为空
        if (username == null || username.isEmpty()) {
            request.setAttribute("error", "用户名不能为空！");
            request.getRequestDispatcher("jsp/other/register.jsp").forward(request, response);
            return;
        }

        // 密码不能为空
        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "密码不能为空！");
            request.getRequestDispatcher("jsp/other/register.jsp").forward(request, response);
            return;
        }

        // 检查用户名是否已存在
        try {
            User existingUser = userService.findUserByUsername(username);
            if (existingUser != null) {
                request.setAttribute("error", "用户名已存在！");
                request.getRequestDispatcher("jsp/other/register.jsp").forward(request, response);
                return;
            }

            // 创建新的用户对象
            User newUser = new User(0, username, password, userType);  // ID 为 0，数据库会自动生成
            boolean isRegistered = userService.addUser(newUser);

            // 如果注册成功
            if (isRegistered) {
                response.sendRedirect("jsp/other/login.jsp");  // 跳转到登录页面
            } else {
                // 注册失败
                request.setAttribute("error", "注册失败，请重试！");
                request.getRequestDispatcher("jsp/other/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("数据库操作错误", e);
        }
    }

}
