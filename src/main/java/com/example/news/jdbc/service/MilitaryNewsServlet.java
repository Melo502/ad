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

@WebServlet("/MilitaryNewsServlet")
public class MilitaryNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建 NewsDao 实例
        NewsDao newsDao = new NewsDao();

        // 查询军事新闻
        List<News> list = null;
        try {
            list = newsDao.searchNewsBySectionTopTen(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<News> clickList = null;
        try {
            clickList = newsDao.searchNewsByClickTopSix();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 将查询结果传递到 JSP 页面
        request.setAttribute("newsList", list);
        request.setAttribute("clickNewsList", clickList);

        // 转发到 JSP 页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("index4.jsp");
        dispatcher.forward(request, response);
    }
}

