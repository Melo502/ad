package com.example.advserv.controller;


import com.example.advserv.dao.AdDao;
import com.example.advserv.entity.Ad;
import com.example.advserv.entity.User;
import com.example.advserv.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



@WebServlet("/advertiserDashboard")
public class AdvertiserDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前广告主的登录信息（这里假设广告主已经登录并保存在session中）
        User advertiser = (User) request.getSession().getAttribute("user");

        if (advertiser == null) {
            // 如果广告主没有登录，则跳转到登录页面
            response.sendRedirect("login.jsp");
            return;
        }

        // 从数据库查询该广告主名下的所有广告
        try (Connection connection = DBUtil.getConnection()) {
            AdDao adDao = new AdDao(connection);
            List<Ad> ads = adDao.findAdsByAdmaster(advertiser.getUsername());

            // 将广告列表传递到JSP页面
            request.setAttribute("ads", ads);

            // 转发到广告主面板页面
            request.getRequestDispatcher("/advertiserDashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库操作失败");
        }
    }
}
