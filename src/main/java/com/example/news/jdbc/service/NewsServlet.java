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

@WebServlet("/index") //urlPatterns = {"/", "/index"}
public class NewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsDao newsDao = new NewsDao();

        // 获取新闻数据
        List<News> list = null;
        try {
            list = newsDao.searchNewsByDateTopTen();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null;
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 将数据存入请求范围 (request scope)
        request.setAttribute("newsList", list);
        request.setAttribute("clickList", clickList);

        // 转发到 JSP 页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}

