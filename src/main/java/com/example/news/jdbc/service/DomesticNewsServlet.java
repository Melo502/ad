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

@WebServlet("/domesticNews")
public class DomesticNewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取新闻数据
        NewsDao newsDao = new NewsDao();
        List<News> list = null; // 获取国内新闻前十
        try {
            list = newsDao.searchNewsBySectionTopTen(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null; // 获取点击排行前六
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 设置数据到请求中
        request.setAttribute("newsList", list);
        request.setAttribute("clickList", clickList);

        // 转发到 JSP 页面
        request.getRequestDispatcher("/index2.jsp").forward(request, response);
    }
}

