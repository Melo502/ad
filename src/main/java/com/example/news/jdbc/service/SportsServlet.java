package com.example.news.jdbc.service;

import com.example.news.jdbc.dao.NewsDao;
import com.example.news.jdbc.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/sports")
public class SportsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取数据库连接和查询数据
        NewsDao newsDao = new NewsDao();
        List<News> newsList = null;  // 获取体育新闻
        try {
            newsList = newsDao.searchNewsBySectionTopTen(6);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null;   // 获取点击最多的新闻
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 将数据设置到请求中
        request.setAttribute("newsList", newsList);
        request.setAttribute("clickList", clickList);

        // 转发到 JSP 页面
        request.getRequestDispatcher("index6.jsp").forward(request, response);
    }
}

