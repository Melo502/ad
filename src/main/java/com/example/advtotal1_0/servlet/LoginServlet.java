package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.pojo.User;
import com.example.advtotal1_0.service.user.UserService;
import com.example.advtotal1_0.service.user.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

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

        HttpSession session = request.getSession(true);

        // 登录类型不能为空
        if (userType == null || userType.isEmpty()) {
            request.setAttribute("error", "请选择登录类型！");
            request.getRequestDispatcher("jsp/other/login.jsp").forward(request, response);
            return;
        }

        // 验证用户账号密码和用户类型
        try {
            // 查询数据库中对应用户的信息
            User user = userService.findUserByUsername(username);

            if (user != null && user.getUserType().equals(userType) && user.getPassword().equals(password)) {

                // 登录成功，设置 session
                session.setAttribute("user", user);

                // 跳转到不同的页面
                if ("广告主".equals(userType)) {
                    response.sendRedirect("jsp/advertiser/advertiserDashboard.jsp"); // 广告主
                } else if ("网站长".equals(userType)) {
                    response.sendRedirect("jsp/webMaster/webmasterDashboard.jsp"); // 网站站长
                } else if ("admin".equals(userType)) {
                    response.sendRedirect("jsp/admin/adminDashboard.jsp"); // 管理员
                }
            } else {
                // 用户验证失败
                request.setAttribute("error", "账号、密码或用户类型错误！");
                request.getRequestDispatcher("jsp/other/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("数据库操作错误", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
