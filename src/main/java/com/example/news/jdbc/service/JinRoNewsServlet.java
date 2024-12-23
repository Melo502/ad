package com.example.news.jdbc.service;


import com.example.news.jdbc.dao.NewsDao;
import com.example.news.jdbc.model.News;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/JinRoNewsServlet")
public class JinRoNewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsDao newsDao = new NewsDao();

        // 获取新闻数据
        List<News> list = null;
        try {
            list = newsDao.searchNewsBySectionTopTen(5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null;
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 将新闻数据添加到请求中
        request.setAttribute("newsList", list);
        request.setAttribute("clickList", clickList);

        // 转发到 JSP 页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("index5.jsp");
        dispatcher.forward(request, response);
    }
}

